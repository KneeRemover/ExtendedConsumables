package com.kneeremover.extendedconsumables.capabilities;

import net.minecraft.nbt.CompoundTag;

public class WorldTargetTime {
	private int targetTime;

	public int getTarget() {
		return targetTime;
	}
	public void setTarget(int toSet) {
		targetTime = toSet;
	}
	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt("extendedconsumables.targetTime", targetTime);
	}
	public void loadNBTData(CompoundTag nbt) {
		targetTime = nbt.getInt("extendedconsumables.targetTime");
	}
}
