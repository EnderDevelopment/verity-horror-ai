package com.enderdevelopment.horror.client;

import com.enderdevelopment.horror.entity.VerityEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class VerityEntityRenderer extends EntityRenderer<VerityEntity> {
	// Reuses the vanilla Enderman texture so Verity renders correctly out of the box
	// without shipping a binary texture file. The dark tint makes it a shadowy figure.
	private static final Identifier TEXTURE = Identifier.tryParse("minecraft:textures/entity/enderman/enderman.png");
	private static final RenderLayer SOLID_LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);
	private final EntityModel<VerityEntity> model;

	public VerityEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.model = new VerityEntityModel(context.getPart(VerityModelLayers.VERITY));
	}

	@Override
	public void render(VerityEntity entity, float yaw, float tickDelta, MatrixStack matrices,
		VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.scale(-1.0F, -1.0F, 1.0F);

		// A subtle, unnatural sway as it watches you.
		float pulse = (float) Math.sin(entity.age * 0.12F) * 0.06F;
		matrices.translate(0.0F, -1.501F + pulse, 0.0F);

		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SOLID_LAYER);
		this.model.setAngles(entity, 0.0F, 0.0F, entity.age + tickDelta, yaw, entity.getPitch());
		this.model.render(matrices, vertexConsumer, light, 0, 0.35F, 0.42F, 1.0F);

		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(VerityEntity entity) {
		return TEXTURE;
	}
}
