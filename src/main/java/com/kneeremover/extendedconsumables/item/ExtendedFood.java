package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.util.ModifierUtils;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ExtendedFood extends Item { // Although this doesn't NEED to be an abstract, it's definitely going to reduce unnecessary size in the item register.
	public ExtendedFood(Properties pProperties, String foodName, boolean onlyOneLevel, List<MobEffect> effects) {
		super(pProperties.tab(ModCreativeModeTabs.FOOD_TAB).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).alwaysEat().build()));
		this.foodName = foodName;
		this.onlyOneLevel = onlyOneLevel;
		this.effects = effects;
		this.moddedEffects = new ArrayList<>();
	}

	public ExtendedFood(Properties pProperties, String foodName, boolean onlyOneLevel, List<MobEffect> effects, List<RegistryObject<MobEffect>> moddedEffects) {
		super(pProperties.tab(ModCreativeModeTabs.FOOD_TAB).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).alwaysEat().build()));
		this.foodName = foodName;
		this.onlyOneLevel = onlyOneLevel;
		this.effects = effects;
		this.moddedEffects = moddedEffects;
	}

	public String foodName;
	public List<MobEffect> effects;
	public List<RegistryObject<MobEffect>> moddedEffects;
	public boolean onlyOneLevel;

	@Override
	public @NotNull SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_EAT;
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
			ModifierUtils.ExtendedModifier modifier = ModifierUtils.getCombinedModifiers(pStack);
			pTooltipComponents.add(new TextComponent(new TranslatableComponent("modifiers.extendedconsumables.strength").getString() + (1 + Math.max(modifier.getAmplifierEffect(), 0))));
			pTooltipComponents.add(new TextComponent(new TranslatableComponent("modifiers.extendedconsumables.duration").getString()
					+ (1200 * (1 + Math.max(modifier.getDurationEffect(), 0))) / 20
					+ new TranslatableComponent("modifiers.extendedconsumables.seconds").getString()));
			pTooltipComponents.add(new TextComponent(new TranslatableComponent("modifiers.extendedconsumables.nourishing").getString() + modifier.getSaturationEffect()));
		} else {
			pTooltipComponents.add(new TranslatableComponent("description.generic.extendedconsumables.shift"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pLivingEntity) {
		if (!pLevel.isClientSide) {
			pLivingEntity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10 * ModifierUtils.getCombinedModifiers(pStack).getSaturationEffect(), 0));


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
		int baseDuration = 1200;
		ModifierUtils.ExtendedModifier mod = ModifierUtils.getCombinedModifiers(pStack);
		int amplifier = mod.getAmplifierEffect();
		double durationMod = 1 + mod.getDurationEffect();
		durationMod = Math.max(0, durationMod);
		amplifier = Math.max(0, amplifier);

		List<MobEffectInstance> toReturn = new ArrayList<>();
		for (RegistryObject<MobEffect> eff : moddedEffects) {
			toReturn.add(new MobEffectInstance(eff.get(), (int) (eff.get().isInstantenous() ? 1 : baseDuration * durationMod), amplifier));
		}
		for (MobEffect eff : effects) {
			toReturn.add(new MobEffectInstance(eff, (int) (eff.isInstantenous() ? 1 : baseDuration * durationMod), amplifier));
		}
		return toReturn;
	}
}