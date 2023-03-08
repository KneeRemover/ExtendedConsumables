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

		//List<ItemStack> ValidFoodMods = Arrays.asList();
		//List<ItemStack> ValidScrollMods = Arrays.asList();

		if (type.getItem() == Items.GLASS_BOTTLE && ValidPotionMods.contains(modOne.getItem()) && ValidPotionMods.contains(modTwo.getItem())) {
			return true;
		}
		return false;
	}
}
