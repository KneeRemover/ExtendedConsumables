package com.kneeremover.extendedconsumables.block;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.block.custom.ConsumableTable;
import com.kneeremover.extendedconsumables.block.custom.StackCombiner;
import com.kneeremover.extendedconsumables.item.ModCreativeModeTabs;
import com.kneeremover.extendedconsumables.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtendedConsumables.MOD_ID);


	public static final RegistryObject<Block> CONSUMABLE_TABLE = registerBlock("consumable_table",
			() -> new ConsumableTable(BlockBehaviour.Properties.of(Material.WOOD).strength(9f).noOcclusion().destroyTime(4)), ModCreativeModeTabs.OTHER_TAB);

	public static final RegistryObject<Block> STACK_COMBINER = registerBlock("stack_combiner",
			() -> new StackCombiner(BlockBehaviour.Properties.of(Material.WOOD).strength(9f).noOcclusion().destroyTime(4)), ModCreativeModeTabs.OTHER_TAB);

	public static final RegistryObject<Block> DENSE_GLOWSTONE_BLOCK = registerBlock("dense_glowstone_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.SAND).strength(0.6F).sound(SoundType.GLASS).lightLevel((p_50872_) -> 15)), ModCreativeModeTabs.OTHER_TAB);

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, @SuppressWarnings("SameParameterValue") CreativeModeTab tab) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn, tab);
		return toReturn;
	}

	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
