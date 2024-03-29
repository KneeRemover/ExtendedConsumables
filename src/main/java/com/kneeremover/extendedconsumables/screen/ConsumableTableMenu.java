package com.kneeremover.extendedconsumables.screen;

import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.block.entity.custom.ConsumableTableBlockEntity;
import com.kneeremover.extendedconsumables.screen.slot.ExtendedBaseSlot;
import com.kneeremover.extendedconsumables.screen.slot.ExtendedFuelSlot;
import com.kneeremover.extendedconsumables.screen.slot.ExtendedModifierSlot;
import com.kneeremover.extendedconsumables.screen.slot.ModResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ConsumableTableMenu extends AbstractContainerMenu {
	private final ConsumableTableBlockEntity blockEntity;
	private final Level level;
	private final ContainerData data;

	public ConsumableTableMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
		this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
	}

	public ConsumableTableMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
		super(ModMenuTypes.CONSUMABLE_TABLE_MENU.get(), pContainerId);
		checkContainerSize(inv, 8);
		blockEntity = ((ConsumableTableBlockEntity) entity);
		this.level = inv.player.level;
		this.data = data;

		addPlayerInventory(inv);
		addPlayerHotbar(inv);

		this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
			this.addSlot(new ExtendedBaseSlot(handler, 0, 78, 43));
			this.addSlot(new SlotItemHandler(handler, 1, 60, 18));
			this.addSlot(new SlotItemHandler(handler, 2, 78, 18));
			this.addSlot(new SlotItemHandler(handler, 3, 96, 18));
			this.addSlot(new ExtendedModifierSlot(handler, 4, 29, 34));
			this.addSlot(new ExtendedModifierSlot(handler, 5, 29, 52));
			this.addSlot(new ExtendedFuelSlot(handler, 6, 131, 42));
			this.addSlot(new ModResultSlot(handler, 7, 104, 64));
		});

		addDataSlots(data);
	}

	public boolean isCrafting() {
		return data.get(0) > 0;
	}

	public int getScaledFuel() {
		int fuel = this.data.get(2);
		int maxFuel = this.data.get(3);
		int fuelMeterSize = 14;

		return maxFuel != 0 && fuel != 0 ? fuel * fuelMeterSize / maxFuel : 0;
	}
	public int getScaledProgress() {
		int progress = this.data.get(0);
		int maxProgress = this.data.get(1);
		int progressArrowSize = 18;
		return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
	}


	// CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
	// must assign a slot number to each of the slots used by the GUI.
	// For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container, it automatically increases the slotIndex, which means
	//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

	// THIS YOU HAVE TO DEFINE!
	private static final int TE_INVENTORY_SLOT_COUNT = 8;  // must be the number of slots you have!

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
		Slot sourceSlot = slots.get(index);
		if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		// Check if the slot clicked is one of the vanilla container slots
		if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// This is a vanilla container slot so merge the stack into the tile inventory
			if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
					+ TE_INVENTORY_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;  // EMPTY_ITEM
			}
		} else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
			// This is a TE slot so merge the stack into the players inventory
			if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.out.println("Invalid slotIndex:" + index);
			return ItemStack.EMPTY;
		}
		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(playerIn, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public boolean stillValid(@NotNull Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
				pPlayer, ModBlocks.CONSUMABLE_TABLE.get());
	}

	private void addPlayerInventory(Inventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
			}
		}
	}

	private void addPlayerHotbar(Inventory playerInventory) {
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
		}
	}
}
