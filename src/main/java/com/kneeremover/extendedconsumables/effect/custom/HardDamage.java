package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

public class HardDamage extends MobEffect {
	private HashMap<UUID, Float> lkh = new HashMap<>();

	public HardDamage(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		if (pLivingEntity.hasEffect(ModEffects.SHATTERED_DEFENSE.get())) {
			pLivingEntity.removeEffect(ModEffects.SHATTERED_DEFENSE.get());
		}

		if (pLivingEntity.getEffect(ModEffects.HARD_DAMAGE.get()).getDuration() <= 2) { // For this line to run, you must have the effect. I'm sure that you won't have the effect right now and itl throw a NPE though.
			lkh.remove(pLivingEntity);
		} else if (lkh.get(pLivingEntity.getUUID()) == null) {
			lkh.put(pLivingEntity.getUUID(), pLivingEntity.getHealth());
		} else {
			float previousHighestHealth = lkh.get(pLivingEntity.getUUID());
			pLivingEntity.setHealth(Math.min(pLivingEntity.getHealth(), previousHighestHealth));
			lkh.put(pLivingEntity.getUUID(), pLivingEntity.getHealth());
		}
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return (pDuration + 1) % 2 == 0;
	}
}
