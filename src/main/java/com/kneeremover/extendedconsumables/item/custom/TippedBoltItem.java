package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.custom.projectile.TippedBolt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class TippedBoltItem extends Item {
	public TippedBoltItem(Properties pProperties) {
		super(pProperties);
	}

	public TippedBolt createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
		TippedBolt arrow = new TippedBolt(pLevel, pShooter);
		arrow.setEffectsFromItem(pStack);
		return arrow;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		HashMap<Integer, MobEffect> definedEffects = new HashMap<>();
		definedEffects.put(ModEffects.LAST_STAND.get().getColor(), ModEffects.LAST_STAND.get());
		definedEffects.put(ModEffects.STEP_HEIGHT.get().getColor(), ModEffects.STEP_HEIGHT.get());
		definedEffects.put(ModEffects.SATURATION_OVERLOAD.get().getColor(), ModEffects.SATURATION_OVERLOAD.get());
		definedEffects.put(ModEffects.RADIANT_FLAMES.get().getColor(), ModEffects.RADIANT_FLAMES.get());
		definedEffects.put(ModEffects.RADIANT_REGEN.get().getColor(), ModEffects.RADIANT_REGEN.get());
		definedEffects.put(ModEffects.RADIANT_SLOWNESS.get().getColor(), ModEffects.RADIANT_SLOWNESS.get());

		CompoundTag tag = pStack.getOrCreateTag();
		int[] effectIDs = tag.getIntArray("extendedconsumables.effectIDs");
		int[] amplifiers = tag.getIntArray("extendedconsumables.amplifiers");
		int[] durations = tag.getIntArray("extendedconsumables.durations");

		int cursor = 0;
		for (int effectID : effectIDs) {
			pTooltipComponents.add(new TextComponent("\u00a77\u00a7o - " + definedEffects.get(effectID).getDisplayName().getString() + " (LVL " + (amplifiers[cursor] + 1) + ") (DUR " + (durations[cursor] / 20) + "s)"));
			cursor++;
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}
