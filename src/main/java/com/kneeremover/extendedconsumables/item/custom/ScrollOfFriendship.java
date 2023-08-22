package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.item.ExtendedScroll;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class ScrollOfFriendship extends ExtendedScroll { // The friends we made along the way
	public ScrollOfFriendship() {
		super(false, "scroll_friendship");
	}

	@Override
	public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, Player pPlayer, @NotNull LivingEntity pInteractionTarget, @NotNull InteractionHand pUsedHand) {
		pPlayer.getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(truces -> {
			if (pInteractionTarget.getType() == EntityType.PLAYER) {
				if (truces.getTruces().contains(pInteractionTarget.getStringUUID())) {
					truces.subTruce(pInteractionTarget.getStringUUID());
				} else {
					truces.addTruce(pInteractionTarget.getStringUUID());
				}
			}
		});
		return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
		if (!pLevel.isClientSide) {
			pPlayer.getCapability(PlayerTrucesProvider.PLAYER_TRUCES).ifPresent(truces -> {
				pPlayer.sendMessage(new TranslatableComponent("extendedconsumables.info.trucelist.truceIntro"), UUID.randomUUID());
				for (String uuid : truces.getTruces()) {
					UUID targetUUID = UUID.fromString(uuid);
					Player player = pLevel.getPlayerByUUID(targetUUID);
					if (player == null) {
						pPlayer.sendMessage(new TranslatableComponent("extendedconsumables.info.trucelist.offline"), UUID.randomUUID());
					} else {
						pPlayer.sendMessage(new TextComponent("\u00a76 - " + player.getName().getString()), UUID.randomUUID());
					}
				}
			});
		}
		return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
	}
}