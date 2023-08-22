package com.kneeremover.extendedconsumables.util;

import com.kneeremover.extendedconsumables.capabilities.WorldTargetTimeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;

public class TimeUtils {

	private static long wtt = -1;

	public static void setClientTargetTime(Long target) {
		wtt = target;
	}

	public static void tickClient() {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
				if (wtt != -1) {
					if (Math.abs(level.getDayTime() - wtt) < 150) {
						wtt = -1;
					} else {
						long timeToSet = level.getDayTime() + (wtt - level.getDayTime()) / 50;
						level.setDayTime(timeToSet);
					}
				}
		}
	}

	public static void tickServer(TickEvent.WorldTickEvent event) {
		if (event.world instanceof ServerLevel level) {
			level.getCapability(WorldTargetTimeProvider.WORLD_TARGET_TIME).ifPresent(wtt -> {
				if (wtt.getTarget() != -1) {
					if (Math.abs(level.getDayTime() - wtt.getTarget()) < 150) {
						wtt.setTarget(-1);
					} else {
						long timeToSet = level.getDayTime() + (wtt.getTarget() - level.getDayTime()) / 50;
						level.setDayTime(timeToSet);
					}
				}
			});
		}
	}
}
