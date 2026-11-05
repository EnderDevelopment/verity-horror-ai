package com.enderdevelopment.horror.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class VerityEntityModel extends SinglePartEntityModel<com.enderdevelopment.horror.entity.VerityEntity> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public VerityEntityModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.rightArm = root.getChild("right_arm");
		this.leftArm = root.getChild("left_arm");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		root.addChild("head", ModelPartBuilder.create()
			.uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		root.addChild("body", ModelPartBuilder.create()
			.uv(0, 17).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 13.0F, 6.0F),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		root.addChild("right_arm", ModelPartBuilder.create()
			.uv(32, 0).cuboid(-9.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F),
			ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		root.addChild("left_arm", ModelPartBuilder.create()
			.uv(48, 0).cuboid(5.0F, -2.0F, -2.0F, 4.0F, 16.0F, 4.0F),
			ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		root.addChild("right_leg", ModelPartBuilder.create()
			.uv(32, 20).cuboid(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			ModelTransform.pivot(-2.0F, 12.0F, 0.0F));

		root.addChild("left_leg", ModelPartBuilder.create()
			.uv(48, 20).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			ModelTransform.pivot(2.0F, 12.0F, 0.0F));

		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(com.enderdevelopment.horror.entity.VerityEntity entity, float limbAngle, float limbDistance,
		float animationProgress, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;

		this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
		this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
		this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
	}
}
