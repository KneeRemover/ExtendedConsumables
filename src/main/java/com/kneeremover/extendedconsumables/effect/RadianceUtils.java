package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.effect.capabilities.PlayerTruces;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;

public class RadianceUtils {
	public static boolean targetIsValid (LivingEntity target, PlayerTruces truces, LivingEntity player, int range) {
		return !truces.getTruces().contains(target.getStringUUID()) // Can't be truced with target
				&& player.distanceTo(target) < range // Makes sure it's a circle, not a square.
				&& player != target
				&& (target.getType().getCategory() == MobCategory.MONSTER || target.getType() == EntityType.PLAYER);
	}
}
