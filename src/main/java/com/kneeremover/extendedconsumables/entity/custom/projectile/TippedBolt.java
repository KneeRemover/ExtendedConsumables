package com.kneeremover.extendedconsumables.entity.custom.projectile;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TippedBolt extends AbstractArrow {
	private static final int EXPOSED_POTION_DECAY_TIME = 600;
	private static final int NO_EFFECT_COLOR = -1;
	private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(TippedBolt.class, EntityDataSerializers.INT);
	private static final byte EVENT_POTION_PUFF = 0;
	private final List<MobEffectInstance> effectInstances = new ArrayList<>();
	private int[] effects = null;
	private int[] durations = null;
	private int[] amplifiers = null;
	private boolean fixedColor;

	public TippedBolt(Level pLevel, double pX, double pY, double pZ) {
		super(ModEntities.TIPPED_BOLT.get(), pX, pY, pZ, pLevel);
	}

	public TippedBolt(Level pLevel, LivingEntity pShooter) {
		super(ModEntities.TIPPED_BOLT.get(), pShooter, pLevel);
	}

	public TippedBolt(EntityType<TippedBolt> tippedBoltEntityType, Level level) {
		super(tippedBoltEntityType, level);
	}

	public void setEffectsFromItem(ItemStack pStack) {
		HashMap<Integer, MobEffect> definedEffects = new HashMap<>();
		definedEffects.put(ModEffects.LAST_STAND.get().getColor(), ModEffects.LAST_STAND.get());
		definedEffects.put(ModEffects.STEP_HEIGHT.get().getColor(), ModEffects.STEP_HEIGHT.get());
		definedEffects.put(ModEffects.SATURATION_OVERLOAD.get().getColor(), ModEffects.SATURATION_OVERLOAD.get());
		int[] effectIDs = pStack.getOrCreateTag().getIntArray("extendedconsumables.effectIDs");
		int[] durations = pStack.getOrCreateTag().getIntArray("extendedconsumables.durations");
		int[] amplifiers = pStack.getOrCreateTag().getIntArray("extendedconsumables.amplifiers");
		this.effects = effectIDs;
		this.durations = durations;
		this.amplifiers = amplifiers;
		int index = 0;
		for (int effectID : effectIDs) {
			this.effectInstances.add(new MobEffectInstance(definedEffects.get(effectID), durations[index], amplifiers[index]));
			index++;
			int i = getCustomColor(pStack);
			if (i == -1) {
				this.updateColor();
			} else {
				this.setFixedColor(i);
			}
		}
	}

	public static int getCustomColor(ItemStack pStack) {
		CompoundTag compoundtag = pStack.getTag();
		return compoundtag != null && compoundtag.contains("CustomPotionColor", 99) ? compoundtag.getInt("CustomPotionColor") : -1;
	}

	private void updateColor() {
		this.fixedColor = false;
		this.entityData.set(ID_EFFECT_COLOR, this.effectInstances.get(0).getEffect().getColor());

	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ID_EFFECT_COLOR, -1);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
			if (this.inGround) {
				if (this.inGroundTime % 5 == 0) {
					this.makeParticle(1);
				}
			} else {
				this.makeParticle(2);
			}
		} else if (this.inGround && this.inGroundTime != 0 && !this.effectInstances.isEmpty() && this.inGroundTime >= 600) {
			this.level.broadcastEntityEvent(this, (byte) 0);
			this.effectInstances.clear();
			this.entityData.set(ID_EFFECT_COLOR, -1);
		}

	}

	private void makeParticle(int pParticleAmount) {
		int i = this.getColor();
		if (i != -1 && pParticleAmount > 0) {
			double d0 = (double) (i >> 16 & 255) / 255.0D;
			double d1 = (double) (i >> 8 & 255) / 255.0D;
			double d2 = (double) (i >> 0 & 255) / 255.0D;

			for (int j = 0; j < pParticleAmount; ++j) {
				this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
			}

		}
	}

	public int getColor() {
		return this.entityData.get(ID_EFFECT_COLOR);
	}

	private void setFixedColor(int pFixedColor) {
		this.fixedColor = true;
		this.entityData.set(ID_EFFECT_COLOR, pFixedColor);
	}

	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);

		if (this.fixedColor) {
			pCompound.putInt("Color", this.getColor());
		}

		if (!this.effectInstances.isEmpty()) {
			ListTag listtag = new ListTag();

			for (MobEffectInstance mobeffectinstance : this.effectInstances) {
				listtag.add(mobeffectinstance.save(new CompoundTag()));
			}

			pCompound.put("CustomPotionEffects", listtag);
		}

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("Color", 99)) {
			this.setFixedColor(pCompound.getInt("Color"));
		} else {
			this.updateColor();
		}
	}

	protected void doPostHurtEffects(LivingEntity pLiving) {
		super.doPostHurtEffects(pLiving);
		Entity entity = this.getEffectSource();
		for (MobEffectInstance mobeffectinstance : effectInstances) {
			pLiving.addEffect(mobeffectinstance);
		}

		if (!this.effectInstances.isEmpty()) {
			for (MobEffectInstance mobeffectinstance1 : this.effectInstances) {
				pLiving.addEffect(mobeffectinstance1, entity);
			}
		}

	}

	protected ItemStack getPickupItem() {
		ItemStack itemstack = new ItemStack(ModItems.TIPPED_BOLT_ITEM.get());
		itemstack.getOrCreateTag().putIntArray("extendedconsumables.effectIDs", effects);
		itemstack.getOrCreateTag().putIntArray("extendeddconsumables.durations", durations);
		itemstack.getOrCreateTag().putIntArray("extendedconsumables.amplifiers", amplifiers);
		if (this.fixedColor) {
			itemstack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
		}

		return itemstack;
	}

	/**
	 * Handles an entity event fired from {@link net.minecraft.world.level.Level#broadcastEntityEvent}.
	 */
	public void handleEntityEvent(byte pId) {
		if (pId == 0) {
			int i = this.getColor();
			if (i != -1) {
				double d0 = (double) (i >> 16 & 255) / 255.0D;
				double d1 = (double) (i >> 8 & 255) / 255.0D;
				double d2 = (double) (i >> 0 & 255) / 255.0D;

				for (int j = 0; j < 20; ++j) {
					this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
				}
			}
		} else {
			super.handleEntityEvent(pId);
		}

	}
}
