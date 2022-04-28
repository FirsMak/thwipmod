package com.yyon.grapplinghook.items;// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ClassicWebShoter3Model extends ModelBiped {
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;

	public ClassicWebShoter3Model() {
		textureWidth = 180;
		textureHeight = 180;

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(0, -16.65F, 0.0F);
		RightArm.cubeList.add(new ModelBox(RightArm, 19, 23, -1.5F, 20.7F, 2.1F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 19, 23, 0.5F, 20.7F, -2.9F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 11, 23, -1.3F, 19.9F, 2.3F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 11, 23, 0.7F, 19.9F, -2.7F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 13, 0, -1.1F, 20.7F, 2.1F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 13, 0, 0.9F, 20.7F, -2.9F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 19, -1.5F, 20.7F, 2.4F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 19, 0.5F, 20.7F, -2.6F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 15, 23, -1.1F, 20.7F, 2.4F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 15, 23, 0.9F, 20.7F, -2.6F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 19, 23, 0.5F, 20.7F, 1.8F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 11, 23, 0.7F, 19.9F, 2.0F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 13, 0, 0.9F, 20.7F, 1.8F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 19, 0.5F, 20.7F, 2.1F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 15, 23, 0.9F, 20.7F, 2.1F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 19, 23, -3.5F, 20.7F, 1.8F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 11, 23, -3.3F, 19.9F, 2.0F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 13, 0, -3.1F, 20.7F, 1.8F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 19, -3.5F, 20.7F, 2.1F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 15, 23, -3.1F, 20.7F, 2.1F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 19, 23, -4.4F, 20.7F, -0.7F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 11, 23, -4.2F, 19.9F, -0.5F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 13, 0, -4.0F, 20.7F, -0.7F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 19, -4.4F, 20.7F, -0.4F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 15, 23, -4.0F, 20.7F, -0.4F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 23, 23, -1.1F, 26.3F, -0.5F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 14, 16, -0.65F, 24.8F, -2.2F, 2, 1, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 20, 20, -3.1F, 25.2F, -0.6F, 2, 2, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 9, 2, -3.15F, 24.8F, -0.6F, 3, 1, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 12, 20, -0.65F, 24.8F, 0.4F, 2, 1, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 6, 19, -1.3F, 24.2F, -2.2F, 1, 3, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 19, 23, -3.7F, 20.7F, -3.2F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 15, 23, -3.3F, 20.7F, -2.9F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 19, -3.7F, 20.7F, -2.9F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 11, 23, -3.5F, 19.9F, -3.0F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 13, 0, -3.3F, 20.7F, -3.2F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 21, -1.3F, 21.2F, -3.0F, 1, 3, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 21, 16, -1.1F, 21.0F, -3.2F, 1, 2, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 9, 0, -1.3F, 21.5F, -3.3F, 1, 1, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 21, 3, -1.5F, 21.0F, -3.2F, 1, 2, 1, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 0, -3.2F, 21.8F, -2.3F, 3, 2, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 18, 13, -0.65F, 20.8F, 0.4F, 2, 1, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 9, -3.3F, 22.8F, -2.4F, 3, 1, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 18, 0, -3.3F, 22.8F, 0.4F, 3, 1, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 7, 15, -0.65F, 22.8F, -2.4F, 2, 1, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 18, 9, -0.65F, 22.8F, 0.4F, 2, 1, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 13, -0.65F, 20.8F, -2.4F, 2, 1, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 16, 6, -3.3F, 20.8F, 0.4F, 3, 1, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 5, -3.3F, 20.8F, -2.4F, 3, 1, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 17, -0.8F, 21.8F, 0.3F, 2, 2, 2, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 9, 6, -0.8F, 21.8F, -2.3F, 2, 2, 3, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 10, 11, -3.2F, 21.8F, 0.3F, 3, 2, 2, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(0F, -16.65F, 0.0F);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 19, 23, 0.5F, 20.7F, 2.1F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 19, 23, -1.5F, 20.7F, -2.9F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 11, 23, 0.3F, 19.9F, 2.3F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 11, 23, -1.7F, 19.9F, -2.7F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 13, 0, 0.1F, 20.7F, 2.1F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 13, 0, -1.9F, 20.7F, -2.9F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 19, 0.5F, 20.7F, 2.4F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 19, -1.5F, 20.7F, -2.6F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 15, 23, 0.1F, 20.7F, 2.4F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 15, 23, -1.9F, 20.7F, -2.6F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 19, 23, -1.5F, 20.7F, 1.8F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 11, 23, -1.7F, 19.9F, 2.0F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 13, 0, -1.9F, 20.7F, 1.8F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 19, -1.5F, 20.7F, 2.1F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 15, 23, -1.9F, 20.7F, 2.1F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 19, 23, 2.5F, 20.7F, 1.8F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 11, 23, 2.3F, 19.9F, 2.0F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 13, 0, 2.1F, 20.7F, 1.8F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 19, 2.5F, 20.7F, 2.1F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 15, 23, 2.1F, 20.7F, 2.1F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 19, 23, 3.4F, 20.7F, -0.7F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 11, 23, 3.2F, 19.9F, -0.5F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 13, 0, 3.0F, 20.7F, -0.7F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 19, 3.4F, 20.7F, -0.4F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 15, 23, 3.0F, 20.7F, -0.4F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 23, 23, 0.1F, 26.3F, -0.5F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 14, 16, -1.35F, 24.8F, -2.2F, 2, 1, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 20, 20, 1.1F, 25.2F, -0.6F, 2, 2, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 9, 2, 0.15F, 24.8F, -0.6F, 3, 1, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 12, 20, -1.35F, 24.8F, 0.4F, 2, 1, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 6, 19, 0.3F, 24.2F, -2.2F, 1, 3, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 19, 23, 2.7F, 20.7F, -3.2F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 15, 23, 2.3F, 20.7F, -2.9F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 19, 2.7F, 20.7F, -2.9F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 11, 23, 2.5F, 19.9F, -3.0F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 13, 0, 2.3F, 20.7F, -3.2F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 21, 0.3F, 21.2F, -3.0F, 1, 3, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 21, 16, 0.1F, 21.0F, -3.2F, 1, 2, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 9, 0, 0.3F, 21.5F, -3.3F, 1, 1, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 21, 3, 0.5F, 21.0F, -3.2F, 1, 2, 1, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 0, 0.2F, 21.8F, -2.3F, 3, 2, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 18, 13, -1.35F, 20.8F, 0.4F, 2, 1, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 9, 0.3F, 22.8F, -2.4F, 3, 1, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 18, 0, 0.3F, 22.8F, 0.4F, 3, 1, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 7, 15, -1.35F, 22.8F, -2.4F, 2, 1, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 18, 9, -1.35F, 22.8F, 0.4F, 2, 1, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 13, -1.35F, 20.8F, -2.4F, 2, 1, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 16, 6, 0.3F, 20.8F, 0.4F, 3, 1, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 5, 0.3F, 20.8F, -2.4F, 3, 1, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 17, -1.2F, 21.8F, 0.3F, 2, 2, 2, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 9, 6, -1.2F, 21.8F, -2.3F, 2, 2, 3, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 10, 11, 0.2F, 21.8F, 0.3F, 3, 2, 2, 0.0F, true));
		this.bipedLeftArm.addChild(LeftArm);
		this.bipedRightArm.addChild(RightArm);
		this.bipedBody.isHidden = true;}

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