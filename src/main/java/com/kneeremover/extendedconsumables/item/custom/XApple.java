package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.item.ExtendedFood;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.Arrays;

public class XApple extends ExtendedFood {
	public XApple(Properties pProperties) {
		super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()),
			"x_apple",
			true,
			new ArrayList<MobEffect>(
				Arrays.asList(
					MobEffects.MOVEMENT_SLOWDOWN,
					MobEffects.DIG_SLOWDOWN,
					MobEffects.WEAKNESS,
					MobEffects.LUCK
				)
			)
		);
	}
}
