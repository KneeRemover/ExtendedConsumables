package com.kneeremover.extendedconsumables.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackSpeedBoost extends MobEffect {
	public AttackSpeedBoost(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(Attributes.ATTACK_SPEED, "6186f0b0-109f-48cd-825d-aa9c378761cd", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
