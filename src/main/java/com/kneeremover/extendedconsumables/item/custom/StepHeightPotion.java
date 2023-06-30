package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.item.GenericPotion;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;

public class StepHeightPotion extends GenericPotion {
	public StepHeightPotion(Properties pProperties) {
		super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()));
		super.potionName = "step_height";
		super.canSplash = true;
	}

	@Override
	public void fixEffect () {
		super.effect = ModEffects.STEP_HEIGHT.get();
	}
}
