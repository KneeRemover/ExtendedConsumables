package com.kneeremover.extendedconsumables.screen.slot;

import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.util.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExtendedModifierSlot extends SlotItemHandler {
	public ExtendedModifierSlot(IItemHandler itemHandler, int index, int x, int y) {
		super(itemHandler, index, x, y);
	}

	@Override
	public boolean mayPlace(@NotNull ItemStack stack) {
		return Registry.ITEM.getHolderOrThrow(Registry.ITEM.getResourceKey(stack.getItem()).get()).is(ModTags.Items.VALID_POTION_MODIFIERS);
	}
}
