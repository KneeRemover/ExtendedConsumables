package com.kneeremover.extendedconsumables.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldTargetTimeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<WorldTargetTime> WORLD_TARGET_TIME = CapabilityManager.get(new CapabilityToken<>() {
	});

	private WorldTargetTime worldTargetTime = null;
	private final LazyOptional<WorldTargetTime> optional = LazyOptional.of(this::createPlayerSaturationOverload);

	private WorldTargetTime createPlayerSaturationOverload() {
		if(this.worldTargetTime == null) {
			this.worldTargetTime = new WorldTargetTime();
		}
		return this.worldTargetTime;
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == WORLD_TARGET_TIME) {
			return optional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		createPlayerSaturationOverload().saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		createPlayerSaturationOverload().loadNBTData(nbt);
	}
}
