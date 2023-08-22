package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.effect.RadianceUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RadiantFlames extends MobEffect {
	public RadiantFlames(MobEffectCategory pCategory, int pColor) { // Flames isn't a potion effect, so unfortunately I can't use the Abstract.
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
