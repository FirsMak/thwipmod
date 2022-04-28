package com.yyon.grapplinghook.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.yyon.grapplinghook.*;
import com.yyon.grapplinghook.entities.WebBall;
import com.yyon.grapplinghook.entities.grappleArrow;
import com.yyon.grapplinghook.network.DetachSingleHookMessage;
import com.yyon.grapplinghook.network.GrappleDetachMessage;
import com.yyon.grapplinghook.network.KeypressMessage;
import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

public class ClassicWebShooter extends ItemArmor implements KeypressItem, baubles.api.IBauble, IRenderBauble {
	public int leftLenWeb = 3400;
	public int rightLenWeb = 3400;
	public int maxdlenWeb = leftLenWeb + rightLenWeb;
	public BioCable  bioCable;
	public ClassicWebShooter(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, BioCable bioCable) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		maxStackSize = 1;
		this.bioCable = bioCable;

		setUnlocalizedName("classicwebshooter");

		setCreativeTab(grapplemod.tabGrapplemod);
		int len = (int) bioCable.dlen * 4;

		leftLenWeb = len/2;
		rightLenWeb = len/2;
		maxdlenWeb = len;
		setMaxDamage(maxdlenWeb);


		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		System.out.println("проверка теории");
		EntityPlayer player = Minecraft.getMinecraft().player;
		if ((leftLenWeb + rightLenWeb) >= maxdlenWeb){
			leftLenWeb = maxdlenWeb/2;
			rightLenWeb = maxdlenWeb/2;
		}
		else {
			if (rightLenWeb >= leftLenWeb){
				leftLenWeb = leftLenWeb + bioCable.dlen;
				if (leftLenWeb > maxdlenWeb/2){
					leftLenWeb = maxdlenWeb/2;
				}
			}
			else {
				rightLenWeb = rightLenWeb + bioCable.dlen;
				if (rightLenWeb > maxdlenWeb/2){
					rightLenWeb = maxdlenWeb/2;
				}
			}
		}
		itemStack.setItemDamage(maxdlenWeb - (leftLenWeb + rightLenWeb));
		playThwipChange();
		return itemStack;
	}

	public static boolean isEndlessWeb = false;

	@Override
	public void setDamage(ItemStack stack, int damage) {
		if ((leftLenWeb + rightLenWeb) >= maxdlenWeb){
			leftLenWeb = maxdlenWeb/2;
			rightLenWeb = maxdlenWeb/2;
		}
		if (!isEndlessWeb){
			int d = maxdlenWeb-leftLenWeb-rightLenWeb;
			super.setDamage(stack, d);
		}
		else {
			System.out.println("здесь какого фига");
			leftLenWeb = maxdlenWeb/2;
			rightLenWeb = maxdlenWeb/2;
			super.setDamage(stack, 1);
		}
	}


	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add( maxdlenWeb+ " m bio cable");
		tooltip.add("Medium web stiffness coefficient");
		tooltip.add("Weak shooting power");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

		if (ClientProxyClass.itemStack == null){
			ClientProxyClass.itemStack = itemstack;
			ClientProxyClass.isUse = true;
		}
		// keep in same order as enum from KeypressItem
		boolean keys[] = {ClientProxyClass.key_enderlaunch.isKeyDown(), ClientProxyClass.key_leftthrow.isKeyDown(), ClientProxyClass.key_rightthrow.isKeyDown(), ClientProxyClass.key_boththrow.isKeyDown(), ClientProxyClass.key_rocket.isKeyDown()};

		for (int i = 0; i < keys.length; i++) {
			boolean iskeydown = keys[i];
			boolean prevkey = ClientProxyClass.prevkeys[i];

			if (iskeydown != prevkey) {
				KeypressItem.Keys key = KeypressItem.Keys.values()[i];
				if (iskeydown) {
					((KeypressItem) itemstack.getItem()).onCustomKeyDown(itemstack, (EntityPlayer) player, key, true);
				} else {
					((KeypressItem) itemstack.getItem()).onCustomKeyUp(itemstack, (EntityPlayer) player, key, true);
				}
			}
			ClientProxyClass.prevkeys[i] = iskeydown;
		}
	}

	@Override
	public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		ClientProxyClass.isUse = false;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		ClientProxyClass.isUse = true;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}
	
	public boolean hasArrow(Entity entity) {
		grappleArrow arrow1 = getArrowLeft(entity);
		grappleArrow arrow2 = getArrowRight(entity);
		return (arrow1 != null) || (arrow2 != null);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack mat = new ItemStack(Items.LEATHER, 1);
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
	}

	public void setArrowLeft(Entity entity, grappleArrow arrow) {
		ClientProxyClass.grapplearrows1.put(entity, arrow);
	}
	public void setArrowRight(Entity entity, grappleArrow arrow) {
		ClientProxyClass.grapplearrows2.put(entity, arrow);
	}
	public grappleArrow getArrowLeft(Entity entity) {
		if (ClientProxyClass.grapplearrows1.containsKey(entity)) {
			grappleArrow arrow = ClientProxyClass.grapplearrows1.get(entity);
			if (arrow != null && !arrow.isDead) {
				return arrow;
			}
		}
		return null;
	}
	public grappleArrow getArrowRight(Entity entity) {
		if (ClientProxyClass.grapplearrows2.containsKey(entity)) {
			grappleArrow arrow = ClientProxyClass.grapplearrows2.get(entity);
			if (arrow != null && !arrow.isDead) {
				return arrow;
			}
		}
		return null;
	}	
	
	public void dorightclick(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand) {
        if (!worldIn.isRemote) {
    	}
	}
	
	public void throwBoth(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand) {
		grappleArrow arrow_left = getArrowLeft(entityLiving);
		grappleArrow arrow_right = getArrowRight(entityLiving);

		if (arrow_left != null || arrow_right != null) {
			detachBoth(entityLiving);
    		return;
		}
		
    	GrappleCustomization custom = this.getCustomization(stack);
  		double angle = custom.angle;
  		double verticalangle = custom.verticalthrowangle;
  		if (entityLiving.isSneaking()) {
  			angle = custom.sneakingangle;
  			verticalangle = custom.sneakingverticalthrowangle;
  		}

    	if (!(!custom.doublehook || angle == 0)) {
    		throwLeft(stack, worldIn, entityLiving, righthand);
    	}
		throwRight(stack, worldIn, entityLiving, righthand);

		stack.damageItem(1, entityLiving);
		if (arrow_left.attached){
			//worldIn.playSound((EntityPlayer)null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, new SoundEvent(new ResourceLocation("grapplemod", "thwipcontinue")), SoundCategory.PLAYERS, 1F, 1F);
		}
		if (arrow_right.attached){
			//worldIn.playSound((EntityPlayer)null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, new SoundEvent(new ResourceLocation("grapplemod", "thwipcontinue")), SoundCategory.PLAYERS, 1F, 1F);
		}
	}
	
	public boolean throwLeft(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand) {
    	GrappleCustomization custom = this.getCustomization(stack);

  		double angle = custom.angle;
  		double verticalangle = custom.verticalthrowangle;

  		if (entityLiving.isSneaking()) {
  			angle = custom.sneakingangle;
  			verticalangle = custom.sneakingverticalthrowangle;
  		}

  		EntityLivingBase player = entityLiving;
  		vec anglevec = vec.fromAngles(Math.toRadians(-angle), Math.toRadians(verticalangle)); //new vec(0,0,1).rotate_yaw(Math.toRadians(angle)).rotate_pitch(Math.toRadians(verticalangle));
  		anglevec = anglevec.rotate_pitch(Math.toRadians(-player.rotationPitch));
  		anglevec = anglevec.rotate_yaw(Math.toRadians(player.rotationYaw));
        float velx = -MathHelper.sin((float) anglevec.getYaw() * 0.017453292F) * MathHelper.cos((float) anglevec.getPitch() * 0.017453292F);
        float vely = -MathHelper.sin((float) anglevec.getPitch() * 0.017453292F);
        float velz = MathHelper.cos((float) anglevec.getYaw() * 0.017453292F) * MathHelper.cos((float) anglevec.getPitch() * 0.017453292F);
		grappleArrow entityarrow = this.createarrow(stack, worldIn, entityLiving, false, true);// new grappleArrow(worldIn, player, false);
        float extravelocity = (float) vec.motionvec(entityLiving).dist_along(new vec(velx, vely, velz));
        if (extravelocity < 0) { extravelocity = 0; }
        entityarrow.shoot((double) velx, (double) vely, (double) velz, entityarrow.getVelocity() + extravelocity, 0.0F);
		//entityarrow.shoot(entityLiving, entityLiving.rotationPitch,entityLiving.rotationYaw, 0,entityarrow.getVelocity() + extravelocity , 0);

		worldIn.spawnEntity(entityarrow);
		setArrowLeft(entityLiving, entityarrow);

		return true;
	}
	
	public void throwRight(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand) {
    	GrappleCustomization custom = this.getCustomization(stack);
    	
  		double angle = custom.angle;
  		double verticalangle = custom.verticalthrowangle;
  		if (entityLiving.isSneaking()) {
  			angle = custom.sneakingangle;
  			verticalangle = custom.sneakingverticalthrowangle;
  		}
		if (!custom.doublehook || angle == 0) {
			grappleArrow entityarrow = this.createarrow(stack, worldIn, entityLiving, righthand, false);
      		vec anglevec = new vec(0,0,1).rotate_pitch(Math.toRadians(verticalangle));
      		anglevec = anglevec.rotate_pitch(Math.toRadians(-entityLiving.rotationPitch));
      		anglevec = anglevec.rotate_yaw(Math.toRadians(entityLiving.rotationYaw));
	        float velx = -MathHelper.sin((float) anglevec.getYaw() * 0.017453292F) * MathHelper.cos((float) anglevec.getPitch() * 0.017453292F);
	        float vely = -MathHelper.sin((float) anglevec.getPitch() * 0.017453292F);
	        float velz = MathHelper.cos((float) anglevec.getYaw() * 0.017453292F) * MathHelper.cos((float) anglevec.getPitch() * 0.017453292F);
	        float extravelocity = (float) vec.motionvec(entityLiving).dist_along(new vec(velx, vely, velz));
	        if (extravelocity < 0) { extravelocity = 0; }
			entityarrow.shoot((double) velx, (double) vely, (double) velz, entityarrow.getVelocity() + extravelocity, 0.0F);
			//entityarrow.shoot(entityLiving, entityLiving.rotationPitch,entityLiving.rotationYaw, 0,entityarrow.getVelocity() + extravelocity , 0);

			setArrowRight(entityLiving, entityarrow);
			worldIn.spawnEntity(entityarrow);
    	} else {
      		EntityLivingBase player = entityLiving;

			vec anglevec = vec.fromAngles(Math.toRadians(angle), Math.toRadians(verticalangle)); //new vec(0,0,1).rotate_yaw(Math.toRadians(angle)).rotate_pitch(Math.toRadians(verticalangle));
      		anglevec = anglevec.rotate_pitch(Math.toRadians(-player.rotationPitch));
      		anglevec = anglevec.rotate_yaw(Math.toRadians(player.rotationYaw));
	        float velx = -MathHelper.sin((float) anglevec.getYaw() * 0.017453292F) * MathHelper.cos((float) anglevec.getPitch() * 0.017453292F);
	        float vely = -MathHelper.sin((float) anglevec.getPitch() * 0.017453292F);
	        float velz = MathHelper.cos((float) anglevec.getYaw() * 0.017453292F) * MathHelper.cos((float) anglevec.getPitch() * 0.017453292F);
			grappleArrow entityarrow = this.createarrow(stack, worldIn, entityLiving, true, true);//new grappleArrow(worldIn, player, true);
//            entityarrow.shoot(player, (float) anglevec.getPitch(), (float)anglevec.getYaw(), 0.0F, entityarrow.getVelocity(), 0.0F);
	        float extravelocity = (float) vec.motionvec(entityLiving).dist_along(new vec(velx, vely, velz));
	        if (extravelocity < 0) { extravelocity = 0; }
			entityarrow.shoot((double) velx, (double) vely, (double) velz, entityarrow.getVelocity() + extravelocity, 0.0F);
			worldIn.spawnEntity(entityarrow);
			setArrowRight(entityLiving, entityarrow);
		}
	}
	
	public void detachBoth(EntityLivingBase entityLiving) {
		grappleArrow arrow1 = getArrowLeft(entityLiving);
		grappleArrow arrow2 = getArrowRight(entityLiving);

		setArrowLeft(entityLiving, null);
		setArrowRight(entityLiving, null);
		
		if (arrow1 != null) {
			arrow1.removeServer();
		}
		if (arrow2 != null) {
			arrow2.removeServer();
		}

		int id = entityLiving.getEntityId();
		grapplemod.sendtocorrectclient(new GrappleDetachMessage(id), id, entityLiving.world);

		if (grapplemod.attached.contains(id)) {
			grapplemod.attached.remove(new Integer(id));
		}
	}

	public void detachLeft(EntityLivingBase entityLiving, ItemStack stack) {
		grappleArrow arrow1 = getArrowLeft(entityLiving);
		setArrowLeft(entityLiving, null);
		
		if (arrow1 != null) {
			vec ar = new vec(arrow1.posX, arrow1.posY, arrow1.posZ);
			vec pl = new vec(entityLiving.posX, entityLiving.posY, entityLiving.posZ);
			int dlen = Math.abs((int) ar.sub(pl).length());
			if (!arrow1.isAttached){
				vec vecplayer = new vec(entityLiving.posX, entityLiving.posY, entityLiving.posZ);;
				vec vecweb = new vec (arrow1.posX, arrow1.posY, arrow1.posZ);
				if (vecweb.sub(vecplayer).length() < 22){
					WebBall entitySnowball = new WebBall(entityLiving.world, arrow1.posX, arrow1.posY, arrow1.posZ);
					entitySnowball.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, 3F, 1.0F);
					entityLiving.world.spawnEntity(entitySnowball);
					dlen = 2;
				}
			}
			if (arrow1.isEnd){
				ResourceLocation resourceLocation = new ResourceLocation("thwip", "thwipend");
				grapplemod.proxy.playThwipContinue(resourceLocation, 1);
				arrow1.isEnd = false;
			}
			leftLenWeb = leftLenWeb - dlen;
			if (leftLenWeb <= 0){
				leftLenWeb =1;
			}
			setDamage(stack, (leftLenWeb + rightLenWeb));
			arrow1.removeServer();
		}
		
		int id = entityLiving.getEntityId();
		
		// remove controller if hook is attached
		if (getArrowRight(entityLiving) == null) {
			grapplemod.sendtocorrectclient(new GrappleDetachMessage(id), id, entityLiving.world);
		} else {
			grapplemod.sendtocorrectclient(new DetachSingleHookMessage(id, arrow1.getEntityId()), id, entityLiving.world);
		}
		
		if (grapplemod.attached.contains(id)) {
			grapplemod.attached.remove(new Integer(id));
		}
	}
	
	public void detachRight(EntityLivingBase entityLiving, ItemStack stack) {
		grappleArrow arrow2 = getArrowRight(entityLiving);

		
		setArrowRight(entityLiving, null);
		if (arrow2 != null) {
			vec ar = new vec(arrow2.posX, arrow2.posY, arrow2.posZ);
			vec pl = new vec(entityLiving.posX, entityLiving.posY, entityLiving.posZ);
			int dlen = Math.abs((int) ar.sub(pl).length());
			if (!arrow2.isAttached){
				vec vecplayer = new vec (entityLiving.posX, entityLiving.posY, entityLiving.posZ);
				vec vecweb = new vec (arrow2.posX, arrow2.posY, arrow2.posZ);
				if (vecweb.sub(vecplayer).length() < 22){
					WebBall entitySnowball = new WebBall(entityLiving.world, arrow2.posX, arrow2.posY, arrow2.posZ);
					entitySnowball.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, 3F, 1.0F);
					//entitySnowball.shoot(0, 1, 1, 3F, 0F);
					entityLiving.world.spawnEntity(entitySnowball);
					dlen = 2;
				}
			}
			if (arrow2.isEnd){
				ResourceLocation resourceLocation = new ResourceLocation("thwip", "thwipend");
				grapplemod.proxy.playThwipContinue(resourceLocation, 1);
				arrow2.isEnd = false;
			}
			rightLenWeb = rightLenWeb - dlen;
			if (rightLenWeb <= 0){
				rightLenWeb =1;
			}
			setDamage(stack, rightLenWeb);
			arrow2.removeServer();
		}
		
		int id = entityLiving.getEntityId();
		
		// remove controller if hook is attached
		if (getArrowLeft(entityLiving) == null) {
			grapplemod.sendtocorrectclient(new GrappleDetachMessage(id), id, entityLiving.world);
		} else {
			grapplemod.sendtocorrectclient(new DetachSingleHookMessage(id, arrow2.getEntityId()), id, entityLiving.world);
		}
		
		if (grapplemod.attached.contains(id)) {
			grapplemod.attached.remove(new Integer(id));
		}
	}
	
    public double getAngle(EntityLivingBase entity, ItemStack stack) {
    	GrappleCustomization custom = this.getCustomization(stack);
    	if (entity.isSneaking()) {
    		return custom.sneakingangle;
    	} else {
    		return custom.angle;
    	}
    }
	
	public grappleArrow createarrow(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand, boolean isdouble) {
		grappleArrow arrow = new grappleArrow(worldIn, entityLiving, righthand, this.getCustomization(stack), isdouble);
		grapplemod.addarrow(entityLiving.getEntityId(), arrow);
		return arrow;
	}
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityLiving, EnumHand hand) {
    	ItemStack stack = entityLiving.getHeldItem(hand);
        if (!worldIn.isRemote) {
	        this.dorightclick(stack, worldIn, entityLiving, hand == EnumHand.MAIN_HAND);
        }
        entityLiving.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
	public static boolean isLeftDown = false;

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn,
			EntityLivingBase entityLiving, int timeLeft) {
		if (!worldIn.isRemote) {
//			stack.getSubCompound("grapplemod", true).setBoolean("extended", (this.getArrow(entityLiving, worldIn) != null));
		}
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.NONE;
	}

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	return true;
    }
    public static boolean modeSpider = false;
	@Override
	public void onCustomKeyDown(ItemStack stack, EntityPlayer player, Keys key, boolean ismainhand) {
		World world = player.world;
		if (key == Keys.THROWBOTH){
			if (player.world.isRemote){
					for (ItemStack itemStack:player.inventory.mainInventory){
						if (itemStack.getItem() == grapplemod.biocable){
							int i = (int) (bioCable.dlen);
							if (maxdlenWeb - (leftLenWeb + rightLenWeb) <= 10){
								leftLenWeb = maxdlenWeb/2;
								rightLenWeb = maxdlenWeb/2;
								setDamage(stack,leftLenWeb + rightLenWeb);
								player.sendMessage(new TextComponentString("Web-shooters are full"));
							}
							else {
								if (rightLenWeb >= leftLenWeb){
									leftLenWeb = leftLenWeb + i;
									if (leftLenWeb > maxdlenWeb/2){
										leftLenWeb = maxdlenWeb/2;
									}
								}
								else {
									rightLenWeb = rightLenWeb + i;
									if (rightLenWeb > maxdlenWeb/2){
										rightLenWeb = maxdlenWeb/2;
									}
								}
								itemStack.setCount(itemStack.getCount()-1);
								setDamage(stack,leftLenWeb + rightLenWeb);
								playThwipChange();
								player.sendMessage(new TextComponentString("Web-shooters are recharged " + (rightLenWeb + leftLenWeb) + "m"));
							}

						}
					}
			}

		}
		if (key == Keys.ROCKET){
			playThwipMode();
			if (modeSpider){
				modeSpider = false;
				player.sendMessage(new TextComponentString("THWIP mode " + modeSpider));
			}
			else {
				modeSpider = true;
				player.sendMessage(new TextComponentString("THWIP mode " + modeSpider));
			}
		}
		if (modeSpider){
			if (player.world.isRemote) {
				if (key == Keys.LAUNCHER) {
					if (ClientProxyClass.modeRealism){
						ClientProxyClass.modeRealism = false;
					}
					else {
						ClientProxyClass.modeRealism = true;
					}
					player.sendMessage(new TextComponentString("Inertia " + ClientProxyClass.modeRealism));
				} else if (key == Keys.THROWLEFT || key == Keys.THROWRIGHT || key == Keys.THROWBOTH) {
					grapplemod.network.sendToServer(new KeypressMessage(key, true));
				} else if (key == Keys.ROCKET) {
					GrappleCustomization custom = this.getCustomization(stack);
					if (custom.rocket) {
						grapplemod.proxy.startrocket(player, custom);
					}
				}
			}
			else {
				if (key == Keys.THROWLEFT) {
					if ((leftLenWeb) > 15){
						grappleArrow arrow1 = getArrowLeft(player);

						if (arrow1 != null) {
							detachLeft(player, stack);
							return;
						}

						boolean threw = throwLeft(stack, world, player, ismainhand);
					}
					isLeftDown = true;
					if (leftLenWeb < 10){
						playNotHas(world,player);
					}
					else{
						playThwipStart(world, player);
					}
				}
				else if (key == Keys.THROWRIGHT) {
					if ((rightLenWeb) > 15) {
						grappleArrow arrow2 = getArrowRight(player);
						if (arrow2 != null) {
							detachRight(player, stack);
							return;
						}
						throwRight(stack, world, player, ismainhand);
					}
					if (rightLenWeb < 10){
						playNotHas(world,player);
					}
					else{
						playThwipStart(world, player);
					}
				}
			}
		}
		else {
		}
	}
	
	@Override
	public void onCustomKeyUp(ItemStack stack, EntityPlayer player, Keys key, boolean ismainhand) {
		if (player.world.isRemote) {
			if (key == Keys.THROWLEFT || key == Keys.THROWRIGHT || key == Keys.THROWBOTH) {
				grapplemod.network.sendToServer(new KeypressMessage(key, false));
			}
		}
		else {
			if (!modeSpider){
			}
			else {
				GrappleCustomization custom = this.getCustomization(stack);
				if (custom.detachonkeyrelease) {
					grappleArrow arrow_left = getArrowLeft(player);
					grappleArrow arrow_right = getArrowRight(player);

					if (key == Keys.THROWBOTH) {
						detachBoth(player);
					} else if (key == Keys.THROWLEFT) {
						if (arrow_left != null){
							detachLeft(player, stack);
							playThwipEnd(player.world, player);
							isLeftDown = false;

						}
					} else if (key == Keys.THROWRIGHT) {
						if (arrow_right != null){
							detachRight(player, stack);
							playThwipEnd(player.world, player);
						}
					}
				}
			}
		}
	}

	public void playThwipEnd(World world, EntityPlayer player){
		try {
			ResourceLocation loc = new ResourceLocation("thwip", "thwiprelease");
			world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(loc), SoundCategory.PLAYERS, 1F, 1F);
		}
		catch (Exception e){}
	}

	public void playThwipStart (World world, EntityPlayer player) {
		try {
			double i = Math.random();
			if (i > 0.5){
				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipstartc1")), SoundCategory.PLAYERS, 1F, 1F);
			}
			else {
				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipstartc2")), SoundCategory.PLAYERS, 1F, 1F);
			}
		}
		catch (Exception e){}
	}

	public void playNotHas (World world, EntityPlayer player) {
		try {
			if (!world.isRemote){
				world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, new SoundEvent(new ResourceLocation("thwip", "thwipnothas")), SoundCategory.PLAYERS, 1F, 1F);
			}
		}
		catch (Exception e){}
	}

	public void playThwipChange () {
		try {
			ResourceLocation loc = new ResourceLocation("thwip", "thwipchange");
			EntityPlayer player = Minecraft.getMinecraft().player;
			Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(loc, SoundCategory.PLAYERS, 1, 1.0F, false, 0, ISound.AttenuationType.NONE, (float) player.posX, (float) player.posY, (float) player.posZ));
		}
		catch (Exception e){}
	}
	public void playThwipMode () {
		try {
			EntityPlayer player = Minecraft.getMinecraft().player;
			ResourceLocation loc = new ResourceLocation("thwip", "thwipmode");
			Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(loc, SoundCategory.PLAYERS, 1, 1.0F, false, 0, ISound.AttenuationType.NONE, (float) player.posX, (float) player.posY, (float) player.posZ));
		}
		catch (Exception e){}
	}
   
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	return true;
    }
   
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos k, EntityPlayer player)
    {
      return true;
    }
    
    public GrappleCustomization getCustomization(ItemStack itemstack) {

    		GrappleCustomization custom = this.getDefaultCustomization();

    		
    		return custom;

    }
    
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
		custom.maxlen = 200;
		custom.throwspeed = 6;
		custom.reelin = false;
		custom.setDouble("sc", 0.035 * Math.pow(10, 9));

		return custom;
    }

	@Nullable
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		ClassicWebShooterModel modelCustomWebShooter = new ClassicWebShooterModel();
		modelCustomWebShooter.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;

		modelCustomWebShooter.isChild = _default.isChild;
		modelCustomWebShooter.isRiding = _default.isRiding;
		modelCustomWebShooter.isSneak = _default.isSneak;
		modelCustomWebShooter.rightArmPose = _default.rightArmPose;
		modelCustomWebShooter.leftArmPose = _default.leftArmPose;
		onPlayerBaubleRender(itemStack, (EntityPlayer) entityLiving, RenderType.BODY, 12);

		return modelCustomWebShooter;
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "thwip:textures/models/armor/webshooter3.png";
	}

	
	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getDefaultInstance()
    {
        ItemStack stack = new ItemStack(this);
        this.getCustomization(stack);
        return stack;
    }
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
        	ItemStack stack = new ItemStack(this);
        	this.getCustomization(stack);
            items.add(stack);
        }
    }
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		int id = player.getEntityId();
		grapplemod.sendtocorrectclient(new GrappleDetachMessage(id), id, player.world);
		
		if (grapplemod.attached.contains(id)) {
			grapplemod.attached.remove(id);
		}
		
		if (ClientProxyClass.grapplearrows1.containsKey(player)) {
			grappleArrow arrow1 = ClientProxyClass.grapplearrows1.get(player);
			setArrowLeft(player, null);
			if (arrow1 != null) {
				arrow1.removeServer();
			}
		}
		
		if (ClientProxyClass.grapplearrows2.containsKey(player)) {
			grappleArrow arrow2 = ClientProxyClass.grapplearrows2.get(player);
			setArrowLeft(player, null);
			if (arrow2 != null) {
				arrow2.removeServer();
			}
		}
		
		return super.onDroppedByPlayer(item, player);
	}

	@Override
	public baubles.api.BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.TRINKET;
	}

	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
	}
}
