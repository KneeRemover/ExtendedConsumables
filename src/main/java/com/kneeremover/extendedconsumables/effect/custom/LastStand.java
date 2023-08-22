package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LastStand extends MobEffect {
	public LastStand(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.setInvulnerable(true);

		if (pLivingEntity.getEffect(ModEffects.LAST_STAND.get()).getDuration() <= 3 && pLivingEntity.getHealth() > pLivingEntity.getMaxHealth() / 2) { // For this line to run, you must have the effect. I'm sure that you won't have the effect right now and itl throw a NPE though.
			pLivingEntity.setHealth(pLivingEntity.getMaxHealth() / 2);
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}
