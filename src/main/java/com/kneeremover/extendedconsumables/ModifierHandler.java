package com.kneeremover.extendedconsumables;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.item.Items.*;

public class ModifierHandler {
	public static boolean isModsValid(ItemStack type, ItemStack modOne, ItemStack modTwo) {
		List<Item> ValidPotionMods = Arrays.asList(GLOWSTONE_DUST, AIR, REDSTONE, GUNPOWDER);
		List<Item> ValidFoodMods = Arrays.asList(GLOWSTONE_DUST, AIR, REDSTONE, COOKED_BEEF, GOLD_INGOT);
		//List<ItemStack> ValidScrollMods = Arrays.asList();

		return ((type.getItem() == Items.GLASS_BOTTLE && ValidPotionMods.contains(modOne.getItem()) && ValidPotionMods.contains(modTwo.getItem())) || // Check potion mods
				(type.getItem() == APPLE && ValidFoodMods.contains(modOne.getItem()) && ValidFoodMods.contains(modTwo.getItem()))); // Check food mods
	}
	/**
	 * @param stack The Itemstack to check for modifiers
	 * @return A list of strings equal to the modifier item's resourcelocation (e.g. minecraft:gunpowder)
	 */
	public static List<String> getModifiers(ItemStack stack) {
		String modOne = stack.getOrCreateTag().getString("consumable_modifiers.modOne");
		String modTwo = stack.getOrCreateTag().getString("consumable_modifiers.modTwo");
		if (modOne.equals("")) modOne = "minecraft:air";
		if (modTwo.equals("")) modTwo = "minecraft:air";

		return Arrays.asList(modOne, modTwo);
	}

	/**
	 * @param str A string equal to a modifier's resourcelocation (e.g. minecraft:gunpowder)
	 * @return A translationcomponent to be used in descriptions
	 */
	public static String translateModifier(String str) {
		List<String> input = Arrays.asList("minecraft:redstone", "minecraft:glowstone_dust", "minecraft:gunpowder", "minecraft:air", "minecraft:cooked_beef");
		List<String> output = Arrays.asList("modifiers.extendedconsumables.duration", "modifiers.extendedconsumables.strength", "modifiers.extendedconsumables.splash", "modifiers.extendedconsumables.empty", "modifiers.extendedconsumables.nourishing");
		if (input.contains(str)) {
			return output.get(input.indexOf(str));
		}
		return "extendedconsumables.modifiers.empty";
	}
}
