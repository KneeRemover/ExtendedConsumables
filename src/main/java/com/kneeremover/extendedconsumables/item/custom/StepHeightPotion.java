package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.item.GenericPotion;

public class StepHeightPotion extends GenericPotion {
	public StepHeightPotion(Properties pProperties) {
		super(pProperties);
		setPotionDescription("It's just better auto jump. Each level allows you to step up 1 more block than usual, allowing you to quickly cross ");
		setEffect(ModEffects.STEP_HEIGHT.get());
	}
}
