package com.kneeremover.extendedconsumables.entity.custom.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

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
