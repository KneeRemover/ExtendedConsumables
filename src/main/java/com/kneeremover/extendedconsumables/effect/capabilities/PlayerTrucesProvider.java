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

public class PlayerTrucesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<PlayerTruces> PLAYER_TRUCES = CapabilityManager.get(new CapabilityToken<>() {
	});

	private PlayerTruces playerTruces = null;
	private final LazyOptional<PlayerTruces> optional = LazyOptional.of(this::createPlayerTruces);

	private PlayerTruces createPlayerTruces() {
		if(this.playerTruces == null) {
			this.playerTruces = new PlayerTruces();
		}
		return this.playerTruces;
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == PLAYER_TRUCES) {
			return optional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		createPlayerTruces().saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		createPlayerTruces().loadNBTData(nbt);
	}
}
