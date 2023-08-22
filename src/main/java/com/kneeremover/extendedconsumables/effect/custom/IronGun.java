package com.kneeremover.extendedconsumables.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IronGun extends MobEffect {
	public IronGun(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, "c3910a13-2b6d-4bb0-9196-8f8b64244409", -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL); // Multiplier seems to be increased by one because lojik
		addAttributeModifier(Attributes.MAX_HEALTH, "f75aaafe-22f0-41e6-bd6f-d2c78929dee4", 1, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}