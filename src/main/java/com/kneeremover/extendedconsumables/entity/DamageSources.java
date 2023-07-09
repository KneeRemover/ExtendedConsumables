package com.kneeremover.extendedconsumables.entity;

import com.kneeremover.extendedconsumables.entity.custom.projectile.TippedBolt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class DamageSources {
	public static final DamageSource TIME_RAN_OUT = (new DamageSource("extendedconsumables.time_ran_out")).bypassArmor().bypassInvul();
	@SuppressWarnings("unused")
	public static DamageSource TIPPED_BOLT(TippedBolt pArrow, @Nullable Entity pIndirectEntity) {
		return (new IndirectEntityDamageSource("arrow", pArrow, pIndirectEntity)).setProjectile();
	}
}
