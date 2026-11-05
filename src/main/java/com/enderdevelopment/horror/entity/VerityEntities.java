package com.enderdevelopment.horror.entity;

import com.enderdevelopment.horror.VerityMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class VerityEntities {
	public static final EntityType<VerityEntity> VERITY = Registry.register(
		Registries.ENTITY_TYPE,
		new Identifier(VerityMod.MOD_ID, "verity"),
		FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VerityEntity::new)
			.dimensions(EntityDimensions.fixed(0.7F, 2.0F))
			.build()
	);

	public static void register() {
		FabricDefaultAttributeRegistry.register(VERITY, VerityEntity.createVerityAttributes());
	}
}
