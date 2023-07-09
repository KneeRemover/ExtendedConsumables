package com.kneeremover.extendedconsumables.entity.renderer;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.custom.projectile.TippedBolt;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TippedBoltRenderer extends ArrowRenderer<TippedBolt> {
	public TippedBoltRenderer(EntityRendererProvider.Context pContext) {
		super(pContext);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public @NotNull ResourceLocation getTextureLocation(@NotNull TippedBolt pEntity) {

		return new ResourceLocation(ExtendedConsumables.MOD_ID, "textures/entity/projectile/arrowtexture.png");
	}
}