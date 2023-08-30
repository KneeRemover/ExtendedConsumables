package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.effect.custom.*;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class ModEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<MobEffect> SATURATION_OVERLOAD = MOB_EFFECTS.register("saturation_overload", () -> new SaturationOverload(MobEffectCategory.BENEFICIAL, 16751415));
	public static final RegistryObject<MobEffect> STEP_HEIGHT = MOB_EFFECTS.register("step_height", () -> new StepHeight(MobEffectCategory.BENEFICIAL, 10288609));
	public static final RegistryObject<MobEffect> INCREASED_REACH = MOB_EFFECTS.register("increased_reach", () -> new IncreasedReach(MobEffectCategory.BENEFICIAL,8411869));
	public static final RegistryObject<MobEffect> RADIANT_FLAMES = MOB_EFFECTS.register("radiant_flames", () -> new RadiantEffect(MobEffectCategory.BENEFICIAL, (entityWithRadiance, entityToAffect, range) -> {
		if (RadianceUtils.targetIsValid(entityToAffect, entityWithRadiance, range, false)) {
			entityToAffect.setRemainingFireTicks(40);
		}
	}, ParticleTypes.FLAME,16734464, 1));
	public static final RegistryObject<MobEffect> RADIANT_REGEN = MOB_EFFECTS.register("radiant_regen", () -> new RadiantEffect(MobEffectCategory.BENEFICIAL, (entityWithRadiance, entityToAffect, range) -> {
			if (RadianceUtils.targetIsValid(entityToAffect, entityWithRadiance, range, true)) {
				entityToAffect.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 1));
			}
		}, ModParticles.RADIANT_REGEN_PARTICLES, 15886847, 1));
	public static final RegistryObject<MobEffect> RADIANT_SLOWNESS = MOB_EFFECTS.register("radiant_slowness", () -> new RadiantEffect(MobEffectCategory.BENEFICIAL, (entityWithRadiance, entityToAffect, range) -> {
		if (RadianceUtils.targetIsValid(entityToAffect, entityWithRadiance, range, false)) {
			entityToAffect.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
		}
	}, ModParticles.RADIANT_SLOWNESS_PARTICLES, 15886847, 1));
	public static final RegistryObject<MobEffect> FINS = MOB_EFFECTS.register("fins", () -> new Fins(MobEffectCategory.BENEFICIAL,3041184));
	public static final RegistryObject<MobEffect> IMMUNE = MOB_EFFECTS.register("immune", () -> new HardcodeyEffect(MobEffectCategory.BENEFICIAL,	1893087));
	public static final RegistryObject<MobEffect> ATTACK_SPEED_BOOST = MOB_EFFECTS.register("attack_speed_boost", () -> new AttackSpeedBoost(MobEffectCategory.BENEFICIAL,14981376));

	public static final RegistryObject<MobEffect> LAST_STAND = MOB_EFFECTS.register("last_stand", () -> new LastStand(MobEffectCategory.NEUTRAL, 255));
	public static final RegistryObject<MobEffect> IRON_GUN = MOB_EFFECTS.register("iron_gun", () -> new IronGun(MobEffectCategory.NEUTRAL,13553358));

	public static final RegistryObject<MobEffect> DC = MOB_EFFECTS.register("dc", () -> new DC(MobEffectCategory.HARMFUL,1));
	public static final RegistryObject<MobEffect> CRASH = MOB_EFFECTS.register("crash", () -> new Crash(MobEffectCategory.HARMFUL,2));
	public static final RegistryObject<MobEffect> HARD_DAMAGE = MOB_EFFECTS.register("hard_damage", () -> new HardDamage(MobEffectCategory.HARMFUL,8333095));
	public static final RegistryObject<MobEffect> CHAOTIC_TELEPORTATION = MOB_EFFECTS.register("chaotic_teleportation", () -> new ChaoticTeleportation(MobEffectCategory.HARMFUL,9568431));
	public static final RegistryObject<MobEffect> DISARMING = MOB_EFFECTS.register("disarming", () -> new Disarming(MobEffectCategory.HARMFUL,1444096));
	public static final RegistryObject<MobEffect> DISORIENT = MOB_EFFECTS.register("disorient", () -> new Disorient(MobEffectCategory.HARMFUL,16767011));
	public static final RegistryObject<MobEffect> RADIANT_FRIENDLY_DAMAGE_LINK = MOB_EFFECTS.register("radiant_friendly_damage_link", () -> new RadiantFriendlyDamageLink(MobEffectCategory.HARMFUL,6815744)); // To complex for simple lambda statements
	public static final RegistryObject<MobEffect> RADIANT_VIRUS = MOB_EFFECTS.register("radiant_virus", () -> new RadiantEffect(MobEffectCategory.HARMFUL, (entityWithRadiance, entityToAffect, range) -> {
		if (RadianceUtils.targetIsValid(entityToAffect, entityWithRadiance, range, true)) {
			if (!entityToAffect.hasEffect(ModEffects.IMMUNE.get())) {
				int duration = entityWithRadiance.getEffect(ModEffects.RADIANT_VIRUS.get()).getDuration();
				entityToAffect.addEffect(new MobEffectInstance(ModEffects.RADIANT_VIRUS.get(), duration, 1));
				entityToAffect.addEffect(new MobEffectInstance(ModEffects.IMMUNE.get(), (int) (duration * 1.5), 1));
			}
		}
	}, ModParticles.RADIANT_VIRUS_PARTICLES, 15886847, 4));
	public static final RegistryObject<MobEffect> SHATTERED_DEFENSE = MOB_EFFECTS.register("shattered_defense", () -> new ShatteredDefense(MobEffectCategory.HARMFUL,5650999));
	public static final RegistryObject<MobEffect> EFFECT_REMOVER = MOB_EFFECTS.register("effect_remover", () -> new EffectRemover(MobEffectCategory.HARMFUL,16777215));
	public static final RegistryObject<MobEffect> IGNITION = MOB_EFFECTS.register("ignition", () -> new Ignition(MobEffectCategory.HARMFUL,16725760));
	public static final RegistryObject<MobEffect> AGING = MOB_EFFECTS.register("aging", () -> new Aging(MobEffectCategory.HARMFUL,65338));

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
