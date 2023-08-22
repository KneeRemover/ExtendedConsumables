package com.kneeremover.extendedconsumables.entity.renderer;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.custom.Potionoliger;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class PotionoligerRenderer extends AnimatedGeoModel<Potionoliger> {

	@Override
	public ResourceLocation getModelLocation(Potionoliger object) {
		return new ResourceLocation(ExtendedConsumables.MOD_ID, "geo/Poitionoliger.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Potionoliger object) {
		return new ResourceLocation(ExtendedConsumables.MOD_ID, "textures/entity/mob/Poitionoliger.png.json");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Potionoliger object) {
		return new ResourceLocation(ExtendedConsumables.MOD_ID, "animations/Poitionoliger.animation.json");
	}


}
