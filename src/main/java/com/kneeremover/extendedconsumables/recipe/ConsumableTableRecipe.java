package com.kneeremover.extendedconsumables.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.util.ModifierUtils;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
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
	public boolean matches(@NotNull SimpleContainer pContainer, @NotNull Level pLevel) {
		ItemStack[] types = recipeItems.get(0).getItems();
		ItemStack[] ing1s = recipeItems.get(1).getItems();
		ItemStack[] ing2s = recipeItems.get(2).getItems();
		ItemStack[] ing3s = recipeItems.get(3).getItems();
		for (int i = 0; i < types.length; i++) {
			ItemStack type = types[i];
			ItemStack[] ings = new ItemStack[3];
			ings[0] = ing1s.length > i ? ing1s[i] : null;
			ings[1] = ing2s.length > i ? ing2s[i] : null;
			ings[2] = ing3s.length > i ? ing3s[i] : null;
			Item containerIng1 = pContainer.getItem(1).getItem();
			Item containerIng2 = pContainer.getItem(2).getItem();
			Item containerIng3 = pContainer.getItem(3).getItem();

			int matches = 0;

			if (type.is(pContainer.getItem(0).getItem())) {
				matches++;
			}

			for (int j = 0; j < 3; j++) {
				if (ings[j] == null ? containerIng1 == Items.AIR : ings[j].is(containerIng1)) {
					matches++;
					containerIng1 = null;
					continue;
				}
				if (ings[j] == null ? containerIng2 == Items.AIR : ings[j].is(containerIng2)) {
					matches++;
					containerIng2 = null;
					continue;
				}
				if (ings[j] == null ? containerIng3 == Items.AIR : ings[j].is(containerIng3)) {
					matches++;
					containerIng3 = null;
				}
			}
			boolean mods = ModifierUtils.isModsValid(pContainer.getItem(0), pContainer.getItem(4), pContainer.getItem(5));
			if (matches == 4 && mods) {
				return true;
			}
		}
		return false;
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
