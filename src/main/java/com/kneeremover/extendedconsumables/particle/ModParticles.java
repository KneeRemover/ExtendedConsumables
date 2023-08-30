package com.kneeremover.extendedconsumables.particle;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<SimpleParticleType> RADIANT_REGEN_PARTICLES = PARTICLE_TYPES.register("radiant_regen_particles", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RADIANT_SLOWNESS_PARTICLES = PARTICLE_TYPES.register("radiant_slowness_particles", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RADIANT_FRIENDLY_DAMAGE_LINK_PARTICLES = PARTICLE_TYPES.register("radiant_friendly_damage_link_particles", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RADIANT_VIRUS_PARTICLES = PARTICLE_TYPES.register("radiant_virus_particles", () -> new SimpleParticleType(true));

	public static void register(IEventBus eventBus) {
		PARTICLE_TYPES.register(eventBus);
	}
}
