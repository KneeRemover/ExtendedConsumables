package com.kneeremover.extendedconsumables.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExtendedFuelSlot extends SlotItemHandler {
	public ExtendedFuelSlot(IItemHandler itemHandler, int index, int x, int y) {
		super(itemHandler, index, x, y);
	}

	@Override
	public boolean mayPlace(@NotNull ItemStack stack) {
		return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) >= 1;
	}
}
