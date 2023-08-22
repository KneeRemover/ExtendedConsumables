package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.capabilities.WorldTargetTimeProvider;
import com.kneeremover.extendedconsumables.item.ExtendedScroll;
import com.kneeremover.extendedconsumables.networking.ModMessages;
import com.kneeremover.extendedconsumables.networking.packet.TimeChangeS2CPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class ScrollOfDay extends ExtendedScroll { // The friends we made along the way
	public ScrollOfDay() {
		super(true, "scroll_day");
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		if (pLevel instanceof ServerLevel slevel) {
			slevel.getCapability(WorldTargetTimeProvider.WORLD_TARGET_TIME).ifPresent(targetTime -> {
				targetTime.setTarget(6000);
				for (ServerPlayer player:((ServerLevel) pLevel).getPlayers(serverPlayer -> true)) {
					ModMessages.sendToPlayer(new TimeChangeS2CPacket(6000), player);
				}
				if (!pPlayer.isCreative()) {
					stack.shrink(1);
				}
			});
		}
		return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
	}
}