package com.enderdevelopment.horror;

import com.enderdevelopment.horror.entity.VerityEntities;
import com.enderdevelopment.horror.item.ModItems;
import com.enderdevelopment.horror.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerityMod implements ModInitializer {
	public static final String MOD_ID = "horror";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModSounds.register();
		VerityEntities.register();
		ModItems.register();

		// Verity naturally hunts in darkness across most overworld biomes.
		BiomeModifications.addSpawn(
			BiomeSelectors.foundInOverworld(),
			SpawnGroup.MONSTER,
			VerityEntities.VERITY,
			40,
			1,
			1
		);
		SpawnRestriction.register(VerityEntities.VERITY, SpawnRestriction.Location.ON_GROUND,
			Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);

		LOGGER.info("Verity Horror has awoken. It knows you are reading this.");
	}
}
