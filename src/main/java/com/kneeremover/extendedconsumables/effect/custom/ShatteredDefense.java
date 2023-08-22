package com.kneeremover.extendedconsumables.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShatteredDefense extends MobEffect {
	public ShatteredDefense(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(Attributes.ARMOR, "5a8404b5-d175-482e-a31b-404ae1220e14", -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}