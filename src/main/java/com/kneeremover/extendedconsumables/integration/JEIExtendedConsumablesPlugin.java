package com.kneeremover.extendedconsumables.integration;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.recipe.ConsumableTableRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIExtendedConsumablesPlugin implements IModPlugin {
	public static RecipeType<ConsumableTableRecipe> CONSUMABLE_CRAFTING_TYPE = new RecipeType<>(ConsumableTableRecipeCategory.UID, ConsumableTableRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(ExtendedConsumables.MOD_ID, "jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories((new ConsumableTableRecipeCategory(registration.getJeiHelpers().getGuiHelper())));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(ModBlocks.CONSUMABLE_TABLE.get().asItem().getDefaultInstance(), CONSUMABLE_CRAFTING_TYPE);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		// Add item descriptions
		registration.addIngredientInfo(ModItems.MODIFIER_PLACEHOLDER.get().getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.modifier_placeholder"));
		// Potions
		registration.addIngredientInfo(ModItems.LAST_STAND_POTION.get().getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.last_stand"));
		registration.addIngredientInfo(ModItems.STEP_HEIGHT_POTION.get().getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.step_height"));
		registration.addIngredientInfo(ModItems.SATURATION_OVERLOAD_POTION.get().getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.saturation_overload_potion"));
		// Food
		registration.addIngredientInfo(ModItems.ENVIRONMENTAL_APPLE.get().getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.environmental_apple"));
		// Vanilla
		registration.addIngredientInfo(Items.GUNPOWDER.getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.gunpowder"));
		registration.addIngredientInfo(Items.GLOWSTONE_DUST.getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.glowstone"));
		registration.addIngredientInfo(Items.REDSTONE.getDefaultInstance(), VanillaTypes.ITEM_STACK, new TranslatableComponent("item_description.extendedconsumables.redstone"));

		// Register recipe types
		RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

		List<ConsumableTableRecipe> recipes = rm.getAllRecipesFor(ConsumableTableRecipe.Type.INSTANCE);
		registration.addRecipes(CONSUMABLE_CRAFTING_TYPE, recipes);
	}
}
