package com.kneeremover.extendedconsumables.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ExtendedConsumablesCommonConfigs {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Boolean> INCLUDE_DC_POTION;
	public static final ForgeConfigSpec.ConfigValue<Boolean> INCLUDE_CRASH_POTION;

	static {
		BUILDER.push("Configs for Extended Consumables");

		INCLUDE_DC_POTION = BUILDER.comment("Include the game breaking DC potion, allowing you to create arrows that disconnect hit players. Default: False").define("Include DC Potion", false);
		INCLUDE_CRASH_POTION = BUILDER.comment("Include the game breaking Crash potion, allowing you to create arrows that crash hit players. Default: False").define("Include Crash Potion", false);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
