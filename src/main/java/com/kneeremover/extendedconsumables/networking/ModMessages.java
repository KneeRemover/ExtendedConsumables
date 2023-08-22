package com.kneeremover.extendedconsumables.networking;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.networking.packet.TimeChangeS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
	private static SimpleChannel INSTANCE;

	private static int packetID = 0;
	private static int id() {
		return packetID++;
	}

	public static void register() {
		SimpleChannel net = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(ExtendedConsumables.MOD_ID, "messages"))
				.networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();
		INSTANCE = net;

		net.messageBuilder(TimeChangeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
				.decoder(TimeChangeS2CPacket::new)
				.encoder(TimeChangeS2CPacket::toBytes)
				.consumer(TimeChangeS2CPacket::handle)
				.add();
	}

	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}
	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
}
