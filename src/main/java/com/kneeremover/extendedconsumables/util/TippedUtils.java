package com.kneeremover.extendedconsumables.util;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class TippedUtils {
	public static HashMap<Integer, MobEffect> definedEffects = new HashMap<>();

	public static void defineEffects() {
		for (RegistryObject<MobEffect> effect: ModEffects.MOB_EFFECTS.getEntries()) {
			definedEffects.put(effect.get().getColor(), effect.get());
		}
	}
}
