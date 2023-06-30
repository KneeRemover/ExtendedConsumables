package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.entity.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LastStand extends MobEffect {
	protected LastStand(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}

	private boolean isLastTick = false;

	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.setInvulnerable(true);
		if (isLastTick) {
			pLivingEntity.setInvulnerable(false);
			pLivingEntity.hurt(DamageSources.TIME_RAN_OUT, Float.MAX_VALUE);
			this.isLastTick = false;
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		if (pDuration == 1) {
			this.isLastTick = true;
		}
		return true;
	}
}
