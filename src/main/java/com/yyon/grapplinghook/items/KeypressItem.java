package com.yyon.grapplinghook.items;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


public interface KeypressItem {
	enum Keys {
		LAUNCHER, THROWLEFT, THROWRIGHT, THROWBOTH, ROCKET, MODE
	}
	
	public abstract void onCustomKeyDown(ItemStack stack, EntityPlayer player, Keys key, boolean ismainhand);
	public abstract void onCustomKeyUp(ItemStack stack, EntityPlayer player, Keys key, boolean ismainhand);
}
