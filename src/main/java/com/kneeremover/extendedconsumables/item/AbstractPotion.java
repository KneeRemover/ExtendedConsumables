package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.ModifierHandler;
import com.kneeremover.extendedconsumables.entity.custom.projectile.SplashPotion;
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
import java.util.Objects;

public abstract class AbstractPotion extends Item {
	public AbstractPotion(Properties pProperties, String potionName, RegistryObject<MobEffect> effect, int baseDuration, boolean onlyOneLevel, boolean canSplash) {
		super(pProperties.tab(ModCreativeModeTabs.POTION_TAB).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).alwaysEat().build()));
		this.potionName = potionName;
		this.baseDuration = baseDuration;
		this.onlyOneLevel = onlyOneLevel;
		this.canSplash = canSplash;
		this.effect = effect;
	}

	private final String potionName;
	private final RegistryObject<MobEffect> effect;
	private final int baseDuration;

	private final boolean onlyOneLevel;
	private final boolean canSplash;

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
		List<String> modifiers = ModifierHandler.getModifiers(stack);
		if (canSplash && (Objects.equals(modifiers.get(0), "minecraft:gunpowder") || Objects.equals(modifiers.get(1), "minecraft:gunpowder"))) {
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
		List<String> modifiers = ModifierHandler.getModifiers(pStack);
		int amplifier = 0;
		int durationMods = 0;
		int duration = baseDuration;
		if (Objects.equals(modifiers.get(0), "minecraft:redstone")) durationMods++;
		if (Objects.equals(modifiers.get(1), "minecraft:redstone")) durationMods++;

		if (durationMods != 0) duration = (int) Math.round(baseDuration * (1.5 * durationMods));
		if (Objects.equals(modifiers.get(0), "minecraft:glowstone_dust")) amplifier++;
		if (Objects.equals(modifiers.get(1), "minecraft:glowstone_dust")) amplifier++;
		return new MobEffectInstance(effect.get(), duration, amplifier);
	}
}