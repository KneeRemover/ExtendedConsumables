package com.kneeremover.extendedconsumables.effect.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerSaturationOverload {
	private float overload;

	public float getOverload() {
		return overload;
	}
	public void addOverload(float toAdd) {
		overload += toAdd;
	}

	public void subOverload(float toSub) {
		overload -= toSub;
	}

	public void zeroOverload() {
		overload = 0;
	}

	public void copyFrom(PlayerSaturationOverload source) {
		this.overload = source.overload;
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putFloat("extendedconsumables.overload", overload);
	}

	public void loadNBTData(CompoundTag nbt) {
		overload = nbt.getFloat("extendedconsumables.overload");
	}
}
