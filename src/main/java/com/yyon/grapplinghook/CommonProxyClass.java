package com.yyon.grapplinghook;

import java.lang.reflect.Method;
import java.util.HashSet;

import com.yyon.grapplinghook.controllers.grappleController;
import com.yyon.grapplinghook.entities.Web;
import com.yyon.grapplinghook.entities.grappleArrow;
import com.yyon.grapplinghook.network.GrappleDetachMessage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxyClass {
	public enum keys {
		keyBindUseItem,
		keyBindForward,
		keyBindLeft,
		keyBindBack,
		keyBindRight,
		keyBindJump,
		keyBindSneak,
		keyBindAttack
	}

	Method capturePosition = null;


	
	public void preInit(FMLPreInitializationEvent event) {
	    MinecraftForge.EVENT_BUS.register(this);

    	if (capturePosition == null) {
    		System.out.println("Error: could not access capturePosition function");
    	}
}

	public void init(FMLInitializationEvent event, grapplemod grapplemod) {
		
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	public void sendplayermovementmessage(grappleArrow grappleArrow, int playerid, int arrowid) {
	}

	public void getplayermovement(grappleController control, int playerid) {
	}
	
//	@SubscribeEvent
//	public void onLivingFallEvent(LivingFallEvent event)
//	{
//		if (event.getEntity() != null && grapplemod.attached.contains(event.getEntity().getEntityId()))
//		{
//			event.setCanceled(true);
//		}
//	}
	
	
	public void resetlaunchertime(int playerid) {
	}

	public void launchplayer(EntityPlayer player) {
	}
	
	public boolean isSneaking(Entity entity) {
		return entity.isSneaking();
	}
	
    @SubscribeEvent
    public void onBlockBreak(BreakEvent event){
    	EntityPlayer player = event.getPlayer();
    	if (player != null) {

    	}
    	
    	this.blockbreak(event);
    }
    
    
    public void blockbreak(BreakEvent event) {
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
    	if (event.getSource() == DamageSource.IN_WALL) {
    		if (grapplemod.attached.contains(event.getEntity().getEntityId())) {
    			event.setCanceled(true);
    		}
    	}
    }

	@SubscribeEvent
	public void dssd(RenderPlayerEvent event){
		event.getRenderer().getMainModel().bipedLeftArm.rotateAngleZ = 10;
		event.getRenderer().getMainModel().bipedLeftArm.rotateAngleY = 10;
		event.getRenderer().getMainModel().bipedLeftArm.rotateAngleX = 10;

	}
    
	public String getkeyname(CommonProxyClass.keys keyenum) {
		return null;
	}

	
	public String localize(String string) {
		return string;
	}

	public void startrocket(EntityPlayer player, GrappleCustomization custom) {
	}
	
	public void updateRocketRegen(double rocket_active_time, double rocket_refuel_ratio) {
	}

	public double getRocketFunctioning() {
		return 0;
	}

	public boolean iswallrunning(Entity entity, vec motion) {
		return false;
	}
	
	public boolean issliding(Entity entity, vec motion) {
		return false;
	}
	
	public Method getCapturePositionMethod() {
		return capturePosition;
	}
	
	public grappleController createControl(int id, int arrowid, int entityid, World world, vec pos, BlockPos blockpos, GrappleCustomization custom) {
		return null;
	}

	public void playSlideSound(Entity entity) {
	}

	public void playThwipContinue(ResourceLocation loc, float volume) {
	}


	
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
		Entity entity = event.getEntity();
		int id = entity.getEntityId();
		boolean isconnected = grapplemod.allarrows.containsKey(id);
		if (isconnected) {
			HashSet<grappleArrow> arrows = grapplemod.allarrows.get(id);
			for (grappleArrow arrow: arrows) {
				arrow.removeServer();
			}
			arrows.clear();

			grapplemod.attached.remove(id);
			
			if (grapplemod.controllers.containsKey(id)) {
				grapplemod.controllers.remove(id);
			}
			
			grapplemod.sendtocorrectclient(new GrappleDetachMessage(id), id, entity.world);
		}
	}

	public void playWallrunJumpSound(Entity entity) {
	}

	public void playSound(ResourceLocation loc, float volume) {
	}
}
