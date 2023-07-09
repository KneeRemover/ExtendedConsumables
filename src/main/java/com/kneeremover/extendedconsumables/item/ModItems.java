package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.custom.*;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<Item> EC_CROSSBOW = ITEMS.register("ec_crossbow", () -> new ECCrossbow(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> TIPPED_BOLT_ITEM = ITEMS.register("tipped_bolt_item", () -> new TippedBoltItem(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> MODIFIER_PLACEHOLDER = ITEMS.register("modifier_placeholder", () -> new ModifierPlaceholder(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));

	public static final RegistryObject<Item> STEP_HEIGHT_POTION = ITEMS.register("step_height_potion", () -> new StepHeightPotion(new Item.Properties()));
	public static final RegistryObject<Item> LAST_STAND_POTION = ITEMS.register("last_stand_potion", () -> new LastStandPotion(new Item.Properties()));
	public static final RegistryObject<Item> SATURATION_OVERLOAD_POTION = ITEMS.register("saturation_overload_potion", () -> new SaturationOverloadPotion(new Item.Properties()));
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RADIANT_FLAMES_POTION = ITEMS.register("radiant_flames_potion", () -> new RadiantFlamesPotion(new Item.Properties()));
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RADIANT_REGEN_POTION = ITEMS.register("radiant_regen_potion", () -> new RadiantRegenPotion(new Item.Properties()));
	public static final RegistryObject<Item> RADIANT_SLOWNESS_POTION = ITEMS.register("radiant_slowness_potion", () -> new RadiantSlownessPotion(new Item.Properties()));

	public static final RegistryObject<Item> ENVIRONMENTAL_APPLE = ITEMS.register("environmental_apple", () -> new EnvironmentalApple(new Item.Properties()));

	public static final RegistryObject<Item> FRIENDHSIP_SCROLL = ITEMS.register("scroll_friendship", () -> new ScrollOfFriendship(new Item.Properties().tab(ModCreativeModeTabs.SCROLL_TAB)));

	public static final RegistryObject<Item> POCKET_DIMENSION_ANALYZER = ITEMS.register("pocket_dimension_analyzer", () -> new PocketDimensionAnalyzer(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> POTIONOLIGER_SPAWN_EGG = ITEMS.register("potionoligerspawnegg",
			() -> new ForgeSpawnEggItem(ModEntities.POTIONOLIGER, 102 - 153, -153, new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB))); // Currently unused

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
