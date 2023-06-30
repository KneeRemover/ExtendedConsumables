package com.kneeremover.extendedconsumables.util;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
	public static class Items {
		public static final TagKey<Item> VALID_POTION_MODIFIERS = tag("valid_potion_modifiers");

		private static TagKey<Item> tag(String name) {
			return ItemTags.create(new ResourceLocation(ExtendedConsumables.MOD_ID, name));
		}
	}
}
