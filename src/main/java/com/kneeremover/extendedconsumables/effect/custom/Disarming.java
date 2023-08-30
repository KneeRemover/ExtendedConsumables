package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Disarming extends MobEffect {
	public Disarming(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.removeEffect(ModEffects.DISARMING.get());
		if (pLivingEntity instanceof Player player && !pLivingEntity.getLevel().isClientSide) {
			player.drop(player.getMainHandItem(), true);
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}
