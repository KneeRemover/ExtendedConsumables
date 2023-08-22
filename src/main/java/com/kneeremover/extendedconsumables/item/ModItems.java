package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.config.ExtendedConsumablesCommonConfigs;
import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"unused", "Convert2MethodRef"})
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<Item> EC_CROSSBOW = ITEMS.register("ec_crossbow", () -> new ECCrossbow(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> DULL_ARROW = ITEMS.register("dull_arrow", () -> new DullArrow(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> DULL_TIPPED_BOLT_ITEM = ITEMS.register("dull_tipped_bolt_item", () -> new DullTippedBoltItem(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> TIPPED_BOLT_ITEM = ITEMS.register("tipped_bolt_item", () -> new TippedBoltItem(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> MODIFIER_PLACEHOLDER = ITEMS.register("modifier_placeholder", () -> new ModifierPlaceholder(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));

	public static final RegistryObject<Item> ADVANCED_STICKS = ITEMS.register("advanced_sticks", () -> new AdvancedSticks(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> WOODEN_STAXE = ITEMS.register("wooden_staxe", () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> IRON_PICKSTAXE = ITEMS.register("iron_pickstaxe", () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));
	public static final RegistryObject<Item> THIRD_OF_AN_IRON_INGOT = ITEMS.register("third_of_an_iron_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));

	public static final RegistryObject<Item> DC_POTION = ITEMS.register("dc_potion", () -> new ExtendedRestrictedPotion(new ExtendedPotion.EPProperties().setPotName("dc")
			.setEffect(ModEffects.DC).onlyOneLevel(), ExtendedConsumablesCommonConfigs.INCLUDE_DC_POTION));
	public static final RegistryObject<Item> CRASH_POTION = ITEMS.register("crash_potion", () -> new ExtendedRestrictedPotion(new ExtendedPotion.EPProperties().setPotName("crash")
			.setEffect(ModEffects.CRASH).noSplash().onlyOneLevel(), ExtendedConsumablesCommonConfigs.INCLUDE_CRASH_POTION));

	public static final RegistryObject<Item> STEP_HEIGHT_POTION = ITEMS.register("step_height_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("step_height")
			.setEffect(ModEffects.STEP_HEIGHT)));
	public static final RegistryObject<Item> REACH_POTION = ITEMS.register("reach_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("increased_reach")
			.setEffect(ModEffects.INCREASED_REACH)));
	public static final RegistryObject<Item> FINS_POTION = ITEMS.register("fins_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("fins").setEffect(ModEffects.FINS).onlyOneLevel()));
	public static final RegistryObject<Item> IRON_GUN_POTION = ITEMS.register("iron_gun_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("iron_gun").setEffect(ModEffects.IRON_GUN)));
	public static final RegistryObject<Item> SHATTERED_DEFENSE_POTION = ITEMS.register("shattered_defense_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("shattered_defense")
			.setEffect(ModEffects.SHATTERED_DEFENSE)));
	public static final RegistryObject<Item> EFFECT_REMOVER_POTION = ITEMS.register("effect_remover_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("effect_remover")
			.setEffect(ModEffects.EFFECT_REMOVER).unstackable().onlyOneLevel().noBolt()));

	public static final RegistryObject<Item> LAST_STAND_POTION = ITEMS.register("last_stand_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("last_stand")
			.setEffect(ModEffects.LAST_STAND).noSplash().onlyOneLevel()));
	public static final RegistryObject<Item> SATURATION_OVERLOAD_POTION = ITEMS.register("saturation_overload_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("saturation_overload")
			.setEffect(ModEffects.SATURATION_OVERLOAD).noSplash().onlyOneLevel()));

	public static final RegistryObject<Item> RADIANT_FLAMES_POTION = ITEMS.register("radiant_flames_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("radiant_flames")
			.setEffect(ModEffects.RADIANT_FLAMES).noSplash()));
	public static final RegistryObject<Item> RADIANT_REGEN_POTION = ITEMS.register("radiant_regen_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("radiant_regen")
			.setEffect(ModEffects.SATURATION_OVERLOAD).noSplash()));
	public static final RegistryObject<Item> RADIANT_SLOWNESS_POTION = ITEMS.register("radiant_slowness_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties().setPotName("radiant_slowness")
			.setEffect(ModEffects.RADIANT_SLOWNESS).noSplash()));
	public static final RegistryObject<Item> RADIANT_FRIENDLY_DAMAGE_LINK_POTION = ITEMS.register("radiant_friendly_damage_link_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties()
			.setPotName("radiant_friendly_damage_link").setEffect(ModEffects.RADIANT_FRIENDLY_DAMAGE_LINK).noSplash()));
	public static final RegistryObject<Item> IGNITION_POTION = ITEMS.register("ignition_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties()
			.setPotName("ignition").setEffect(ModEffects.IGNITION).onlyOneLevel()));
	public static final RegistryObject<Item> AGING_POTION = ITEMS.register("aging_potion", () -> new ExtendedPotion(new ExtendedPotion.EPProperties()
			.setPotName("aging").setEffect(ModEffects.AGING)));

	public static final RegistryObject<Item> ENVIRONMENTAL_APPLE = ITEMS.register("environmental_apple", () -> new EnvironmentalApple(new Item.Properties()));
	public static final RegistryObject<Item> HEALTHY_APPLE = ITEMS.register("healthy_apple", () -> new HealthyApple(new Item.Properties()));
	public static final RegistryObject<Item> X_APPLE = ITEMS.register("x_apple", () -> new XApple(new Item.Properties()));
	public static final RegistryObject<Item> BATTLE_APPLE = ITEMS.register("battle_apple", () -> new BattleApple(new Item.Properties()));

	public static final RegistryObject<Item> FRIENDHSIP_SCROLL = ITEMS.register("scroll_friendship", () -> new ScrollOfFriendship());
	public static final RegistryObject<Item> SADEKIVI_SCROLL = ITEMS.register("scroll_sadekivi", () -> new ScrollOfSadekivi());
	public static final RegistryObject<Item> DAY_SCROLL = ITEMS.register("scroll_day", () -> new ScrollOfDay());
	public static final RegistryObject<Item> NIGHT_SCROLL = ITEMS.register("scroll_night", () -> new ScrollOfNight());
	public static final RegistryObject<Item> SUN_SCROLL = ITEMS.register("scroll_sun", () -> new ScrollOfSun());
	public static final RegistryObject<Item> RAIN_SCROLL = ITEMS.register("scroll_rain", () -> new ScrollOfRain());
	public static final RegistryObject<Item> THUNDER_SCROLL = ITEMS.register("scroll_thunder", () -> new ScrollOfThunder());
	public static final RegistryObject<Item> DOUBLE_JUMP_SCROLL = ITEMS.register("scroll_double_jump", () -> new ScrollOfDoubleJump());

	public static final RegistryObject<Item> POCKET_DIMENSION_ANALYZER = ITEMS.register("pocket_dimension_analyzer", () -> new PocketDimensionAnalyzer(new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB)));

	//public static final RegistryObject<Item> POTIONOLIGER_SPAWN_EGG = ITEMS.register("potionoligerspawnegg",
	//		() -> new ForgeSpawnEggItem(ModEntities.POTIONOLIGER, 102 - 153, -153, new Item.Properties().tab(ModCreativeModeTabs.OTHER_TAB))); // Currently unused

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
