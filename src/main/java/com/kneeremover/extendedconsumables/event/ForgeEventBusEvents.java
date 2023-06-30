package com.kneeremover.extendedconsumables.event;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.effect.capabilities.PlayerSaturationOverloadProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEventBusEvents {
	@SubscribeEvent
	public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			if(!event.getObject().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).isPresent()) {
				event.addCapability(new ResourceLocation(ExtendedConsumables.MOD_ID, "properties"), new PlayerSaturationOverloadProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if(event.isWasDeath()) {
			event.getOriginal().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(oldStore -> {
				event.getOriginal().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(newStore -> {
					newStore.copyFrom(oldStore);
				});
			});
		}
	}

	@SubscribeEvent
	public static void eatFood(LivingEntityUseItemEvent.Finish evt) {
		evt.getEntityLiving().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(sat -> {
			if (evt.getItem().getFoodProperties(evt.getEntityLiving()) != null) {
				FoodProperties food = evt.getItem().getFoodProperties(evt.getEntityLiving());
				Player player = ((Player) evt.getEntityLiving());
				if (food.getNutrition() + food.getSaturationModifier() + player.getFoodData().getSaturationLevel() > 20) {
					sat.addOverload((food.getNutrition() + food.getSaturationModifier() + player.getFoodData().getSaturationLevel()) - 20);
				}
			}
		});
	}
}
