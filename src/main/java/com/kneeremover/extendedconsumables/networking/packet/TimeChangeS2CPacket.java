package com.kneeremover.extendedconsumables.networking.packet;

import com.kneeremover.extendedconsumables.util.TimeUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeChangeS2CPacket {
	private final long wtt;
	public TimeChangeS2CPacket(long wtt) {
		this.wtt = wtt;
	}

	public TimeChangeS2CPacket(FriendlyByteBuf buf) {
		this.wtt = buf.readLong();
	}

	public void toBytes(FriendlyByteBuf buf) {
		buf.writeLong(wtt);
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			TimeUtils.setClientTargetTime(wtt);
		});
		return true;
	}
}
