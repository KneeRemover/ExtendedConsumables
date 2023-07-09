package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.capabilities.PlayerSaturationOverloadProvider;
import com.kneeremover.extendedconsumables.effect.capabilities.PlayerTrucesProvider;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import java.util.UUID;


public class PocketDimensionAnalyzer extends Item {
	public PocketDimensionAnalyzer(Properties properties) {
		super(properties);
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
		if (!pLevel.isClientSide) {
			pPlayer.getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(sat ->
					pPlayer.sendMessage(new TranslatableComponent("extendedconsumables.info.pocket_dimension_analyzer.saturationIntro").append("" + sat.getOverload()), UUID.randomUUID()));
		}
		return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables.pocket_dimension_analyzer"));
		} else {
			pTooltipComponents.add(new TextComponent("\u00A78<\u00A7aSHIFT\u00A78>"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}