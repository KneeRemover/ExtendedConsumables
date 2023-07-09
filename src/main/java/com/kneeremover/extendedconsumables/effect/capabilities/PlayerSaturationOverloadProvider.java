package com.kneeremover.extendedconsumables.effect.capabilities;

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

public class PlayerSaturationOverloadProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<PlayerSaturationOverload> PLAYER_SATURATION_OVERLOAD = CapabilityManager.get(new CapabilityToken<>() {
	});

	private PlayerSaturationOverload saturationOverload = null;
	private final LazyOptional<PlayerSaturationOverload> optional = LazyOptional.of(this::createPlayerSaturationOverload);

	private PlayerSaturationOverload createPlayerSaturationOverload() {
		if(this.saturationOverload == null) {
			this.saturationOverload = new PlayerSaturationOverload();
		}
		return this.saturationOverload;
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == PLAYER_SATURATION_OVERLOAD) {
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
