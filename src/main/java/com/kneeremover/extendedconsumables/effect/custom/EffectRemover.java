package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.util.Randomizer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collection;

public class EffectRemover extends MobEffect {
	public EffectRemover(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		if (!pLivingEntity.getLevel().isClientSide) {
			Collection<MobEffectInstance> activeEffects = new ArrayList<>(pLivingEntity.getActiveEffects());
			for (int i = 0; i < activeEffects.size() + 5; i++) {
				MobEffectInstance effectToRemove = Randomizer.getRandomElementFromCollection(activeEffects);
				if (effectToRemove != null && effectToRemove.getEffect() != ModEffects.EFFECT_REMOVER.get() && effectToRemove.isCurativeItem(Items.MILK_BUCKET.getDefaultInstance())) {
					pLivingEntity.removeEffect(effectToRemove.getEffect());
					break;
				} else {
					activeEffects.remove(effectToRemove);
				}
			}
			pLivingEntity.removeEffect(ModEffects.EFFECT_REMOVER.get()); // Do this first so it doesn't have a chance to remove itself twice!
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return pDuration % 10 == 0;
	}
}
