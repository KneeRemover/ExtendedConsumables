package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.AbstractRadiant;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class RadiantRegen extends AbstractRadiant {
	public RadiantRegen(MobEffectCategory pCategory, int pColor) {
		super(pCategory, MobEffects.REGENERATION, ModParticles.RADIANT_REGEN_PARTICLES, pColor, 0,true);
	}
}
