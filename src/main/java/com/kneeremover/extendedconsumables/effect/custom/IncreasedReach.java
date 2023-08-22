package com.kneeremover.extendedconsumables.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class IncreasedReach extends MobEffect {
	public IncreasedReach(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
		addAttributeModifier(ForgeMod.ATTACK_RANGE.get(), "12326468-61d3-4db2-b1f7-8b02b2992d69", 1, AttributeModifier.Operation.ADDITION);
		addAttributeModifier(ForgeMod.REACH_DISTANCE.get(), "dfbe2d72-10f4-4724-a389-a6c7ad8b0735", 1, AttributeModifier.Operation.ADDITION);
	}
}
