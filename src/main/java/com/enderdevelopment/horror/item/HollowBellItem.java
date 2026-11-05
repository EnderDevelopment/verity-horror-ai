package com.enderdevelopment.horror.item;

import com.enderdevelopment.horror.entity.VerityEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * The Hollow Bell is the player's one defensive tool. Ringing it briefly stuns
 * Verity and gives the player a window of light-sight to escape. But a bell
 * draws attention -- ringing it makes Verity aware of exactly where you are.
 */
public class HollowBellItem extends Item {
	private static final int COOLDOWN_TICKS = 300;

	public HollowBellItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BELL_USE, SoundCategory.PLAYERS, 1.0F, 1.2F);

		if (!world.isClient) {
			user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 200, 0, false, false, true),
				user);

			for (LivingEntity entity : world.getEntitiesByClass(VerityEntity.class, user.getBoundingBox().expand(24.0D), e -> true)) {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 3, false, false, false), user);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false, false), user);
			}

			user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
		}

		return TypedActionResult.success(stack, world.isClient());
	}
}
