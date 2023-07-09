package com.kneeremover.extendedconsumables.entity.custom.projectile;

import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class SplashPotion extends ThrowableItemProjectile implements ItemSupplier {

	private MobEffect mobEffect;
	private int duration;
	private int amplifier;

	public SplashPotion(EntityType<? extends SplashPotion> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public SplashPotion(Level pLevel, LivingEntity pShooter) {
		super(ModEntities.SPLASH_POTION.get(), pShooter, pLevel);
	}

	public SplashPotion(Level pLevel, LivingEntity pShooter, MobEffect me, int duration, int amplifier) {
		super(ModEntities.SPLASH_POTION.get(), pShooter, pLevel);
		this.mobEffect = me;
		this.duration = duration;
		this.amplifier = amplifier;
	}

	protected float getGravity() {
		return 0.05F;
	}

	@Override
	protected void onHit(@NotNull HitResult pResult) {
		super.onHit(pResult);
		if (!this.level.isClientSide) {
			if (mobEffect != null) {
				ItemStack itemstack = this.getItem();
				Potion potion = PotionUtils.getPotion(itemstack);
				this.applySplash(new MobEffectInstance(this.mobEffect, duration, amplifier), pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) pResult).getEntity() : null);

				int i = potion.hasInstantEffects() ? 2007 : 2002;
				this.level.levelEvent(i, this.blockPosition(), new MobEffectInstance(this.mobEffect, duration, amplifier).getEffect().getColor());
			}
			this.discard();
		}
	}

	private void applySplash(MobEffectInstance pEffectInstance, @Nullable Entity pTarget) {
		AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
		List<LivingEntity> livingEntities = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
		List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, aabb);
		if (!items.isEmpty()) {
			int affectedItems = 0; // Limit the number of affected stacks to five.
			for (ItemEntity itementity : items) {
				if ((itementity.getItem().getItem() == Items.ARROW || itementity.getItem().getItem() == ModItems.TIPPED_BOLT_ITEM.get()) && affectedItems <= (64 * 5)) {
					ItemStack toreturn;
					if (itementity.getItem().getItem() == Items.ARROW) {
						toreturn = new ItemStack(ModItems.TIPPED_BOLT_ITEM.get());
					} else {
						toreturn = itementity.getItem();
					}
					CompoundTag tag = toreturn.getOrCreateTag();
					int[] ids = tag.getIntArray("extendedconsumables.effectIDs");
					int[] amplifiers = tag.getIntArray("extendedconsumables.amplifiers");
					int[] durations = tag.getIntArray("extendedconsumables.durations");
					if (Arrays.stream(ids).noneMatch(i -> i == this.mobEffect.getColor())) {
						int[] idsToReturn = new int[ids.length + 1];
						int[] amplifiersToReturn = new int[amplifiers.length + 1];
						int[] durationsToReturn = new int[durations.length + 1];

						int cursor = 0;
						for (int id : ids) {
							idsToReturn[cursor] = id;
							cursor++;
						}

						idsToReturn[ids.length] = this.mobEffect.getColor();
						amplifiersToReturn[ids.length] = this.amplifier;
						durationsToReturn[ids.length] = this.duration;

						tag.putIntArray("extendedconsumables.effectIDs", idsToReturn);
						tag.putIntArray("extendedconsumables.amplifiers", amplifiersToReturn);
						tag.putIntArray("extendedconsumables.durations", durationsToReturn);

						toreturn.setTag(tag);
						toreturn.setCount(itementity.getItem().getCount());
						itementity.setItem(toreturn);
						affectedItems += itementity.getItem().getCount();
					}
				}
			}
		}

		if (!livingEntities.isEmpty()) {
			Entity entity = this.getEffectSource();
			for (LivingEntity livingentity : livingEntities) {
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

	@Override
	protected @NotNull Item getDefaultItem() {
		return ModItems.STEP_HEIGHT_POTION.get();
	}
}
