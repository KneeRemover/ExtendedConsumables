package com.kneeremover.extendedconsumables.item;

import net.minecraftforge.common.ForgeConfigSpec;

public class ExtendedRestrictedPotion extends ExtendedPotion {
	public ExtendedRestrictedPotion(ExtendedPotion.EPProperties properties, ForgeConfigSpec.ConfigValue<Boolean> config) {
		super(properties);
		this.config = config;
	}
	public ForgeConfigSpec.ConfigValue<Boolean> config;
}