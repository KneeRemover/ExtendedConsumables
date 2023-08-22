package com.kneeremover.extendedconsumables.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExtendedScroll extends Item {
	public ExtendedScroll(boolean isConsumable, String name) {
		super(new Item.Properties().tab(ModCreativeModeTabs.SCROLL_TAB));
		this.isConsumable = isConsumable;
		this.name = name;
	}

	boolean isConsumable;
	String name;

	@Override
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			if (isConsumable) {
				pTooltipComponents.add(new TranslatableComponent("description.warn.extendedconsumables.consumable"));
			} else {
				pTooltipComponents.add(new TranslatableComponent("description.generic.extendedconsumables.notconsumable"));
			}
			pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables." + name));
		} else {
			pTooltipComponents.add(new TranslatableComponent("description.generic.extendedconsumables.shift"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}
