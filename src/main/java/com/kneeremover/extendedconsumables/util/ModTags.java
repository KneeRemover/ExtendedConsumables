package com.kneeremover.extendedconsumables.util;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class ModTags {
	@SuppressWarnings("unused")
	public static class Items {
		@SuppressWarnings("unused")
		public static final TagKey<Item> VALID_POTION_MODIFIERS = tag("valid_potion_modifiers");

		private static TagKey<Item> tag(@SuppressWarnings("SameParameterValue") String name) {
			return ItemTags.create(new ResourceLocation(ExtendedConsumables.MOD_ID, name));
		}
	}
}
