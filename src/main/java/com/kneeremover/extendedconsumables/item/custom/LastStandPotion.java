package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.item.GenericPotion;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;

public class LastStandPotion extends GenericPotion {
	public LastStandPotion(Properties pProperties) {
		super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()));
		super.potionName = "last_stand";
		super.onlyOneLevel = true;
		super.canSplash = false;
	}

	@Override
	public void fixEffect () {
		super.effect = ModEffects.LAST_STAND.get();
	}
}
