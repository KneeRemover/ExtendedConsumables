package com.kneeremover.extendedconsumables.capabilities;

import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;
import java.util.LinkedList;

public class PlayerTruces {
	private LinkedList<String> truces = new LinkedList<>();

	public LinkedList<String> getTruces() {
		return truces;
	}
	public void addTruce(String toAdd) {
		truces.add(toAdd);
	}

	public void subTruce(String toSub) {
		truces.remove(toSub);
	}

	public void copyFrom(PlayerTruces source) {
		this.truces = source.truces;
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putString("extendedconsumables.truces", String.join("|", truces));
	}

	public void loadNBTData(CompoundTag nbt) {
		truces.addAll(Arrays.stream(nbt.getString("extendedconsumables.truces").split("\\|")).toList());
	}
}
