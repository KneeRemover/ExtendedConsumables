package com.kneeremover.extendedconsumables.event;

import com.kneeremover.extendedconsumables.effect.capabilities.PlayerSaturationOverload;
import com.kneeremover.extendedconsumables.recipe.ConsumableTableRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventBusEvents {
	@SubscribeEvent
	public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
		Registry.register(Registry.RECIPE_TYPE, ConsumableTableRecipe.Type.ID, ConsumableTableRecipe.Type.INSTANCE);
	}

	@SubscribeEvent
	public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
		event.register(PlayerSaturationOverload.class);
	}
}
