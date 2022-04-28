package com.yyon.grapplinghook;

import java.util.*;

import com.yyon.grapplinghook.controllers.grappleController;
import com.yyon.grapplinghook.enchantments.DoublejumpEnchantment;
import com.yyon.grapplinghook.enchantments.SlidingEnchantment;
import com.yyon.grapplinghook.enchantments.WallrunEnchantment;
import com.yyon.grapplinghook.entities.Web;
import com.yyon.grapplinghook.entities.grappleArrow;
import com.yyon.grapplinghook.items.*;
import com.yyon.grapplinghook.items.KeypressItem.Keys;

import com.yyon.grapplinghook.network.DetachSingleHookMessage;
import com.yyon.grapplinghook.network.GrappleAttachMessage;
import com.yyon.grapplinghook.network.GrappleAttachPosMessage;
import com.yyon.grapplinghook.network.GrappleDetachMessage;
import com.yyon.grapplinghook.network.GrappleEndMessage;
import com.yyon.grapplinghook.network.KeypressMessage;
import com.yyon.grapplinghook.network.LoggedInMessage;
import com.yyon.grapplinghook.network.PlayerMovementMessage;
import com.yyon.grapplinghook.network.SegmentMessage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

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

//TODO
// Pull mobs
// Attach 2 things together
// wallrun on diagonal walls
// smart motor acts erratically when aiming above hook
// key events

@Mod(modid = grapplemod.MODID, version = grapplemod.VERSION)
public class grapplemod {

	public grapplemod(){}

    public static final String MODID = "thwip";
    
    public static final String VERSION = "1.12.2-v12";
	public static Item biocable;

	public static Item classicwebshooter;
	public static Item classicwebshooter2;

	public static Item WebShooterTasm;
    public static Item launcheritem;
    public static Item repelleritem;

    public static Item baseupgradeitem;

    public static Item longfallboots;
    
    public static final EnumEnchantmentType GRAPPLEENCHANTS_FEET = EnumHelper.addEnchantmentType("GRAPPLEENCHANTS_FEET", (item) -> item instanceof ItemArmor && ((ItemArmor)item).armorType == EntityEquipmentSlot.FEET);
    
    public static WallrunEnchantment wallrunenchantment;
    public static DoublejumpEnchantment doublejumpenchantment;
    public static SlidingEnchantment slidingenchantment;

	public static Object instance;
	
	public static SimpleNetworkWrapper network;


	public static HashMap<Integer, grappleController> controllers = new HashMap<Integer, grappleController>(); // client side
	public static HashMap<BlockPos, grappleController> controllerpos = new HashMap<BlockPos, grappleController>();
	public static HashSet<Integer> attached = new HashSet<Integer>(); // server side
	
	public static HashMap<Integer, HashSet<grappleArrow>> allarrows = new HashMap<Integer, HashSet<grappleArrow>>(); // server side
	
	private static int controllerid = 0;
	public static int GRAPPLEID = controllerid++;
	public static int REPELID = controllerid++;
	public static int AIRID = controllerid++;
		
	public static boolean anyblocks = true;
	public static HashSet<Block> grapplingblocks;
	public static boolean removeblocks = false;
	public static HashSet<Block> grapplingbreaksblocks;
	public static boolean anybreakblocks = false;
	public static HashSet<Block> grapplingignoresblocks;
	public static boolean anyignoresblocks = false;
	
	public static Block blockGrappleModifier;
	public static ItemBlock itemBlockGrappleModifier;
	
	public ResourceLocation resourceLocation;

	
	public static final CreativeTabs tabGrapplemod = (new CreativeTabs("tabThwipmod") {
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> items) {
			// sort items
			super.displayAllRelevantItems(items);
			Item[] allitems = getAllItems();
			Collections.sort(items, new Comparator<ItemStack>() {
				@Override
				public int compare(ItemStack arg0, ItemStack arg1) {
					return getIndex(arg0.getItem()) - getIndex(arg1.getItem());
				}
				public int getIndex(Item item) {
					int i = 0;
					for (Item item2 : allitems) {
						if (item == item2) {
							return i;
						}
						i++;
					}
					return i;
				}
			});
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(classicwebshooter);
		}
	});
	
	@SidedProxy(clientSide="com.yyon.grapplinghook.ClientProxyClass", serverSide="com.yyon.grapplinghook.ServerProxyClass")
	public static CommonProxyClass proxy;

	public static HashSet<Block> stringToBlocks(String s) {
		HashSet<Block> blocks = new HashSet<Block>();
		
		if (s.equals("") || s.equals("none") || s.equals("any")) {
			return blocks;
		}
		
		String[] blockstr = s.split(",");
		
	    for(String str:blockstr){
	    	str = str.trim();
	    	String modid;
	    	String name;
	    	if (str.contains(":")) {
	    		String[] splitstr = str.split(":");
	    		modid = splitstr[0];
	    		name = splitstr[1];
	    	} else {
	    		modid = "minecraft";
	    		name = str;
	    	}
	    	
	    	Block b = Block.REGISTRY.getObject(new ResourceLocation(modid, name));
	    	
	    	blocks.add(b);
	    }
	    
	    return blocks;
	}
	
	public static void updateGrapplingBlocks() {
		String s = GrappleConfig.getconf().grapplingBlocks;
		if (s.equals("any") || s.equals("")) {
			s = GrappleConfig.getconf().grapplingNonBlocks;
			if (s.equals("none") || s.equals("")) {
				anyblocks = true;
			} else {
				anyblocks = false;
				removeblocks = true;
			}
		} else {
			anyblocks = false;
			removeblocks = false;
		}
	
		if (!anyblocks) {
			grapplingblocks = stringToBlocks(s);
		}

		grapplingbreaksblocks = stringToBlocks(GrappleConfig.getconf().grappleBreakBlocks);
		anybreakblocks = grapplingbreaksblocks.size() != 0;
		
		grapplingignoresblocks = stringToBlocks(GrappleConfig.getconf().grappleIgnoreBlocks);
		anyignoresblocks = grapplingignoresblocks.size() != 0;
		
	}

	@SubscribeEvent
	public void deleteWeb(WorldEvent.Save event){
		for (Web web: grappleArrow.webArrayList){
			Block blk = Blocks.AIR;
			// Make a position.
			IBlockState state0=blk.getDefaultState();
			event.getWorld().setBlockState(web.blockPos, state0);
		}
	}

	@SubscribeEvent
	public void deleteWeb2(WorldEvent.Unload event){
		for (Web web: grappleArrow.webArrayList){
			Block blk = Blocks.AIR;
			// Make a position.
			IBlockState state0=blk.getDefaultState();
			event.getWorld().setBlockState(web.blockPos, state0);
		}
	}
	@SubscribeEvent
	@EventHandler
	public void deleteWeb3(PlayerEvent.PlayerChangedDimensionEvent event){
		for (Web web: grappleArrow.webArrayList){
			Block blk = Blocks.AIR;
			// Make a position.
			IBlockState state0=blk.getDefaultState();
			event.player.world.setBlockState(web.blockPos, state0);
		}
	}
	@SubscribeEvent
	@EventHandler
	public void deleteWeb3(PlayerEvent.PlayerLoggedOutEvent event){
		for (Web web: grappleArrow.webArrayList){
			Block blk = Blocks.AIR;
			// Make a position.
			IBlockState state0=blk.getDefaultState();
			event.player.world.setBlockState(web.blockPos, state0);
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
	    event.getRegistry().registerAll(getAllItems());

	}
	
	public static Item[] getAllItems() {
		return new Item[] {
				biocable,
				classicwebshooter,
				classicwebshooter2,
				WebShooterTasm
		};
	}

	public static final ItemArmor.ArmorMaterial ARMOR_MATERIAL = EnumHelper.addArmorMaterial("tut:armor", "tut:armor", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		int webdlen = 1424;
		biocable = new BioCable(webdlen);
		biocable.setRegistryName("biocable");



		classicwebshooter = new ClassicWebShooter("grapplebowArmor", ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, new BioCable(webdlen));
		classicwebshooter.setRegistryName("classicwebshooter");

		classicwebshooter2 = new ClassicWebShooter2("grapplebowArmor", ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, new BioCable(webdlen));
		classicwebshooter2.setRegistryName("classicwebshooter2");

		WebShooterTasm = new WebShooterTasm("grapplebowArmor", ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, new BioCable(webdlen));
		WebShooterTasm.setRegistryName("webShooterTasm");

		resourceLocation = new ResourceLocation(grapplemod.MODID, "thwip");
		
		registerEntity(grappleArrow.class, "grappleArrow");
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel("grapplemodchannel");
		byte id = 0;
		network.registerMessage(PlayerMovementMessage.Handler.class, PlayerMovementMessage.class, id++, Side.SERVER);
		network.registerMessage(GrappleAttachMessage.Handler.class, GrappleAttachMessage.class, id++, Side.CLIENT);
		network.registerMessage(GrappleEndMessage.Handler.class, GrappleEndMessage.class, id++, Side.SERVER);
		network.registerMessage(GrappleDetachMessage.Handler.class, GrappleDetachMessage.class, id++, Side.CLIENT);
		network.registerMessage(DetachSingleHookMessage.Handler.class, DetachSingleHookMessage.class, id++, Side.CLIENT);
		network.registerMessage(GrappleAttachPosMessage.Handler.class, GrappleAttachPosMessage.class, id++, Side.CLIENT);
		network.registerMessage(SegmentMessage.Handler.class, SegmentMessage.class, id++, Side.CLIENT);
		network.registerMessage(LoggedInMessage.Handler.class, LoggedInMessage.class, id++, Side.CLIENT);
		network.registerMessage(KeypressMessage.Handler.class, KeypressMessage.class, id++, Side.SERVER);
		

	    MinecraftForge.EVENT_BUS.register(this);
	    
		proxy.preInit(event);
		
		tabGrapplemod.setRelevantEnchantmentTypes(GRAPPLEENCHANTS_FEET);

		CraftingRegister.register();

	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init(event, this);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		
		grapplemod.updateGrapplingBlocks();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new Command());
		System.out.println("Команда зарегестрирована");
	}
	
	int entityID = 0;
	public void registerEntity(Class<? extends Entity> entityClass, String name)
	{
		EntityRegistry.registerModEntity(resourceLocation, entityClass, name, entityID++, this, 900, 1, true);
	}
	
	public static void registerController(int entityId, grappleController controller) {
		if (controllers.containsKey(entityId)) {
			controllers.get(entityId).unattach();
		}
		controllers.put(entityId, controller);
	}
	
	public static void unregisterController(int entityId) {
		controllers.remove(entityId);
	}

	public static void receiveGrappleDetach(int id) {
		grappleController controller = controllers.get(id);
		if (controller != null) {
			controller.receiveGrappleDetach();
		}
	}
	
	public static void receiveGrappleDetachHook(int id, int hookid) {
		grappleController controller = controllers.get(id);
		if (controller != null) {
			controller.receiveGrappleDetachHook(hookid);
		}
	}

	public static void receiveEnderLaunch(int id, double x, double y, double z) {
		grappleController controller = controllers.get(id);
		if (controller != null) {
			controller.receiveEnderLaunch(x, y, z);
		}
	}
	
	public static void sendtocorrectclient(IMessage message, int playerid, World w) {
		Entity entity = w.getEntityByID(playerid);
		if (entity instanceof EntityPlayerMP) {
			grapplemod.network.sendTo(message, (EntityPlayerMP) entity);
		}
	}
	

	public static void removesubarrow(int id) {
		HashSet<Integer> arrowIds = new HashSet<Integer>();
		arrowIds.add(id);
		grapplemod.network.sendToServer(new GrappleEndMessage(-1, arrowIds));
	}

	public static void receiveGrappleEnd(int id, World world, HashSet<Integer> arrowIds) {
		if (grapplemod.attached.contains(id)) {
			grapplemod.attached.remove(new Integer(id));
		}
		else {
		}
		
		for (int arrowid : arrowIds) {
	      	Entity grapple = world.getEntityByID(arrowid);
	  		if (grapple instanceof grappleArrow) {
	  			((grappleArrow) grapple).removeServer();
	  		} else {
	
	  		}
		}
  		
  		Entity entity = world.getEntityByID(id);
  		if (entity != null) {
      		entity.fallDistance = 0;
  		}
  		
  		grapplemod.removeallmultihookarrows(id);
	}
	public static void addarrow(int id, grappleArrow arrow) {
		if (!allarrows.containsKey(id)) {
			allarrows.put(id, new HashSet<grappleArrow>());
		}
		allarrows.get(id).add(arrow);
	}
	
	public static void removeallmultihookarrows(int id) {
		if (!allarrows.containsKey(id)) {
			allarrows.put(id, new HashSet<grappleArrow>());
		}
		for (grappleArrow arrow : allarrows.get(id)) {
			if (arrow != null && !arrow.isDead) {
				arrow.removeServer();
			}
		}
		allarrows.put(id, new HashSet<grappleArrow>());
	}
	

	public static NBTTagCompound getstackcompound(ItemStack stack, String key) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound basecompound = stack.getTagCompound();
        if (basecompound.hasKey(key, 10))
        {
            return basecompound.getCompoundTag(key);
        }
        else
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            stack.setTagInfo(key, nbttagcompound);
            return nbttagcompound;
        }
	}
	

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
	    if(eventArgs.getModID().equals("thwip")){
			System.out.println("thwip config updated");
			ConfigManager.sync("thwip", Type.INSTANCE);;
			
			grapplemod.updateGrapplingBlocks();
		}
	}

	public static void receiveKeypress(EntityPlayer player, Keys key, boolean isDown) {
		System.out.println("flashing второй раз вызвался 0");
		if (player != null) {
			System.out.println("flashing второй раз вызвался 2");

			if (player.inventory.armorInventory.get(2).getItem() != null){
				if (player.inventory.armorInventory.get(2).getItem() instanceof ClassicWebShooter) {
					if (isDown) {
						((KeypressItem) player.inventory.armorInventory.get(2).getItem()).onCustomKeyDown(player.inventory.armorInventory.get(2), player, key, true);
					} else {
						((KeypressItem) player.inventory.armorInventory.get(2).getItem()).onCustomKeyUp(player.inventory.armorInventory.get(2), player, key, true);
					}
				}
			}
			if (ClientProxyClass.isUse){
				System.out.println("flashing второй раз 2");
				if (isDown) {
					System.out.println("flashing второй раз 3");
					((KeypressItem) ClientProxyClass.itemStack.getItem()).onCustomKeyDown(ClientProxyClass.itemStack, player, key, true);
				} else {
					((KeypressItem) ClientProxyClass.itemStack.getItem()).onCustomKeyUp(ClientProxyClass.itemStack, player, key, true);
				}
			}
		}
	}

	@SubscribeEvent
	public void deletedsfsd(LivingDeathEvent e){
		if(e.getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)e.getEntity();
			if (player.getName().equals(Minecraft.getMinecraft().getSession().getUsername())){
				ClientProxyClass.isUse = false;
				ClientProxyClass.itemStack = null;
			}
		}
	}

	public static Rarity getRarityFromInt(int rarity_int) {
		Rarity[] rarities = (new Rarity[] {Rarity.VERY_RARE, Rarity.RARE, Rarity.UNCOMMON, Rarity.COMMON});
		if (rarity_int < 0) {rarity_int = 0;}
		if (rarity_int >= rarities.length) {rarity_int = rarities.length-1;}
		return rarities[rarity_int];
	}
}
