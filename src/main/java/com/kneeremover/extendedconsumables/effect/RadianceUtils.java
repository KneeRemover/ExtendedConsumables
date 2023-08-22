package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.capabilities.PlayerTruces;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;

public class RadianceUtils {
	public static boolean targetIsValid (LivingEntity target, PlayerTruces truces, LivingEntity player, int range) {
		return (!truces.getTruces().contains(target.getStringUUID()) // Can't be truced with target
				&& player.distanceTo(target) < range // Makes sure it's a circle, not a square.
				&& player != target
				&& (target.getType().getCategory() == MobCategory.MONSTER || target.getType() == EntityType.PLAYER))
				|| target.getType() == EntityType.WANDERING_TRADER; // Hardcode to kill wandering traders because yes.
	}
}
