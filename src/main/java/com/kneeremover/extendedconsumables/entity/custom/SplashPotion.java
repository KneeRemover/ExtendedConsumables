package com.kneeremover.extendedconsumables.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

public class SplashPotion extends ThrownPotion {

	private MobEffectInstance mobEffect;

	public SplashPotion(EntityType<? extends SplashPotion> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public SplashPotion(Level pLevel, LivingEntity pShooter) {
		super(pLevel, pShooter);
	}

	public void setMobEffect(MobEffectInstance mei) {
		this.mobEffect = mei;
	}

	@Override
	protected void onHit(HitResult pResult) {
		super.onHit(pResult);
		if (!this.level.isClientSide) {
			if (mobEffect != null) {
				ItemStack itemstack = this.getItem();
				Potion potion = PotionUtils.getPotion(itemstack);
				this.applySplash(this.mobEffect, pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) pResult).getEntity() : null);

				int i = potion.hasInstantEffects() ? 2007 : 2002;
				this.level.levelEvent(i, this.blockPosition(), PotionUtils.getColor(itemstack));
			}
			this.discard();
		}
	}

	private void applySplash(MobEffectInstance pEffectInstance, @Nullable Entity pTarget) {
		AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
		if (!list.isEmpty()) {
			Entity entity = this.getEffectSource();
			for (LivingEntity livingentity : list) {
				if (livingentity.isAffectedByPotions()) {
					double d0 = this.distanceToSqr(livingentity);
					if (d0 < 16.0D) {
						double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
						if (livingentity == pTarget) {
							d1 = 1.0D;
						}
						MobEffect mobeffect = pEffectInstance.getEffect();
						if (mobeffect.isInstantenous()) {
							mobeffect.applyInstantenousEffect(this, this.getOwner(), livingentity, pEffectInstance.getAmplifier(), d1);
						} else {
							int i = (int) (d1 * (double) pEffectInstance.getDuration() + 0.5D);
							if (i > 20) {
								livingentity.addEffect(new MobEffectInstance(mobeffect, i, pEffectInstance.getAmplifier(), pEffectInstance.isAmbient(), pEffectInstance.isVisible()), entity);
							}
						}
					}
				}
			}
		}
	}
}
