package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.custom.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<Item> EC_CROSSBOW = ITEMS.register("ec_crossbow", () -> new ECCrossbow(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> TIPPED_BOLT_ITEM = ITEMS.register("tipped_bolt_item", () -> new TippedBoltItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> STINGOT = ITEMS.register("stingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> MODIFIER_PLACEHOLDER = ITEMS.register("modifier_placeholder", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> STEP_HEIGHT_POTION = ITEMS.register("step_height_potion", () -> new StepHeightPotion(new Item.Properties()));
	public static final RegistryObject<Item> LAST_STAND_POTION = ITEMS.register("last_stand_potion", () -> new LastStandPotion(new Item.Properties()));
	public static final RegistryObject<Item> SATURATION_OVERLOAD_POTION = ITEMS.register("saturation_overload_potion", () -> new SaturationOverloadPotion(new Item.Properties()));

	public static final RegistryObject<Item> ENVIRONMENTAL_APPLE = ITEMS.register("environmental_apple", () -> new EnvironmentalApple(new Item.Properties()));

	public static final RegistryObject<Item> POTIONOLIGER_SPAWN_EGG = ITEMS.register("potionoligerspawnegg",
			() -> new ForgeSpawnEggItem(ModEntities.POTIONOLIGER,102- 0 -153,153-153-153, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
