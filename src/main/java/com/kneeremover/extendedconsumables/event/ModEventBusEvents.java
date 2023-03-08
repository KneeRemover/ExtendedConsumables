package com.kneeremover.extendedconsumables.event;

import com.kneeremover.extendedconsumables.ExtendedConsumables;

import com.kneeremover.extendedconsumables.recipe.ConsumableTableRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = ExtendedConsumables.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
	@SubscribeEvent
	public static void registerRecipeTypes(final RegistryEvent .Register<RecipeSerializer<?>> event) {
		Registry.register(Registry.RECIPE_TYPE, ConsumableTableRecipe.Type.ID, ConsumableTableRecipe.Type.INSTANCE);
	}
}
