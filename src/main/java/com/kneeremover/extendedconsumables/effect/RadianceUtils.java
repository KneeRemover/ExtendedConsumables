package com.kneeremover.extendedconsumables.effect;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.capabilities.PlayerTruces;
import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.common.util.LazyOptional;

public class RadianceUtils {
	public static boolean targetIsValid(LivingEntity target, LivingEntity player, int range, boolean affectAllies) {
		var ref = new Object() {
			boolean toReturn = false;
		};
		LazyOptional<PlayerTruces> capability = player.getCapability(PlayerTrucesProvider.PLAYER_TRUCES);
		player.getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(truces -> {
			ref.toReturn = (!truces.getTruces().contains(target.getStringUUID()) // Can't be truced with target
					&& player.distanceTo(target) < range // Makes sure it's a circle, not a square.
					&& player != target
					&& !affectAllies ==
					((target.getType().getCategory() == MobCategory.MONSTER
							|| target.getType() == EntityType.PLAYER)
							|| target.getType() == EntityType.WANDERING_TRADER));
			ExtendedConsumables.LOGGER.info("Capability found!");
		});
		if (!capability.isPresent()) {
			ExtendedConsumables.LOGGER.info("Capability NOT found!");
			return affectAllies == (player.getType().getCategory() == target.getType().getCategory());
		}
		ExtendedConsumables.LOGGER.info("Result: " + ref.toReturn);
		return ref.toReturn;
	}
}
