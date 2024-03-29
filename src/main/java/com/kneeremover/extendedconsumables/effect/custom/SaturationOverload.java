package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.capabilities.PlayerSaturationOverloadProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import org.jetbrains.annotations.NotNull;

public class SaturationOverload extends MobEffect {
	public SaturationOverload(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}

	private boolean lastHeldFood = false;

	@Override
	public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
		if (pLivingEntity instanceof Player player) {
			player.getCapability(PlayerSaturationOverloadProvider.PLAYER_SATURATION_OVERLOAD).ifPresent(sat -> {
				ExtendedConsumables.LOGGER.info("Overload: " + sat.getOverload());
				FoodData foodData = player.getFoodData();
				sat.subOverload(20 - foodData.getSaturationLevel());
				foodData.setSaturation(20);
				if (sat.getOverload() < 0) {
					foodData.setSaturation(20 + sat.getOverload());
					sat.addOverload(sat.getOverload() * -1);
				}

				FoodProperties heldFoodProperties = player.getMainHandItem().getFoodProperties(player);
				if (foodData.getFoodLevel() == 20 && heldFoodProperties != null ) {
					// Occurs when you are at max hunger and holding food
					foodData.setFoodLevel(19);
					this.lastHeldFood = true;
				} else if (this.lastHeldFood && heldFoodProperties == null) {
					// Occurs when you have held food but are no longer holding food
					foodData.setFoodLevel(20);
					this.lastHeldFood = false;
				} else if (foodData.getFoodLevel() <= 18 && this.lastHeldFood) {
					// Occurs when your hunger drops while holding food
					foodData.setFoodLevel(19);
					this.lastHeldFood = false;
				}
			});
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return pDuration % 5 == 0;
	}
}
