package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.item.ExtendedScroll;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class ScrollOfDoubleJump extends ExtendedScroll { // The friends we made along the way
	public ScrollOfDoubleJump() {
		super(true, "scroll_double_jump");
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		if (!pLevel.isClientSide) {
			pPlayer.setDeltaMovement(new Vec3(0, 1, 0).add(Vec3.directionFromRotation(pPlayer.getRotationVector()).normalize().multiply(0.3, 0.3, 0.3)));
			pPlayer.hurtMarked = true;
			pPlayer.resetFallDistance();
			if (!pPlayer.isCreative()) {
				stack.shrink(1);
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
	}
}