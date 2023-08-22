package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.custom.projectile.TippedBolt;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class TippedBoltItem extends ArrowItem {
	public TippedBoltItem(Properties pProperties) {
		super(pProperties);
	}
	public static HashMap<Integer, MobEffect> definedEffects = new HashMap<>();
	@Override
	public TippedBolt createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
		TippedBolt arrow = new TippedBolt(pLevel, pShooter);
		arrow.setEffectsFromItem(pStack);
		return arrow;
	}

	public boolean overrideTooltip = false;

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		if (!overrideTooltip) {
			if (Screen.hasShiftDown()) {
				pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables.tipped_bolt"));
			} else {
				pTooltipComponents.add(new TranslatableComponent("description.generic.extendedconsumables.shift"));
			}
		}
		for (RegistryObject<MobEffect> effect:ModEffects.MOB_EFFECTS.getEntries()) {
			definedEffects.put(effect.get().getColor(), effect.get());
		}

		CompoundTag tag = pStack.getOrCreateTag();
		int[] effectIDs = tag.getIntArray("extendedconsumables.effectIDs");
		int[] amplifiers = tag.getIntArray("extendedconsumables.amplifiers");
		int[] durations = tag.getIntArray("extendedconsumables.durations");

		int cursor = 0;
		for (int effectID : effectIDs) {
			if (definedEffects.containsKey(effectID) && amplifiers.length > cursor && durations.length > cursor) {
				pTooltipComponents.add(new TextComponent("\u00a77\u00a7o - " + definedEffects.get(effectID).getDisplayName().getString() + " (LVL " + (amplifiers[cursor] + 1) + ") (DUR " + (durations[cursor] / 20) + "s)"));
			} else {
				pTooltipComponents.add(new TextComponent("\u00a7cFATAL ERROR PREVENTED"));
			}
			cursor++;
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}
