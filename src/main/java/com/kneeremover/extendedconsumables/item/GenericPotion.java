package com.kneeremover.extendedconsumables.item;

import com.kneeremover.extendedconsumables.entity.custom.SplashPotion;
import com.kneeremover.extendedconsumables.potion.PotionHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class GenericPotion extends Item {
	public GenericPotion(Properties pProperties) {
		super(pProperties.tab(CreativeModeTab.TAB_BREWING).food(new FoodProperties.Builder().saturationMod(0).nutrition(0).build()));
	}

	private String potionDescription = "I don't know why you are seeing this, but you shouldn't be. Yell at Null or something.";
	private MobEffect effect = MobEffects.BLINDNESS;

	public void setPotionDescription (String str) {
		potionDescription = str;
	}
	public void setEffect (MobEffect eff) {
		effect = eff;
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		List<String> modifiers = PotionHelper.getModifiers(stack);
		if (Objects.equals(modifiers.get(0), "minecraft:gunpowder") || Objects.equals(modifiers.get(1), "minecraft:gunpowder")) {
			pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
			if (!pLevel.isClientSide) {
				SplashPotion potion = new SplashPotion(pLevel, pPlayer);
				potion.setMobEffect(getStackEffect(stack));
				potion.setItem(stack);
				potion.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
				pLevel.addFreshEntity(potion);
				pPlayer.addItem(Items.GLASS_BOTTLE.getDefaultInstance());
			}

			pPlayer.awardStat(Stats.ITEM_USED.get(this));
			if (!pPlayer.getAbilities().instabuild) {
				stack.shrink(1);
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TranslatableComponent(this.potionDescription));
			pTooltipComponents.add(new TextComponent(""));
			pTooltipComponents.add(new TranslatableComponent("extendedconsumables.modifiers.title"));
			List<String> strings = PotionHelper.getModifiers(pStack);
			pTooltipComponents.add(new TranslatableComponent(PotionHelper.translateModifier(strings.get(0))));
			pTooltipComponents.add(new TranslatableComponent(PotionHelper.translateModifier(strings.get(1))));
		} else {
			pTooltipComponents.add(new TextComponent("\u00A78<\u00A7aSHIFT\u00A78>"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public boolean isEdible() {
		return true;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
		if (!pLevel.isClientSide) {

			pLivingEntity.addEffect(getStackEffect(pStack));
			if (pLivingEntity instanceof Player)
				((Player) pLivingEntity).addItem(Items.GLASS_BOTTLE.getDefaultInstance());
		}
		return super.finishUsingItem(pStack, pLevel, pLivingEntity);
	}

	public MobEffectInstance getStackEffect(ItemStack pStack) {
		List<String> modifiers = PotionHelper.getModifiers(pStack);
		int amplifier = 0;
		int duration = 1200;
		if (Objects.equals(modifiers.get(0), "minecraft:redstone")) duration += 600;
		if (Objects.equals(modifiers.get(1), "minecraft:redstone")) duration += 600;

		if (Objects.equals(modifiers.get(0), "minecraft:glowstone_dust")) amplifier++;
		if (Objects.equals(modifiers.get(1), "minecraft:glowstone_dust")) amplifier++;
		return new MobEffectInstance(effect, duration, amplifier);
	}
}
