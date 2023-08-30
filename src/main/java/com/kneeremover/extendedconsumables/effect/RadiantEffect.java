package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RadiantEffect extends MobEffect {
	public interface OnRadiantEffect {
		void applyEffect(LivingEntity entityWithRadiance, LivingEntity entityToAffect, int range);
	}

	public RadiantEffect(MobEffectCategory pCategory, OnRadiantEffect ore, RegistryObject<SimpleParticleType> particleType, int pColor, int particleMod) {
		super(pCategory, pColor);
		this.particleType = particleType;
		this.ore = ore;
		this.particleMod = particleMod;
	}
	public RadiantEffect(MobEffectCategory pCategory, OnRadiantEffect ore, SimpleParticleType particleType, int pColor, int particleMod) {
		super(pCategory, pColor);
		this.vanillaParticleType = particleType;
		this.ore = ore;
		this.particleMod = particleMod;
	}
	public final OnRadiantEffect ore;
	public SimpleParticleType vanillaParticleType;
	public RegistryObject<SimpleParticleType> particleType;
	public final int particleMod;

	@Override
	public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
		double range = 4 + (2 * pAmplifier);
		AABB aabb = pLivingEntity.getBoundingBox().inflate(range, 2.0D, range);
		List<LivingEntity> livingEntities = pLivingEntity.level.getEntitiesOfClass(LivingEntity.class, aabb);
		for (int i = 0; i < 360 / particleMod; i++) {
			double angle = Math.toRadians(i * particleMod);

			double vx = range * Math.cos(angle);
			double vz = range * Math.sin(angle);
			Level level = pLivingEntity.getLevel();
			if (level instanceof ServerLevel slevel) {
				if (pLivingEntity.getRandom().nextInt(10) > particleMod) {
					slevel.sendParticles(particleType == null ? vanillaParticleType : particleType.get(), pLivingEntity.getX() + vx, pLivingEntity.getY(), pLivingEntity.getZ() + vz, 5, 0, 0, 0, 0.03);
				}
			}
		}
		if (!livingEntities.isEmpty()) {
			for (LivingEntity livingentity : livingEntities) {
				ore.applyEffect(pLivingEntity, livingentity, (int) range);
			}
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return pDuration % 2 == 0;
	}
}
