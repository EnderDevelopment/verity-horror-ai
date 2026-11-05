package com.enderdevelopment.horror.client;

import com.enderdevelopment.horror.VerityMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class VerityModelLayers {
	public static final EntityModelLayer VERITY =
		new EntityModelLayer(new Identifier(VerityMod.MOD_ID, "verity"), "main");

	private VerityModelLayers() {
	}
}
