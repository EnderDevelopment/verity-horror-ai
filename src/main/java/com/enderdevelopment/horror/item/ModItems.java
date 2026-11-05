package com.enderdevelopment.horror.item;

import com.enderdevelopment.horror.VerityMod;
import com.enderdevelopment.horror.entity.VerityEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
	public static final Item VERITY_SPAWN_EGG = new SpawnEggItem(
		VerityEntities.VERITY, 0x0a0a12, 0x6b1f3a,
		new FabricItemSettings()
	);

	public static final Item HOLLOW_BELL = new HollowBellItem(new FabricItemSettings().maxCount(1));

	public static void register() {
		Registry.register(Registries.ITEM, new Identifier(VerityMod.MOD_ID, "verity_spawn_egg"), VERITY_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier(VerityMod.MOD_ID, "hollow_bell"), HOLLOW_BELL);
	}
}
