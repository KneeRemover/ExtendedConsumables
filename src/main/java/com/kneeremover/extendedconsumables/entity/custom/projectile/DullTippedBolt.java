package com.kneeremover.extendedconsumables.entity.custom.projectile;

import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.item.custom.TippedBoltItem;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DullTippedBolt extends TippedBolt {
	public DullTippedBolt(Level pLevel, double pX, double pY, double pZ) {
		super(pLevel, pX, pY, pZ);
		super.setBaseDamage(0);
	}

	public DullTippedBolt(Level pLevel, LivingEntity pShooter) {
		super(pLevel, pShooter);
		super.setBaseDamage(0);
	}

	public DullTippedBolt(EntityType<DullTippedBolt> tippedBoltEntityType, Level level) {
		super(tippedBoltEntityType, level);
		super.setBaseDamage(0);
	}
}
