package com.kneeremover.extendedconsumables.effect.custom;

import com.kneeremover.extendedconsumables.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChaoticTeleportation extends MobEffect {
	public ChaoticTeleportation(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}
	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		if (teleport(pLivingEntity)) {
			pLivingEntity.removeEffect(ModEffects.CHAOTIC_TELEPORTATION.get());
		}
		super.applyEffectTick(pLivingEntity, pAmplifier);
	}
	// All of this is stolen from the enderman lol
	public static boolean teleport(LivingEntity pLivingEntity) {
		if (!pLivingEntity.level.isClientSide() && pLivingEntity.isAlive()) {
			double d0 = pLivingEntity.getX() + (pLivingEntity.getRandom().nextDouble() - 0.5D) * 64.0D;
			double d1 = pLivingEntity.getY() + (double)(pLivingEntity.getRandom().nextInt(64) - 32);
			double d2 = pLivingEntity.getZ() + (pLivingEntity.getRandom().nextDouble() - 0.5D) * 64.0D;
			return teleport(pLivingEntity, d0, d1, d2);
		} else {
			return false;
		}
	}

	public static boolean teleport(LivingEntity pLivingEntity, double pX, double pY, double pZ) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

		while(blockpos$mutableblockpos.getY() > pLivingEntity.level.getMinBuildHeight() && !pLivingEntity.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		BlockState blockstate = pLivingEntity.level.getBlockState(blockpos$mutableblockpos);
		boolean canStandOn = blockstate.getMaterial().blocksMotion();
		boolean isLava = blockstate.getFluidState().is(FluidTags.LAVA);
		if (canStandOn && !isLava) {
			boolean canTeleport = pLivingEntity.randomTeleport(pX, pY, pZ, true);
			if (canTeleport && !pLivingEntity.isSilent()) {
				pLivingEntity.level.playSound(null, pLivingEntity.xo, pLivingEntity.yo, pLivingEntity.zo, SoundEvents.ENDERMAN_TELEPORT, pLivingEntity.getSoundSource(), 1.0F, 1.0F);
				pLivingEntity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}

			return canTeleport;
		} else {
			return false;
		}
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}
}
