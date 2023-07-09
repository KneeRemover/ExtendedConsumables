package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.item.AbstractFood;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentalApple extends AbstractFood {
	public EnvironmentalApple(Properties pProperties) {
		super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()));
		super.foodName = "environmental_apple";
		super.onlyOneLevel = true;
	}

	@Override
	public void fixEffect () {
		List<MobEffect> toReturn = new ArrayList<>();
		toReturn.add(MobEffects.FIRE_RESISTANCE);
		toReturn.add(MobEffects.WATER_BREATHING);
		toReturn.add(MobEffects.SLOW_FALLING);
		super.effect = toReturn;
	}
}
