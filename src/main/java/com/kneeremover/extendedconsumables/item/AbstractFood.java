package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.ModifierHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFood extends Item {
	public AbstractFood(Properties pProperties) {
		super(pProperties.tab(ModCreativeModeTabs.FOOD_TAB).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).alwaysEat().build()));
	}

	public String foodName = "Placeholder";
	public List<MobEffect> effect = new ArrayList<>();

	public boolean onlyOneLevel = false;

	@Override
	public @NotNull SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_EAT;
	}

	/**
	 * Called when the potion effect is the default
	 * Override with {@code super.effect = new List<MobEffect>};
	 * This allows setting the effect properly.
	 */
	public void fixEffect() {
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
		return UseAnim.EAT;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables." + foodName));
			pTooltipComponents.add(new TextComponent(""));
			if (onlyOneLevel) {
				pTooltipComponents.add(new TranslatableComponent("description.warn.extendedconsumables.only_one_level"));
			}
			pTooltipComponents.add(new TranslatableComponent("modifiers.extendedconsumables.title"));
			List<String> strings = ModifierHandler.getModifiers(pStack);
			pTooltipComponents.add(new TranslatableComponent(ModifierHandler.translateModifier(strings.get(0))));
			pTooltipComponents.add(new TranslatableComponent(ModifierHandler.translateModifier(strings.get(1))));
		} else {
			pTooltipComponents.add(new TextComponent("\u00A78<\u00A7aSHIFT\u00A78>"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pLivingEntity) {
		if (!pLevel.isClientSide) {
			int nourishing = 0;
			if (Objects.equals(ModifierHandler.getModifiers(pStack).get(0), "minecraft:cooked_beef")) nourishing++;
			if (Objects.equals(ModifierHandler.getModifiers(pStack).get(1), "minecraft:cooked_beef")) nourishing++;
			pLivingEntity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10 * nourishing, 0));


			List<MobEffectInstance> stackEffect = getStackEffect(pStack);
			for (MobEffectInstance mei : stackEffect) {
				pLivingEntity.addEffect(mei);
			}
		}
		return super.finishUsingItem(pStack, pLevel, pLivingEntity);
	}

	/**
	 * Gets the effect of an ItemStack
	 *
	 * @param pStack the ItemStack to check
	 * @return A MobEffectInstance with a duration and amplifier modified based on the stack's modifiers
	 */
	public List<MobEffectInstance> getStackEffect(ItemStack pStack) {
		List<String> modifiers = ModifierHandler.getModifiers(pStack);
		int amplifier = 0;
		int duration = 1200;
		if (Objects.equals(modifiers.get(0), "minecraft:redstone")) duration += 600;
		if (Objects.equals(modifiers.get(1), "minecraft:redstone")) duration += 600;

		if (Objects.equals(modifiers.get(0), "minecraft:glowstone_dust")) amplifier++;
		if (Objects.equals(modifiers.get(1), "minecraft:glowstone_dust")) amplifier++;
		if (effect.size() == 0) fixEffect();
		List<MobEffectInstance> toReturn = new ArrayList<>();
		for (MobEffect eff : effect) {
			toReturn.add(new MobEffectInstance(eff, duration, amplifier));
		}
		return toReturn;
	}
}