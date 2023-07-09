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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
@JeiPlugin
public class JEIExtendedConsumablesPlugin implements IModPlugin {
	public static final RecipeType<ConsumableTableRecipe> CONSUMABLE_CRAFTING_TYPE = new RecipeType<>(ConsumableTableRecipeCategory.UID, ConsumableTableRecipe.class);

	@Override
	public @NotNull ResourceLocation getPluginUid() {
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
