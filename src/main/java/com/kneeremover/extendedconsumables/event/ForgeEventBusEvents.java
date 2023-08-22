package com.kneeremover.extendedconsumables.event;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.capabilities.PlayerSaturationOverloadProvider;
import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.capabilities.WorldTargetTimeProvider;
import com.kneeremover.extendedconsumables.networking.ModMessages;
import com.kneeremover.extendedconsumables.networking.packet.TimeChangeS2CPacket;
import com.kneeremover.extendedconsumables.util.TimeUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEventBusEvents {
	@SubscribeEvent
	public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			if(!event.getObject().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).isPresent()) {
				event.addCapability(new ResourceLocation(ExtendedConsumables.MOD_ID, "satov"), new PlayerSaturationOverloadProvider());
			}
			if(!event.getObject().getCapability(PlayerTrucesProvider.PLAYER_TRUCES).isPresent()) {
				event.addCapability(new ResourceLocation(ExtendedConsumables.MOD_ID, "truces"), new PlayerTrucesProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event) {
		if(!event.getObject().getCapability(WorldTargetTimeProvider.WORLD_TARGET_TIME).isPresent()) {
			event.addCapability(new ResourceLocation(ExtendedConsumables.MOD_ID, "wtt"), new WorldTargetTimeProvider());
		}
	}

	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if(event.isWasDeath()) {
			event.getOriginal().reviveCaps();

			event.getOriginal().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(oldStore -> event.getEntity().getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(newStore -> {
				newStore.copyFrom(oldStore);
				if (!event.getPlayer().getLevel().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) { // If not keep inventory, zero out extra saturation
					newStore.zeroOverload();
				}
			}));
			event.getOriginal().getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(oldStore -> event.getEntity().getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(newStore -> {
				newStore.copyFrom(oldStore);
			}));

			event.getOriginal().invalidateCaps();
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

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			TimeUtils.tickServer(event);
		}
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			TimeUtils.tickClient();
		}
	}

	@SubscribeEvent
	public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (!event.getWorld().isClientSide) {
			if (event.getEntity() instanceof ServerPlayer player) {
				event.getWorld().getCapability(WorldTargetTimeProvider.WORLD_TARGET_TIME).ifPresent(wtt -> {
					ModMessages.sendToPlayer(new TimeChangeS2CPacket(wtt.getTarget()), player);
				});
			}
		}
	}
}
