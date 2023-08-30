package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.entity.custom.projectile.DullTippedBolt;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DullTippedBoltItem extends TippedBoltItem {
	public DullTippedBoltItem(Properties pProperties) {
		super(pProperties);
		super.overrideTooltip = true;
	}
	@Override
	public DullTippedBolt createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
		DullTippedBolt arrow = new DullTippedBolt(pLevel, pShooter);
		arrow.setEffectsFromItem(pStack);
		return arrow;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables.dull_tipped_bolt"));
		} else {
			pTooltipComponents.add(new TranslatableComponent("description.generic.extendedconsumables.shift"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}
