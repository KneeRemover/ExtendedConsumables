package com.kneeremover.extendedconsumables.integration;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.recipe.ConsumableTableRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
@SuppressWarnings("[removal]")
public class ConsumableTableRecipeCategory implements IRecipeCategory<ConsumableTableRecipe> {
	public final static ResourceLocation UID = new ResourceLocation(ExtendedConsumables.MOD_ID, "consumable_crafting");
	public final static ResourceLocation TEXTURE =
			new ResourceLocation(ExtendedConsumables.MOD_ID, "textures/jei/consumable_table_gui.png");

	private final IDrawable background;
	private final IDrawable icon;

	public ConsumableTableRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CONSUMABLE_TABLE.get()));
	}

	@Override
	public RecipeType<ConsumableTableRecipe> getRecipeType() {
		return JEIExtendedConsumablesPlugin.CONSUMABLE_CRAFTING_TYPE;
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends ConsumableTableRecipe> getRecipeClass() {
		return ConsumableTableRecipe.class;
	}

	@Override
	public Component getTitle() {
		return new TextComponent("Consumable Crafting");
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setIngredients(ConsumableTableRecipe recipe, IIngredients ingredients) {
		IRecipeCategory.super.setIngredients(recipe, ingredients);
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ConsumableTableRecipe recipe, @Nonnull IFocusGroup focusGroup) {
		builder.addSlot(RecipeIngredientRole.INPUT, 78, 43).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 60, 18).addIngredients(recipe.getIngredients().get(1));
		builder.addSlot(RecipeIngredientRole.INPUT, 78, 18).addIngredients(recipe.getIngredients().get(2));
		builder.addSlot(RecipeIngredientRole.INPUT, 96, 18).addIngredients(recipe.getIngredients().get(3));
		builder.addSlot(RecipeIngredientRole.INPUT, 29, 34).addIngredients(Ingredient.of(ModItems.MODIFIER_PLACEHOLDER.get()));
		builder.addSlot(RecipeIngredientRole.INPUT, 29, 52).addIngredients(Ingredient.of(ModItems.MODIFIER_PLACEHOLDER.get()));
		builder.addSlot(RecipeIngredientRole.INPUT, 131, 42).addIngredients(Ingredient.of(Items.COAL.getDefaultInstance()));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 64).addItemStack(recipe.getResultItem());
	}
}
