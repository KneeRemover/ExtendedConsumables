package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.capabilities.PlayerTruces;
import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.effect.RadianceUtils;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RadiantFriendlyDamageLink extends MobEffect {
	public RadiantFriendlyDamageLink(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	private HashMap<UUID, Float> lkh = new HashMap<>();
	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	} // For both balance AND for technical reasons!

	@Override
	public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
		if (pLivingEntity.getEffect(ModEffects.RADIANT_FRIENDLY_DAMAGE_LINK.get()).getDuration() <= 2) { // For this line to run, you must have the effect. I'm sure that you won't have the effect right now and itl throw a NPE though.
			lkh.remove(pLivingEntity);
		} else if (lkh.get(pLivingEntity.getUUID()) == null) {
			lkh.put(pLivingEntity.getUUID(), pLivingEntity.getHealth());
		} else {
			float health = lkh.get(pLivingEntity.getUUID());
			float damageToDeal = health - pLivingEntity.getHealth();
			lkh.put(pLivingEntity.getUUID(), pLivingEntity.getHealth());
			double range = 4 + (2 * pAmplifier);
			AABB aabb = pLivingEntity.getBoundingBox().inflate(range, 2.0D, range);
			List<LivingEntity> livingEntities = pLivingEntity.level.getEntitiesOfClass(LivingEntity.class, aabb);
			for (int i = 0; i < 360; i++) {
				double angle = Math.toRadians(i);

				double vx = range * Math.cos(angle);
				double vz = range * Math.sin(angle);
				Level level = pLivingEntity.getLevel();
				if (level instanceof ServerLevel slevel) {
					slevel.sendParticles(ModParticles.RADIANT_FRIENDLY_DAMAGE_LINK_PARTICLES.get(), pLivingEntity.getX() + vx, pLivingEntity.getY(), pLivingEntity.getZ() + vz, 5, 0, 0, 0, 0.03);
				}
			}

			LazyOptional<PlayerTruces> capability = pLivingEntity.getCapability(PlayerTrucesProvider.PLAYER_TRUCES);
			DamageSource source = pLivingEntity.getLastDamageSource();
			if (capability.isPresent()) {
				capability.ifPresent(truces -> {
					if (!livingEntities.isEmpty()) {
						for (LivingEntity livingentity : livingEntities) { // Is a player
							if (!RadianceUtils.targetIsValid(livingentity, pLivingEntity, (int) range, true) && damageToDeal > 0 && source != null) { // This only works on friendlies!
								livingentity.hurt(source, damageToDeal); // Java ternary operator
							}
						}
					}
				});
			} else {
				if (!livingEntities.isEmpty()) {
					for (LivingEntity livingentity : livingEntities) { // Is not player
						if (pLivingEntity.getType().getCategory() == livingentity.getType().getCategory() && pLivingEntity != livingentity && damageToDeal > 0) { // The category of the entities must match
							livingentity.hurt(source == null ? DamageSource.OUT_OF_WORLD : source, damageToDeal); // Java ternary operator
						}
					}
				}
			}
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return pDuration % 2 == 0;
	}
}
