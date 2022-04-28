package com.yyon.grapplinghook;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;

public class CraftingRegister {
    public static void register() {
        registerRecipes("wsf");
        registerRecipes("wsf2");
        registerRecipes("wsf3");
    }


    private static void registerRecipes(String name) {
        CraftingHelper.register(new ResourceLocation(grapplemod.MODID, name), (IRecipeFactory) (context, json) -> CraftingHelper.getRecipe(json, context));
    }
}
