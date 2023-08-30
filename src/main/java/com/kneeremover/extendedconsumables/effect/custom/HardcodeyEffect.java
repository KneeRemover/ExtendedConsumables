package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HardcodeyEffect extends MobEffect {
	public HardcodeyEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return false;
	}
}
