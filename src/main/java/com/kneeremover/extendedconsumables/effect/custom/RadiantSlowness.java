package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.AbstractRadiant;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class RadiantSlowness extends AbstractRadiant {
	public RadiantSlowness(MobEffectCategory pCategory, int pColor) {
		super(pCategory, MobEffects.MOVEMENT_SLOWDOWN, ModParticles.RADIANT_SLOWNESS_PARTICLES, pColor, 2, false);
	}
}
