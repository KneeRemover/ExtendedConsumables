package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.effect.RadianceUtils;
import com.kneeremover.extendedconsumables.effect.capabilities.PlayerTrucesProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractRadiant extends MobEffect {
	public AbstractRadiant(MobEffectCategory pCategory,  RegistryObject<MobEffect> targetEffect, RegistryObject<SimpleParticleType> particleType, int pColor, int amplifier, boolean isPositive) {
		super(pCategory, pColor);
		this.isPositive = isPositive;
		this.amplifier = amplifier;
		this.targetEffect = targetEffect;
		this.particleType = particleType;
	}
	public AbstractRadiant(MobEffectCategory pCategory,  MobEffect targetEffect, RegistryObject<SimpleParticleType> particleType, int pColor, int amplifier, boolean isPositive) {
		super(pCategory, pColor);
		this.isPositive = isPositive;
		this.amplifier = amplifier;
		this.targetEffectVanilla = targetEffect;
		this.particleType = particleType;
	}
	private final int amplifier;
	private final boolean isPositive;
	private RegistryObject<MobEffect> targetEffect;
	private MobEffect targetEffectVanilla;
	private final RegistryObject<SimpleParticleType> particleType;
	private MobEffect usableTargetEffect;

	@Override
	public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
		if (targetEffect == null) { // Check which initializer was used
			usableTargetEffect = targetEffectVanilla;
		} else {
			usableTargetEffect = targetEffect.get();
		}
		double range = 4 + (2 * pAmplifier);
		AABB aabb = pLivingEntity.getBoundingBox().inflate(range, 2.0D, range);
		List<LivingEntity> livingEntities = pLivingEntity.level.getEntitiesOfClass(LivingEntity.class, aabb);
		for (int i = 0; i < 360; i++) {
			double angle = Math.toRadians(i);

			double vx = range * Math.cos(angle);
			double vz = range * Math.sin(angle);
			Level level = pLivingEntity.getLevel();
			if (level instanceof ServerLevel slevel) {
				slevel.sendParticles(particleType.get(), pLivingEntity.getX() + vx, pLivingEntity.getY(), pLivingEntity.getZ() + vz, 5, 0, 0, 0, 0.03);
			}
		}
		pLivingEntity.getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(truces -> {
			if (!livingEntities.isEmpty()) {
				for (LivingEntity livingentity : livingEntities) {
					MobEffectInstance effect = livingentity.getEffect(usableTargetEffect); // No clones of myself please (haha get it bc my name is NullPointerException haha im so funny)
					if ((!isPositive == RadianceUtils.targetIsValid(livingentity, truces, pLivingEntity, (int) range)) && (effect == null || effect.getDuration() < 40)) {
						livingentity.addEffect(new MobEffectInstance(usableTargetEffect, 60, amplifier));
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
