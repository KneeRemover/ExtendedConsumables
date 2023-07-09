package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTabs {
	public static final CreativeModeTab POTION_TAB = new CreativeModeTab("extendedconsumables.potiontab") {
		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ModItems.STEP_HEIGHT_POTION.get());
		}
	};
	public static final CreativeModeTab FOOD_TAB = new CreativeModeTab("extendedconsumables.foodtab") {
		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ModItems.ENVIRONMENTAL_APPLE.get());
		}
	};
	public static final CreativeModeTab SCROLL_TAB = new CreativeModeTab("extendedconsumables.scrolltab") {
		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ModItems.FRIENDHSIP_SCROLL.get());
		}
	};
	public static final CreativeModeTab OTHER_TAB = new CreativeModeTab("extendedconsumables.othertab") {
		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ModBlocks.CONSUMABLE_TABLE.get());
		}
	};
}
