package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.item.ExtendedScroll;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class ScrollOfThunder extends ExtendedScroll { // The friends we made along the way
	public ScrollOfThunder() {
		super(true, "scroll_thunder");
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		if (pLevel instanceof ServerLevel slevel) {
			slevel.setWeatherParameters(0, 20 * 60 * 20, true, true);
			if (!pPlayer.isCreative()) {
				stack.shrink(1);
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
	}
}