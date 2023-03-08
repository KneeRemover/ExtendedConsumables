package com.kneeremover.extendedconsumables.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PotionHelper {
	public static List<String> getModifiers(ItemStack stack) {
		String modOne = stack.getOrCreateTag().getString("consumable_modifiers.modOne");
		String modTwo = stack.getOrCreateTag().getString("consumable_modifiers.modTwo");
		return Arrays.asList(modOne, modTwo);
	}

	public static String translateModifier(String str) {
		List<String> input = Arrays.asList("minecraft:redstone", "minecraft:glowstone_dust", "minecraft:gunpowder", "minecraft:air");
		List<String> output = Arrays.asList("extendedconsumables.modifiers.duration", "extendedconsumables.modifiers.strength", "extendedconsumables.modifiers.splash", "extendedconsumables.modifiers.empty");
		return output.get(input.indexOf(str));
	}
}
