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

public class WebShooterTasm extends ClassicWebShooter {

	public WebShooterTasm(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, BioCable i) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn, i);
		setUnlocalizedName("tasmwebshooter");
		int len = (int) bioCable.dlen * 2;

		leftLenWeb = len/2;
		rightLenWeb = len/2;
		maxdlenWeb = len;
		setMaxDamage(maxdlenWeb);
	}

	@Override
	public void playThwipStart(World world, EntityPlayer player) {
		if (!world.isRemote){
			double i = Math.random();
			if (i > 0.5){
				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipstarttasm")), SoundCategory.PLAYERS, (float) i, 1F);
			}
			else {
				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipstarttasm3")), SoundCategory.PLAYERS, (float) i, 1F);
			}
			world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipcontinuetasm")), SoundCategory.PLAYERS, (float) i, 1F);
		}
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "thwip:textures/models/armor/webshootertasm.png";
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add( getMaxDamage(stack)+ " m bio cable");
		tooltip.add("Hard web stiffness coefficient");
		tooltip.add("Strong shooting power");
	}

	@Nullable
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		TasmWebShoterModel modelCustomWebShooter = new TasmWebShoterModel();
		modelCustomWebShooter.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;

		modelCustomWebShooter.isChild = _default.isChild;
		modelCustomWebShooter.isRiding = _default.isRiding;
		modelCustomWebShooter.isSneak = _default.isSneak;
		modelCustomWebShooter.rightArmPose = _default.rightArmPose;
		modelCustomWebShooter.leftArmPose = _default.leftArmPose;
		onPlayerBaubleRender(itemStack, (EntityPlayer) entityLiving, RenderType.BODY, 12);
		return modelCustomWebShooter;
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
		custom.maxlen = 300;
		custom.throwspeed = 12;
		custom.reelin = false;
		custom.setBoolean("tasmmode", true);
		custom.setDouble("sc", 0.095 * Math.pow(10, 9));

		return custom;
	}
}
