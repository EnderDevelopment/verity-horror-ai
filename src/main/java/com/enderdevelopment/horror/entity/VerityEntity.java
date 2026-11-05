package com.enderdevelopment.horror.entity;

import com.enderdevelopment.horror.sound.ModSounds;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * Verity is the intelligent horror of this mod. It does not simply chase you --
 * it learns. It stays out of direct light, teleports out of sight, studies your
 * position, and only strikes when you are alone, in darkness, and vulnerable.
 */
public class VerityEntity extends HostileEntity {
	/** When enough "sight" has been accumulated the creature commits to an attack. */
	private int awareness = 0;
	private static final int AWARENESS_THRESHOLD = 200;
	private boolean screeched = false;

	public VerityEntity(EntityType<? extends VerityEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 12;
	}

	public static DefaultAttributeContainer.Builder createVerityAttributes() {
		return HostileEntity.createHostileAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32D)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
			.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5D)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new FleeLightGoal(this, 1.0D));
		this.goalSelector.add(1, new TeleportToDarknessGoal(this));
		this.goalSelector.add(2, new MeleeAttackGoal(this, 1.35D, false));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.9D));
		this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
		this.goalSelector.add(5, new LookAroundGoal(this));

		this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false, this::shouldTarget));
	}

	/** Verity only wants to hunt players who are alone and in darkness. */
	private boolean shouldTarget(PlayerEntity player) {
		if (player.isCreative() || player.isSpectator()) {
			return false;
		}
		float light = this.getWorld().getLightLevel(LightType.BLOCK, player.getBlockPos());
		return light <= 3 && countNearbyPlayers() <= 1;
	}

	private int countNearbyPlayers() {
		int count = 0;
		for (PlayerEntity player : this.getWorld().getPlayers()) {
			if (player.isAlive() && player.getBlockPos().isWithinDistance(this.getBlockPos(), 48.0D)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.getWorld().isClient && this.getTarget() instanceof PlayerEntity player) {
			float light = this.getWorld().getLightLevel(LightType.BLOCK, player.getBlockPos());

			// In darkness, Verity grows bolder and faster.
			if (light <= 3) {
				this.awareness++;
			} else {
				this.awareness = Math.max(0, this.awareness - 4);
			}

			// When fully "aware", it commits and screams a warning.
			if (this.awareness >= AWARENESS_THRESHOLD && !this.screeched) {
				this.screeched = true;
				this.getWorld().playSound(null, this.getBlockPos(), ModSounds.VERITY_SCREAM,
					SoundCategory.HOSTILE, 2.0F, 0.8F + this.random.nextFloat() * 0.4F);
			}
		}
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return distanceSquared > (double) (this.getWorld().getDifficulty() != Difficulty.PEACEFUL ? 25600 : 12800) && this.awareness < AWARENESS_THRESHOLD / 2;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return this.getWorld().isNight() ? ModSounds.VERITY_AMBIENT : null;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.VERITY_HURT;
	}

	@Override
	protected float getSoundVolume() {
		return 0.75F;
	}

	@Override
	protected float getActiveEyeHeight(net.minecraft.entity.EntityPose pose, EntityDimensions dimensions) {
		return 1.85F;
	}

	// -------------------------------------------------------------------------
	// Purpose-built AI goals
	// -------------------------------------------------------------------------

	/** Verity flees into shadow when standing in bright light, always seeking cover. */
	private static class FleeLightGoal extends Goal {
		private final MobEntity mob;
		private final double speed;

		FleeLightGoal(MobEntity mob, double speed) {
			this.mob = mob;
			this.speed = speed;
			this.setControls(EnumSet.of(Control.MOVE));
		}

		@Override
		public boolean canStart() {
			BlockPos pos = this.mob.getBlockPos();
			return this.mob.getWorld().getLightLevel(LightType.BLOCK, pos) > 5;
		}

		@Override
		public void tick() {
			BlockPos pos = this.mob.getBlockPos();
			int radius = 12;
			BlockPos best = null;
			int bestLight = Integer.MAX_VALUE;

			for (int dx = -radius; dx <= radius; dx++) {
				for (int dz = -radius; dz <= radius; dz++) {
					BlockPos candidate = pos.add(dx, 0, dz);
					int light = this.mob.getWorld().getLightLevel(LightType.BLOCK, candidate);
					boolean walkable = !this.mob.getWorld().getBlockState(candidate).isAir()
						&& this.mob.getWorld().getBlockState(candidate.up()).isAir();
					if (walkable && light < bestLight) {
						bestLight = light;
						best = candidate;
					}
				}
			}

			if (best != null && bestLight <= 3) {
				this.mob.getNavigation().startMovingTo(best.getX() + 0.5, best.getY(), best.getZ() + 0.5, this.speed);
			}
		}
	}

	/**
	 * The signature move: when the player looks away or is far, Verity teleports
	 * out of sight near the player, then studies them from the dark.
	 */
	private static class TeleportToDarknessGoal extends Goal {
		private final VerityEntity verity;
		private int cooldown;

		TeleportToDarknessGoal(VerityEntity verity) {
			this.verity = verity;
			this.setControls(EnumSet.of(Control.MOVE));
		}

		@Override
		public boolean canStart() {
			PlayerEntity target = this.verity.getTarget();
			if (target == null || --this.cooldown > 0) {
				return false;
			}
			double dist = this.verity.squaredDistanceTo(target);
			return dist > 256.0D && dist < 2304.0D; // 16-48 blocks
		}

		@Override
		public void start() {
			PlayerEntity target = this.verity.getTarget();
			if (target == null) {
				return;
			}
			Vec3d toPlayer = target.getPos().subtract(this.verity.getPos()).normalize();
			BlockPos spot = target.getBlockPos().add(toPlayer.multiply(9.0D)).down(1);

			if (this.verity.getWorld().getBlockState(spot).isAir() && !this.verity.getWorld().isClient) {
				this.verity.requestTeleport(spot.getX() + 0.5, this.verity.getY(), spot.getZ() + 0.5);
				this.verity.getWorld().playSound(null, this.verity.getBlockPos(),
					ModSounds.VERITY_TELEPORT, SoundCategory.HOSTILE, 1.2F, 1.0F);
				if (this.verity.getWorld().getRandom().nextFloat() < 0.35F) {
					this.verity.getWorld().playSound(null, this.verity.getBlockPos(),
						ModSounds.VERITY_AMBIENT, SoundCategory.HOSTILE, 1.0F, 0.6F);
				}
			}
			this.cooldown = 160 + this.verity.getRandom().nextInt(80);
		}

		@Override
		public boolean shouldContinue() {
			return false;
		}
	}
}
