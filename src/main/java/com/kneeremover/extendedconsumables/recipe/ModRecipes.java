package com.kneeremover.extendedconsumables.recipe;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<RecipeSerializer<ConsumableTableRecipe>> CONSUMABLE_TABLE_SERIALIZER = SERIALIZERS.register("consumable_crafting", () -> ConsumableTableRecipe.Serializer.INSTANCE);

	public static void register(IEventBus eventBus) {
		SERIALIZERS.register(eventBus);
	}
}
