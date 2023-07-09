package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.item.AbstractPotion;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;

public class SaturationOverloadPotion extends AbstractPotion {
	public SaturationOverloadPotion(Properties pProperties) {
	super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()), "saturation_overload", ModEffects.SATURATION_OVERLOAD,
			1200,true, false);
	}
}
