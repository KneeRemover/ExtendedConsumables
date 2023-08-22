package com.kneeremover.extendedconsumables.block.entity.custom;

import com.kneeremover.extendedconsumables.block.entity.ModBlockEntities;
import com.kneeremover.extendedconsumables.screen.StackCombinerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class StackCombinerBlockEntity extends BlockEntity implements MenuProvider {
	private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};

	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

	public StackCombinerBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntities.STACK_COMBINER_BLOCK_ENTITY.get(), pPos, pBlockState);
	}

	@Override
	public @NotNull Component getDisplayName() {
		return new TextComponent("Stack Combiner Table");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
		return new StackCombinerMenu(pContainerId, pPlayerInventory, this);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return lazyItemHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyItemHandler.invalidate();
	}

	public void drops() {
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		}

		Containers.dropContents(this.level, this.worldPosition, inventory);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, StackCombinerBlockEntity pBlockEntity) {
		if (slotsAreIdentical(pBlockEntity)) {
			setChanged(pLevel, pPos, pState);
			combineStacksItem(pBlockEntity);
		}
	}

	private static boolean slotsAreIdentical(StackCombinerBlockEntity entity) {
		SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
		for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
			inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
		}
		ItemStack itemOne = inventory.getItem(0);
		ItemStack itemTwo = inventory.getItem(1);
		ItemStack item = new ItemStack(itemOne.getItem(), itemOne.getCount() + itemTwo.getCount());
		boolean areIdentical = itemOne.getItem() == itemTwo.getItem() && itemOne.getOrCreateTag().getAsString().equals(itemTwo.getOrCreateTag().getAsString());

		return areIdentical && canInsertAmountIntoOutputSlot(inventory, item)
				&& canInsertItemIntoOutputSlot(inventory, item) ;
	}

	public static void combineStacksItem(StackCombinerBlockEntity entity) {
		Level level = entity.level;
		SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
		for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
			inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
		}
		entity.itemHandler.setStackInSlot(0, Items.AIR.getDefaultInstance());
		entity.itemHandler.setStackInSlot(1, Items.AIR.getDefaultInstance());
		ItemStack itemOne = inventory.getItem(0);
		ItemStack itemTwo = inventory.getItem(1);
		ItemStack toReturn = new ItemStack(itemOne.getItem(), itemOne.getCount() + itemTwo.getCount() + inventory.getItem(2).getCount());
		toReturn.setTag(itemOne.getOrCreateTag());
		entity.itemHandler.setStackInSlot(2, toReturn);
	}

	private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
		return inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
	}

	private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
		return inventory.getItem(2).getMaxStackSize() >= inventory.getItem(2).getCount() + output.getCount() && inventory.getItem(2).isStackable() && output.isStackable();
	}
}
