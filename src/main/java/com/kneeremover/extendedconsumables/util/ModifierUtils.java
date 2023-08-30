package com.kneeremover.extendedconsumables.util;

import com.kneeremover.extendedconsumables.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.item.Items.*;

public class ModifierUtils {
	private static Map<Item, ExtendedModifier> knownModifiers = new HashMap<>();
	static {
		knownModifiers.put(GLOWSTONE_DUST, new ExtendedModifier().setAmplifierEffect(1).setType(ModTypes.BOTH));
		knownModifiers.put(ModBlocks.DENSE_GLOWSTONE_BLOCK.get().asItem(), new ExtendedModifier().setAmplifierEffect(2).setType(ModTypes.BOTH));

		knownModifiers.put(REDSTONE, new ExtendedModifier().setDurationEffect(0.5).setType(ModTypes.BOTH));
		knownModifiers.put(REDSTONE_BLOCK, new ExtendedModifier().setDurationEffect(1).setType(ModTypes.BOTH));

		knownModifiers.put(BLAZE_ROD, new ExtendedModifier().setDurationEffect(0.25).setAmplifierEffect(1).setType(ModTypes.BOTH));
		knownModifiers.put(CHORUS_FRUIT, new ExtendedModifier().setDurationEffect(-0.5).setAmplifierEffect(3).setType(ModTypes.BOTH));
		knownModifiers.put(POPPED_CHORUS_FRUIT, new ExtendedModifier().setDurationEffect(4).setAmplifierEffect(-2).setType(ModTypes.BOTH));
		knownModifiers.put(DRAGON_BREATH, new ExtendedModifier().setDurationEffect(0.5).setAmplifierEffect(2).setCausesSplash(true).setType(ModTypes.BOTH));

		knownModifiers.put(COOKED_BEEF, new ExtendedModifier().setSaturationEffect(1).setType(ModTypes.FOOD));
		knownModifiers.put(GUNPOWDER, new ExtendedModifier().setCausesSplash(true).setType(ModTypes.POTION));

		knownModifiers.put(AIR, new ExtendedModifier().setType(ModTypes.BOTH));
	}
	public static class ExtendedModifier {
		ModTypes type = ModTypes.UNSET;
		String translationID = "everythings borked!";

		int amplifierEffect = 0;
		double durationEffect = 0;
		int saturationEffect = 0;
		boolean causesSplash = false;

		public String getTranslationID() {
			return this.translationID;
		}
		public int getAmplifierEffect() {
			return this.amplifierEffect;
		}
		public double getDurationEffect() {
			return this.durationEffect;
		}
		public int getSaturationEffect() {
			return this.saturationEffect;
		}
		public boolean causesSplash() {
			return this.causesSplash;
		}
		public ModTypes getType() {
			return this.type;
		}
		public ExtendedModifier setType(ModTypes type) {
			this.type = type;
			return this;
		}
		public ExtendedModifier setTranslationID(String translationID) {
			this.translationID = translationID;
			return this;
		}
		public ExtendedModifier setAmplifierEffect(int amplifierEffect) {
			this.amplifierEffect = amplifierEffect;
			return this;
		}
		public ExtendedModifier setDurationEffect(double durationEffect) {
			this.durationEffect = durationEffect;
			return this;
		}
		public ExtendedModifier setSaturationEffect(int saturationEffect) {
			this.saturationEffect = saturationEffect;
			return this;
		}
		public ExtendedModifier setCausesSplash(boolean causesSplash) {
			this.causesSplash = causesSplash;
			return this;
		}

	}
	public enum ModTypes {
		POTION,
		FOOD,
		BOTH,
		UNSET
	}

	public static boolean isModsValid(ItemStack type, ItemStack modOne, ItemStack modTwo) {
		if (type.getItem() == PAPER) {
			return modOne.getItem() == AIR && modTwo.getItem() == AIR;
		} else if (type.getItem() == APPLE) {
			ExtendedModifier knownModOne = knownModifiers.get(modOne.getItem());
			ExtendedModifier knownModTwo = knownModifiers.get(modTwo.getItem());
			return knownModOne != null && knownModTwo != null
					&& (knownModOne.getType() == ModTypes.FOOD || knownModOne.getType() == ModTypes.BOTH)
					&& (knownModTwo.getType() == ModTypes.FOOD || knownModTwo.getType() == ModTypes.BOTH);
		} else if (type.getItem() == GLASS_BOTTLE) {
			ExtendedModifier knownModOne = knownModifiers.get(modOne.getItem());
			ExtendedModifier knownModTwo = knownModifiers.get(modTwo.getItem());
			return knownModOne != null && knownModTwo != null
					&& (knownModOne.getType() == ModTypes.POTION || knownModOne.getType() == ModTypes.BOTH)
					&& (knownModTwo.getType() == ModTypes.POTION || knownModTwo.getType() == ModTypes.BOTH);
		}

		return false;
	}

	public static ExtendedModifier getModifier(ItemStack stack, int id) {
		return idToModifier(stack.getOrCreateTag().getString("consumable_modifiers.mod" + (id == 1 ? "One" : "Two")));
	}

	public static ExtendedModifier getCombinedModifiers(ItemStack stack) {
		ExtendedModifier modOne = idToModifier(stack.getOrCreateTag().getString("consumable_modifiers.modOne"));
		ExtendedModifier modTwo = idToModifier(stack.getOrCreateTag().getString("consumable_modifiers.modTwo"));
		ExtendedModifier toReturn = new ExtendedModifier();
		toReturn.setAmplifierEffect(modOne.getAmplifierEffect() + modTwo.getAmplifierEffect())
			.setDurationEffect(modOne.getDurationEffect() + modTwo.getDurationEffect())
			.setSaturationEffect(modOne.getSaturationEffect() + modTwo.getSaturationEffect())
			.setCausesSplash(modOne.causesSplash() || modTwo.causesSplash());
		return toReturn;
	}

	private static ExtendedModifier idToModifier(String id) {
		if (id.equals("")) id = "minecraft:air";
		String[] modSplit = id.split(":");
		return knownModifiers.get(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modSplit[0], modSplit[1])));
	}
}
