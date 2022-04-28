package com.yyon.grapplinghook.items;

import com.yyon.grapplinghook.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


/*
 * This file is part of GrappleMod.

    GrappleMod is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    GrappleMod is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with GrappleMod.  If not, see <http://www.gnu.org/licenses/>.
 */

public class ClassicWebShooter2 extends ClassicWebShooter {

	public ClassicWebShooter2(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, BioCable i) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn, i);
		setUnlocalizedName("classicwebshooter2");
		int len = (int) bioCable.dlen * 12;

		leftLenWeb = len/2;
		rightLenWeb = len/2;
		maxdlenWeb = len;
		setMaxDamage(maxdlenWeb);


	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add( getMaxDamage(stack)+ " m bio cable");
		tooltip.add("Weak web stiffness coefficient");
		tooltip.add("Strong shooting power");
	}

	@Nullable
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		ClassicWebShoter3Model modelCustomWebShooter = new ClassicWebShoter3Model();
		modelCustomWebShooter.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;

		modelCustomWebShooter.isChild = _default.isChild;
		modelCustomWebShooter.isRiding = _default.isRiding;
		modelCustomWebShooter.isSneak = _default.isSneak;
		modelCustomWebShooter.rightArmPose = _default.rightArmPose;
		modelCustomWebShooter.leftArmPose = _default.leftArmPose;
		return modelCustomWebShooter;
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "thwip:textures/models/armor/classicwebshooter3.png";
	}

	@Override
	public GrappleCustomization getDefaultCustomization() {
		GrappleCustomization custom = new GrappleCustomization();
		custom.motor = true;
		custom.motorwhencrouching = true;
		custom.motorwhennotcrouching = false;
		custom.motoracceleration = 1;
		custom.motormaxspeed = 2;
		custom.repel = true;
		custom.repelforce = 0;
		custom.sticky = true;

		custom.motordampener = true;
		custom.pullbackwards = true;
		custom.doublehook = true;
		custom.oneropepull = true;
		custom.angle = 0;
		custom.sneakingangle = 0;
		custom.detachonkeyrelease = true;
		custom.maxlen = 400;
		custom.throwspeed = 11;
		custom.reelin = false;
		custom.setDouble("sc", 0.025 * Math.pow(10, 9));

		return custom;
	}

	@Override
	public void playThwipStart(World world, EntityPlayer player) {
		try {
			if (!world.isRemote){
				double i = Math.random();
				if (i > 0.5){
					world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipstartcu3")), SoundCategory.PLAYERS, (float) i, 1F);
				}
				else {
					world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipstartcu2")), SoundCategory.PLAYERS, (float) i, 1F);
				}
			}
		}
		catch (Exception e){}

	}
}
