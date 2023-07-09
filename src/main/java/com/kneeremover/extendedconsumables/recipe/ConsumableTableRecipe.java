package com.kneeremover.extendedconsumables.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.ModifierHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ConsumableTableRecipe implements Recipe<SimpleContainer> {
	private final ResourceLocation id;
	private final ItemStack output;
	private final NonNullList<Ingredient> recipeItems;

	public ConsumableTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
		this.id = id;
		this.output = output;
		this.recipeItems = recipeItems;
	}

	@Override
	public boolean matches(SimpleContainer pContainer, @NotNull Level pLevel) {
		List<Integer> recipeItemsList = new LinkedList<>(Arrays.asList(1, 2, 3));
		List<ItemStack> containedItems = new LinkedList<>(Arrays.asList(pContainer.getItem(1),
				pContainer.getItem(2), pContainer.getItem(3)));
		List<Integer> recipeItemsListConsumed = new LinkedList<>();
		List<ItemStack> containedItemsConsumed = new LinkedList<>();

		// Don't allow modifiers to count for the recipe type. That sentance makes no sense, but its early in the morning and i don't care lol.
		if (recipeItems.get(0).test(pContainer.getItem(0))) {
			recipeItemsListConsumed.add(0);
		}
		// Loops through each item in the recipe. For each one, loops through each item in the container. If there is a match, removes both the item in the recipe
		// and the item in the slot from consideration. If every item in the recipe is removed, confirms the recipe.
		for (Integer target:recipeItemsList) {
			if (recipeItems.get(target).isEmpty()) { // Ignore empty requirements
				recipeItemsListConsumed.add(target);
				continue;
			}
			for (ItemStack stack:containedItems) {
				if (containedItemsConsumed.contains(stack)) { // Skip if the item has already been "consumed"
					continue;
				}
				if (recipeItems.get(target).test(stack)) {
					containedItemsConsumed.add(stack);
					recipeItemsListConsumed.add(target);
					break;
				}
			}
		}
		boolean mods = ModifierHandler.isModsValid(pContainer.getItem(0), pContainer.getItem(4), pContainer.getItem(5));
		return recipeItemsListConsumed.size() == 4 && mods;
	}

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return recipeItems;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public @NotNull ItemStack getResultItem() {
		return output.copy();
	}

	@Override
	public @NotNull ResourceLocation getId() {
		return id;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}


	@SuppressWarnings("SameParameterValue")
	public static class Serializer implements RecipeSerializer<ConsumableTableRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID =
				new ResourceLocation(ExtendedConsumables.MOD_ID,"consumable_crafting");

		@Override
		public @NotNull ConsumableTableRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(4, Ingredient.EMPTY);

			for (int i = 0; i < ingredients.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}

			return new ConsumableTableRecipe(id, output, inputs);
		}

		@Override
		public ConsumableTableRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

			inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));

			ItemStack output = buf.readItem();
			return new ConsumableTableRecipe(id, output, inputs);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, ConsumableTableRecipe recipe) {
			buf.writeInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				ing.toNetwork(buf);
			}
			buf.writeItemStack(recipe.getResultItem(), false);
		}

		@Override
		public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
			return INSTANCE;
		}

		@Nullable
		@Override
		public ResourceLocation getRegistryName() {
			return ID;
		}

		@Override
		public Class<RecipeSerializer<?>> getRegistryType() {
			return Serializer.castClass(RecipeSerializer.class);
		}

		@SuppressWarnings("unchecked") // Need this wrapper, because generics
		private static <G> Class<G> castClass(Class<?> cls) {
			return (Class<G>)cls;
		}
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	public static class Type implements RecipeType<ConsumableTableRecipe> {
		private Type() { }
		public static final Type INSTANCE = new Type();
		public static final String ID = "consumable_crafting";
	}
}
