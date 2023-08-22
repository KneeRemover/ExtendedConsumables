package com.kneeremover.extendedconsumables.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Potionoliger extends AbstractIllager implements RangedAttackMob, IAnimatable {
//private AnimationFactory factory = new AnimationFactory(this)

private static final EntityDataAccessor<Boolean> DATA_USING_ITEM = SynchedEntityData.defineId(Witch.class, EntityDataSerializers.BOOLEAN);
	public boolean isDrinkingPotion() {
		return this.getEntityData().get(DATA_USING_ITEM);
	}

	@SuppressWarnings("unused")
	public void performRangedAttack(@NotNull LivingEntity pTarget, float pDistanceFactor) {
		if (!this.isDrinkingPotion()) {
			Vec3 vec3 = pTarget.getDeltaMovement();
			double d0 = pTarget.getX() + vec3.x - this.getX();
			double d1 = pTarget.getEyeY() - (double)1.1F - this.getY();
			double d2 = pTarget.getZ() + vec3.z - this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			Potion potion = Potions.HARMING;
			if (pTarget instanceof Raider) {
				if (pTarget.getHealth() <= 4.0F) {
					potion = Potions.HEALING;
				}}}}


	public Potionoliger(EntityType<? extends Potionoliger> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}
	public boolean isAlliedTo(@NotNull Entity pEntity) {
		if (super.isAlliedTo(pEntity)) {
			return true;
		} else if (pEntity instanceof LivingEntity && ((LivingEntity)pEntity).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && pEntity.getTeam() == null;
		} else {
			return false;
		}
	}
	public @NotNull SoundEvent getCelebrateSound() {
		return SoundEvents.VINDICATOR_CELEBRATE;
	}

	public void applyRaidBuffs(int pWave, boolean pUnusedFalse) {
	}

	@SuppressWarnings("unused")
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30).add(Attributes.MOVEMENT_SPEED, 1D);
}

	@Override
	public void registerControllers(AnimationData data) {

	}

	@Override
	public AnimationFactory getFactory() {
		return null;
	}
}