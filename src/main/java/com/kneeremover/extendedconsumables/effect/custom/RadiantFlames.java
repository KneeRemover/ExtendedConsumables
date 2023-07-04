package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.RadianceUtils;
import com.kneeremover.extendedconsumables.effect.capabilities.PlayerSaturationOverloadProvider;
import com.kneeremover.extendedconsumables.effect.capabilities.PlayerTrucesProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.LinkedList;
import java.util.List;

public class RadiantFlames extends MobEffect {
	public RadiantFlames(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		double range = 4 + (2 * pAmplifier);
		AABB aabb = pLivingEntity.getBoundingBox().inflate(range, 2.0D, range);
		List<LivingEntity> livingEntities = pLivingEntity.level.getEntitiesOfClass(LivingEntity.class, aabb);
		for (int i = 0; i < 360; i++) {
			double angle = Math.toRadians(i);

			double vx = range * Math.cos(angle);
			double vz = range * Math.sin(angle);
			Level level = pLivingEntity.getLevel();
			if (level instanceof ServerLevel slevel) {
				slevel.sendParticles(ParticleTypes.FLAME, pLivingEntity.getX() + vx, pLivingEntity.getY(), pLivingEntity.getZ() + vz, 5, 0, 0, 0, 0.03);
			}
		}
		pLivingEntity.getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(truces -> {
			if (!livingEntities.isEmpty()) {
				for (LivingEntity livingentity : livingEntities) {
					if (RadianceUtils.targetIsValid(livingentity, truces, pLivingEntity, (int) range) && livingentity.getRemainingFireTicks() < 40) {
						livingentity.setRemainingFireTicks(60);
					}
				}
			}
		});
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return pDuration % 2 == 0;
	}
}
