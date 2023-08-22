package com.kneeremover.extendedconsumables.item.custom;

import com.kneeremover.extendedconsumables.item.ExtendedScroll;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;


public class ScrollOfSadekivi extends ExtendedScroll { // The friends we made along the way
	public ScrollOfSadekivi() {
		super(true, "scroll_sadekivi");
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		if (pLevel instanceof ServerLevel slevel) {
			double x = pPlayer.getX();
			double y = pPlayer.getY();
			double z = pPlayer.getZ();
			int range = 2;
			for (BlockPos blockpos : BlockPos.betweenClosed(((int) (x - range)), (int) y, (int) (z - range), (int) (x + range), pLevel.getMaxBuildHeight(), (int) (z + range))) {
				if (!pLevel.getBlockState(blockpos).hasBlockEntity() && pLevel.getBlockState(blockpos).getBlock().defaultDestroyTime() >= 0) {
					pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 18);
				}
				slevel.sendParticles(ParticleTypes.COMPOSTER, blockpos.getX() - 0.5, blockpos.getY() - 0.5, blockpos.getZ() - 0.5, 10, 1, 1, 1, 0.03);
			}
			if (!pPlayer.isCreative()) {
				stack.shrink(1);
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
	}
}