package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.item.AbstractPotion;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;

public class RadiantSlownessPotion extends AbstractPotion {
	@SuppressWarnings("unused")
	public RadiantSlownessPotion(Properties pProperties) {
		super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()), "radiant_slowness", ModEffects.RADIANT_SLOWNESS,
				1200,false, false);
	}
}
