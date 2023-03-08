package com.kneeremover.extendedconsumables.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class StepHeight extends MobEffect {
	protected StepHeight(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION.get(), "5791ee62-3d68-4802-8e01-de1e803cd5f9", 1, AttributeModifier.Operation.ADDITION);
	}
}
