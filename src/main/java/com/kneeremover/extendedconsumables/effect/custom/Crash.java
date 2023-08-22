package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Crash extends MobEffect {
	public Crash(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.removeEffect(ModEffects.DC.get());
		if (pLivingEntity instanceof Player && pLivingEntity.getLevel().isClientSide) {
			int crash = 1 / 0; // haha get crashed lol
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}
