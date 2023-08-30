package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.custom.projectile.SplashPotion;
import com.kneeremover.extendedconsumables.util.ModifierUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExtendedPotion extends Item {
	public ExtendedPotion(ExtendedPotion.EPProperties properties) {
		super(new Item.Properties().tab(ModCreativeModeTabs.POTION_TAB).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).alwaysEat().build())
				.stacksTo(properties.unstackable ? 1 : 64)); // For beginners reading this code, first dont im bad, second this is the equivalent of if bool than object else object.
		this.potionName = properties.potionName;
		this.baseDuration = properties.baseDuration;
		this.onlyOneLevel = properties.onlyOneLevel;
		this.canSplash = properties.canSplash;
		this.unstackable = properties.unstackable;
		this.noBolt = properties.noBolt;
		this.effect = properties.effect;
	}

	public static class EPProperties {
		String potionName = "good_at_moddev";
		int baseDuration = 1200;
		boolean onlyOneLevel = false;
		boolean canSplash = true;
		boolean unstackable = false;
		boolean noBolt = false;
		RegistryObject<MobEffect> effect = ModEffects.STEP_HEIGHT;

		public ExtendedPotion.EPProperties setPotName(String toName) {
			this.potionName = toName;
			return this;
		}
		public ExtendedPotion.EPProperties setBaseDur(int toDur) {
			this.baseDuration = toDur;
			return this;
		}
		public ExtendedPotion.EPProperties onlyOneLevel() {
			this.onlyOneLevel = true;
			return this;
		}
		public ExtendedPotion.EPProperties noSplash() {
			this.canSplash = false;
			return this;
		}
		public ExtendedPotion.EPProperties unstackable() {
			this.unstackable = true;
			return this;
		}
		public ExtendedPotion.EPProperties noBolt() { // Can't be used on arrows to created tipped bolts
			this.noBolt = true;
			return this;
		}
		public ExtendedPotion.EPProperties setEffect(RegistryObject<MobEffect> toEffect) {
			this.effect = toEffect;
			return this;
		}
	}

	private final String potionName;
	private final RegistryObject<MobEffect> effect;
	private final int baseDuration;

	private final boolean onlyOneLevel;
	private final boolean canSplash;
	private final boolean unstackable;
	private final boolean noBolt;

	@Override
	public @NotNull SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
		return UseAnim.DRINK;
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		if (canSplash && ModifierUtils.getCombinedModifiers(stack).causesSplash()) {
			pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
			if (!pLevel.isClientSide) {
				MobEffectInstance mei = getStackEffect(stack);
				SplashPotion potion = new SplashPotion(pLevel, pPlayer, mei.getEffect(), mei.getDuration(), mei.getAmplifier());
				potion.setItem(stack);
				potion.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
				pLevel.addFreshEntity(potion);
			}

			pPlayer.awardStat(Stats.ITEM_USED.get(this));
			if (!pPlayer.getAbilities().instabuild) {
				stack.shrink(1);
			}
		} else {
			ItemStack itemstack = pPlayer.getItemInHand(pHand);
			if (itemstack.isEdible()) {
				if (pPlayer.canEat(itemstack.getFoodProperties(pPlayer).canAlwaysEat())) {
					pPlayer.startUsingItem(pHand);
					return InteractionResultHolder.consume(itemstack);
				} else {
					return InteractionResultHolder.fail(itemstack);
				}
			} else {
				return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
			}
		}
		return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
	}

	@Override
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TranslatableComponent("description.extendedconsumables." + potionName));
			pTooltipComponents.add(new TextComponent(""));
			if (onlyOneLevel) {
				pTooltipComponents.add(new TranslatableComponent("description.warn.extendedconsumables.only_one_level"));
			}
			if (!canSplash) {
				pTooltipComponents.add(new TranslatableComponent("description.warn.extendedconsumables.cannot_splash"));
			}
			if (unstackable) {
				pTooltipComponents.add(new TranslatableComponent("description.warn.extendedconsumables.unstackable"));
			}
			if (noBolt) {
				pTooltipComponents.add(new TranslatableComponent("description.warn.extendedconsumables.no_bolt"));
			}
			pTooltipComponents.add(new TranslatableComponent("modifiers.extendedconsumables.title"));
			ModifierUtils.ExtendedModifier modifier = ModifierUtils.getCombinedModifiers(pStack);
			pTooltipComponents.add(new TextComponent(new TranslatableComponent("modifiers.extendedconsumables.strength").getString() + (1 + Math.max(modifier.getAmplifierEffect(), 0))));
			pTooltipComponents.add(new TextComponent(new TranslatableComponent("modifiers.extendedconsumables.duration").getString()
					+ (baseDuration * (1 + Math.max(modifier.getDurationEffect(), 0))) / 20
					+ new TranslatableComponent("modifiers.extendedconsumables.seconds").getString()));
			if (modifier.causesSplash()) {
				pTooltipComponents.add(new TranslatableComponent("modifiers.extendedconsumables.splash"));
			}
		} else {
			pTooltipComponents.add(new TranslatableComponent("description.generic.extendedconsumables.shift"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pLivingEntity) {
		if (!pLevel.isClientSide) {
			pLivingEntity.addEffect(getStackEffect(pStack));
			if (pLivingEntity instanceof Player)
				((Player) pLivingEntity).addItem(Items.GLASS_BOTTLE.getDefaultInstance());
		}
		return super.finishUsingItem(pStack, pLevel, pLivingEntity);
	}

	/**
	 * Gets the effect of an ItemStack
	 * @param pStack the ItemStack to check
	 * @return A MobEffectInstance with a duration and amplifier modified based on the stack's modifiers
	 */
	public MobEffectInstance getStackEffect(ItemStack pStack) {
		ModifierUtils.ExtendedModifier mod = ModifierUtils.getCombinedModifiers(pStack);
		double durationMod = 1 + mod.getDurationEffect();
		durationMod = Math.max(durationMod, 0);
		int amplifierEffect = mod.getAmplifierEffect();
		amplifierEffect = Math.max(amplifierEffect, 0);
		return new MobEffectInstance(effect.get(), (int) (baseDuration * durationMod), amplifierEffect);
	}

	public boolean noBolt() {
		return this.noBolt;
	}
}