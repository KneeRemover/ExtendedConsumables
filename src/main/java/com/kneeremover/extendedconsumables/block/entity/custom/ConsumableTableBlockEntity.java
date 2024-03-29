package com.kneeremover.extendedconsumables.block.entity.custom;

import com.kneeremover.extendedconsumables.block.entity.ModBlockEntities;
import com.kneeremover.extendedconsumables.item.ExtendedRestrictedPotion;
import com.kneeremover.extendedconsumables.recipe.ConsumableTableRecipe;
import com.kneeremover.extendedconsumables.screen.ConsumableTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
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
import java.util.Optional;
import java.util.UUID;

public class ConsumableTableBlockEntity extends BlockEntity implements MenuProvider {
	private final ItemStackHandler itemHandler = new ItemStackHandler(8) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};

	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

	protected final ContainerData data;
	private int progress = 0;
	private int maxProgress = 72;
	private int fuel = 0;
	private int maxFuel = 0;

	public ConsumableTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntities.CONSUMABLE_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);

		this.data = new ContainerData() {
			public int get(int index) {
				switch (index) {
					case 0:
						return ConsumableTableBlockEntity.this.progress;
					case 1:
						return ConsumableTableBlockEntity.this.maxProgress;
					case 2:
						return ConsumableTableBlockEntity.this.fuel;
					case 3:
						return ConsumableTableBlockEntity.this.maxFuel;
					default:
						return 0;
				}
			}

			public void set(int index, int value) {
				switch (index) {
					case 0:
						ConsumableTableBlockEntity.this.progress = value;
						break;
					case 1:
						ConsumableTableBlockEntity.this.maxProgress = value;
						break;
					case 2:
						ConsumableTableBlockEntity.this.fuel = value;
						break;
					case 3:
						ConsumableTableBlockEntity.this.maxFuel = value;
						break;
				}
			}

			public int getCount() {
				return 4;
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return new TextComponent("Consumable Table");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
		return new ConsumableTableMenu(pContainerId, pPlayerInventory, this, this.data);
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

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		tag.put("inventory", itemHandler.serializeNBT());
		tag.putInt("consumable_table.progress", progress);
		tag.putInt("consumable_table.fuel", fuel);
		tag.putInt("consumable_table.maxFuel", maxFuel);
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
		progress = nbt.getInt("consumable_table.progress");
		fuel = nbt.getInt("consumable_table.fuel");
		maxFuel = nbt.getInt("consumable_table.maxFuel");
	}

	public void drops() {
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		}

		Containers.dropContents(this.level, this.worldPosition, inventory);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ConsumableTableBlockEntity pBlockEntity) {
		if (pBlockEntity.fuel <= 1) {
			addFuel(pBlockEntity);
		}
		if (pBlockEntity.fuel > 0) { // No integer overflow jank
			pBlockEntity.fuel--;
		}
		if (hasRecipe(pBlockEntity)) {
			pBlockEntity.progress++;
			setChanged(pLevel, pPos, pState);
			if (pBlockEntity.progress > pBlockEntity.maxProgress) {
				craftItem(pBlockEntity);
			}
		} else {
			pBlockEntity.resetProgress();
			setChanged(pLevel, pPos, pState);
		}
	}

	private static boolean hasRecipe(ConsumableTableBlockEntity entity) {
		Level level = entity.level;
		SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
		for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
			inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
		}

		Optional<ConsumableTableRecipe> match = level.getRecipeManager()
				.getRecipeFor(ConsumableTableRecipe.Type.INSTANCE, inventory, level);

		return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
				&& canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
				&& hasFuel(entity)
				&& isEnabled(match.get().getResultItem());
	}

	private static boolean isEnabled(ItemStack item) {
		if (item.getItem() instanceof ExtendedRestrictedPotion arpItem) {
			return arpItem.config.get();
		}

		return true;
	}

	private static boolean hasFuel(ConsumableTableBlockEntity entity) {
		return entity.fuel >= 1;
	}

	public static void addFuel(ConsumableTableBlockEntity entity) {
		ItemStack fuelSlot = entity.itemHandler.getStackInSlot(6);
		SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
		for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
			inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
		}

		Optional<ConsumableTableRecipe> match = entity.level.getRecipeManager()
				.getRecipeFor(ConsumableTableRecipe.Type.INSTANCE, inventory, entity.level);
		if (fuelSlot.getItem() != Items.AIR && match.isPresent()) {
			entity.itemHandler.extractItem(6, 1, false);
			entity.fuel =(int) (net.minecraftforge.common.ForgeHooks.getBurnTime(fuelSlot, RecipeType.SMELTING) * .36 + 10);
			entity.maxFuel = entity.fuel;
		}
	}

	public static void craftItem(ConsumableTableBlockEntity entity) {
		Level level = entity.level;
		SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
		for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
			inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
		}

		Optional<ConsumableTableRecipe> match = level.getRecipeManager()
				.getRecipeFor(ConsumableTableRecipe.Type.INSTANCE, inventory, level);

		if (match.isPresent()) {
			// Create the output item
			Item modOne;
			Item modTwo;

			modOne = entity.itemHandler.getStackInSlot(4).getItem();
			modTwo = entity.itemHandler.getStackInSlot(5).getItem();

			ItemStack toReturn = new ItemStack(match.get().getResultItem().getItem(), entity.itemHandler.getStackInSlot(7).getCount() + 1);

			String modOneRegistryName = modOne.asItem().getRegistryName().toString();
			String modTwoRegistryName = modTwo.asItem().getRegistryName().toString();

			// Make sure we aren't overriding any modifiers
			if ((modOneRegistryName.equals(entity.itemHandler.getStackInSlot(7).getOrCreateTag().getString("consumable_modifiers.modOne")) || entity.itemHandler.getStackInSlot(7).getItem() == Items.AIR) && (modTwoRegistryName.equals(entity.itemHandler.getStackInSlot(7).getOrCreateTag().getString("consumable_modifiers.modTwo")) || entity.itemHandler.getStackInSlot(7).getItem() == Items.AIR)) {
				toReturn.getOrCreateTag().putString("consumable_modifiers.modOne", modOneRegistryName);
				toReturn.getOrCreateTag().putString("consumable_modifiers.modTwo", modTwoRegistryName);
				entity.itemHandler.extractItem(4, 1, false);
				entity.itemHandler.extractItem(5, 1, false);
			} else {
				entity.resetProgress();
				BlockPos p = entity.getBlockPos();
				Player player = entity.level.getNearestPlayer(p.getX(), p.getY(), p.getZ(), 10, false);
				if (player != null && !level.isClientSide) {
					player.sendMessage(new TranslatableComponent("extendedconsumables.failed_consumable_craft"), UUID.randomUUID());
				}
				return;
			}

			// Actually expend the items, then place the output and reset the progress
			for (int i = 0; i < 6; i++) { // Check if there is a container here. If there is, "empty" the container. Otherwise, destroy one item from the stack. Do this for every slot from 0 to 5.
				ItemStack containerItem = entity.itemHandler.getStackInSlot(i).getContainerItem();
				if (containerItem.getItem() != Items.AIR) {
					entity.itemHandler.setStackInSlot(i, containerItem);
				} else {
					entity.itemHandler.extractItem(i, 1, false);
				}
			}
			entity.itemHandler.setStackInSlot(7, toReturn);

			entity.resetProgress();
		}
	}

	private void resetProgress() {
		this.progress = 0;
	}

	private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
		return inventory.getItem(7).getItem() == output.getItem() || inventory.getItem(7).isEmpty();
	}

	private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
		return inventory.getItem(7).getMaxStackSize() > inventory.getItem(7).getCount();
	}
}
