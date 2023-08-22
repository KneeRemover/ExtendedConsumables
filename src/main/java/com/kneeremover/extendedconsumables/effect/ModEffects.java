package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.effect.custom.*;
import com.kneeremover.extendedconsumables.item.custom.TippedBoltItem;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<MobEffect> SATURATION_OVERLOAD = MOB_EFFECTS.register("saturation_overload", () -> new SaturationOverload(MobEffectCategory.BENEFICIAL, 16751415));
	public static final RegistryObject<MobEffect> STEP_HEIGHT = MOB_EFFECTS.register("step_height", () -> new StepHeight(MobEffectCategory.BENEFICIAL, 10288609));
	public static final RegistryObject<MobEffect> INCREASED_REACH = MOB_EFFECTS.register("increased_reach", () -> new IncreasedReach(MobEffectCategory.BENEFICIAL,8411869));
	public static final RegistryObject<MobEffect> RADIANT_FLAMES = MOB_EFFECTS.register("radiant_flames", () -> new RadiantFlames(MobEffectCategory.BENEFICIAL,16734464));
	public static final RegistryObject<MobEffect> RADIANT_REGEN = MOB_EFFECTS.register("radiant_regen", () -> new RadiantEffect(MobEffectCategory.BENEFICIAL, MobEffects.REGENERATION,
			ModParticles.RADIANT_REGEN_PARTICLES, 15886847, 0,true));
	public static final RegistryObject<MobEffect> RADIANT_SLOWNESS = MOB_EFFECTS.register("radiant_slowness", () -> new RadiantEffect(MobEffectCategory.BENEFICIAL, MobEffects.MOVEMENT_SLOWDOWN,
			ModParticles.RADIANT_SLOWNESS_PARTICLES, 3753297, 2, false));
	public static final RegistryObject<MobEffect> FINS = MOB_EFFECTS.register("fins", () -> new Fins(MobEffectCategory.BENEFICIAL,3041184));

	public static final RegistryObject<MobEffect> LAST_STAND = MOB_EFFECTS.register("last_stand", () -> new LastStand(MobEffectCategory.NEUTRAL, 255));
	public static final RegistryObject<MobEffect> IRON_GUN = MOB_EFFECTS.register("iron_gun", () -> new IronGun(MobEffectCategory.NEUTRAL,13553358));

	public static final RegistryObject<MobEffect> DC = MOB_EFFECTS.register("dc", () -> new DC(MobEffectCategory.HARMFUL,1));
	public static final RegistryObject<MobEffect> CRASH = MOB_EFFECTS.register("crash", () -> new Crash(MobEffectCategory.HARMFUL,2));
	public static final RegistryObject<MobEffect> RADIANT_FRIENDLY_DAMAGE_LINK = MOB_EFFECTS.register("radiant_friendly_damage_link", () -> new RadiantFriendlyDamageLink(MobEffectCategory.HARMFUL,6815744));
	public static final RegistryObject<MobEffect> SHATTERED_DEFENSE = MOB_EFFECTS.register("shattered_defense", () -> new ShatteredDefense(MobEffectCategory.HARMFUL,5650999));
	public static final RegistryObject<MobEffect> EFFECT_REMOVER = MOB_EFFECTS.register("effect_remover", () -> new EffectRemover(MobEffectCategory.HARMFUL,16777215));
	public static final RegistryObject<MobEffect> IGNITION = MOB_EFFECTS.register("ignition", () -> new Ignition(MobEffectCategory.HARMFUL,16725760));
	public static final RegistryObject<MobEffect> AGING = MOB_EFFECTS.register("aging", () -> new Aging(MobEffectCategory.HARMFUL,65338));

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
