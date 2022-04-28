package com.yyon.grapplinghook.items;// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TasmWebShoterModel extends ModelBiped {
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;

	public TasmWebShoterModel() {
		textureWidth = 48;
		textureHeight = 48;

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(0, -14F, 0.0F);
		RightArm.cubeList.add(new ModelBox(RightArm, 16, 0, -3.5F, 20.5F, -2.4F, 5, 1, 5, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 12, 0, -1.8F, 19.9F, -2.6F, 2, 2, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 16, 3, -1.3F, 20.4F, -2.8F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 0, -1.3F, 19.2F, -2.3F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 16, -1.3F, 22.2F, -2.2F, 1, 2, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 16, 6, -1.3F, 23.3F, -1.2F, 1, 1, 1, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(0, -14F, 0.0F);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 16, 6, 0.3F, 23.3F, -1.2F, 1, 1, 1, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 16, 0.3F, 22.2F, -2.2F, 1, 2, 1, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 0, 0.3F, 19.2F, -2.3F, 1, 3, 1, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 16, 3, 0.3F, 20.4F, -2.8F, 1, 1, 1, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 12, 0, -0.2F, 19.9F, -2.6F, 2, 2, 1, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 16, 0, -1.5F, 20.5F, -2.4F, 5, 1, 5, 0.0F, false));

		this.bipedLeftArm.addChild(LeftArm);
		this.bipedRightArm.addChild(RightArm);
		this.bipedBody.isHidden = true;
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

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}