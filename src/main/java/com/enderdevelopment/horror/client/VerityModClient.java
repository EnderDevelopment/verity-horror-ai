package com.enderdevelopment.horror.client;

import com.enderdevelopment.horror.entity.VerityEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class VerityModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(VerityModelLayers.VERITY, VerityEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(VerityEntities.VERITY, VerityEntityRenderer::new);
	}
}
