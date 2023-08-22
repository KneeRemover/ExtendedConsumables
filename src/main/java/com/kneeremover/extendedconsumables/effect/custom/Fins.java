package com.kneeremover.extendedconsumables.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class Fins extends MobEffect {
	public Fins(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "809fa9e3-a54a-4155-b559-3a2d99fd51b4", 0.37, AttributeModifier.Operation.MULTIPLY_BASE);
	}
}
