package com.kneeremover.extendedconsumables.block.entity;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.block.entity.custom.ConsumableTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<BlockEntityType<ConsumableTableBlockEntity>> CONSUMABLE_TABLE_BLOCK_ENTITY =
			BLOCK_ENTITIES.register("consumable_table_block_entity", () -> BlockEntityType.Builder.of(ConsumableTableBlockEntity::new, ModBlocks.CONSUMABLE_TABLE.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}
}

