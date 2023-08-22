package com.kneeremover.extendedconsumables.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExtendedBaseSlot extends SlotItemHandler {
	public ExtendedBaseSlot(IItemHandler itemHandler, int index, int x, int y) {
		super(itemHandler, index, x, y);
	}

	@Override
	public boolean mayPlace(@NotNull ItemStack stack) {
		return stack.getItem() == Items.PAPER || stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == Items.APPLE;
	}
}
