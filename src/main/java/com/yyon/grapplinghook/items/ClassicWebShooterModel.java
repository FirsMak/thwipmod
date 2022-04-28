package com.yyon.grapplinghook.items;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ClassicWebShooterModel extends ModelBiped {
	private final ModelRenderer bone;
	private final ModelRenderer bone3;

	public ClassicWebShooterModel() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0, 0.4F, 0.0F);
		bone.setTextureOffset(0, 0).addBox(0.5F, 7.0F, -2.8F, 1, 2, 1);
		bone.setTextureOffset(6, 8).addBox(0.5F, 7.4F, -2.3F, 1, 3, 2);
		bone.setTextureOffset(0, 7).addBox(0.0F, 9.6F, -0.7F, 2, 1, 2);
		bone.setTextureOffset(0, 0).addBox(-1.5F, 6.0F, -2.5F, 5, 2, 5);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-2F, 0.4F, 0.0F);
		bone3.setTextureOffset(0, 0).addBox(0.5F, 7.0F, -2.8F, 1, 2, 1);
		bone3.setTextureOffset(6, 8).addBox(0.5F, 7.4F, -2.3F, 1, 3, 2);
		bone3.setTextureOffset(0, 7).addBox(0.0F, 9.6F, -0.7F, 2, 1, 2);
		bone3.setTextureOffset(0, 0).addBox(-1.5F, 6.0F, -2.5F, 5, 2, 5);

		this.bipedLeftArm.addChild(bone);
		this.bipedRightArm.addChild(bone3);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}