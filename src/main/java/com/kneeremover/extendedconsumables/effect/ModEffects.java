package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<SaturationOverload> SATURATION_OVERLOAD = MOB_EFFECTS.register("saturation_overload", () -> new SaturationOverload(MobEffectCategory.BENEFICIAL, 16751415));
	public static final RegistryObject<StepHeight> STEP_HEIGHT = MOB_EFFECTS.register("step_height", () -> new StepHeight(MobEffectCategory.BENEFICIAL, 6225872));
	public static final RegistryObject<LastStand> LAST_STAND = MOB_EFFECTS.register("last_stand", () -> new LastStand(MobEffectCategory.NEUTRAL, 255));

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
