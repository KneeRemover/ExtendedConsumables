package com.kneeremover.extendedconsumables.potion;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<Potion> STEP_HEIGHT_POTION = POTIONS.register("step_height_potion", () -> new Potion(new MobEffectInstance(ModEffects.STEP_HEIGHT.get(), 200, 0)));

	public static void register(IEventBus eventBus) {
		POTIONS.register(eventBus);
	}
}
