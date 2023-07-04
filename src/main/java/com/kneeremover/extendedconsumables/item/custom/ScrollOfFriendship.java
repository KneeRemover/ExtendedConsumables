package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.ModifierHandler;
import com.kneeremover.extendedconsumables.effect.capabilities.PlayerTrucesProvider;
import com.kneeremover.extendedconsumables.entity.custom.projectile.SplashPotion;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ScrollOfFriendship extends Item { // The friends we made along the way
	public ScrollOfFriendship(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
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
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
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

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables.scroll_friendship"));
		} else {
			pTooltipComponents.add(new TextComponent("\u00A78<\u00A7aSHIFT\u00A78>"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}