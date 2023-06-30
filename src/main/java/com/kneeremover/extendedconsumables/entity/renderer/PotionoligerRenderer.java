package com.kneeremover.extendedconsumables.entity.renderer;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.custom.Potionoliger;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PotionoligerRenderer extends IllagerRenderer<Potionoliger> {
	private static final ResourceLocation POTIONOLIGER = new ResourceLocation("textures/entity/mob/potionoliger.png");

	public PotionoligerRenderer(EntityRendererProvider.Context p_174354_) {
		super(p_174354_, new IllagerModel<>(p_174354_.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
		this.addLayer(new ItemInHandLayer<>(this));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(Potionoliger pEntity) {
		return new ResourceLocation(ExtendedConsumables.MOD_ID, "textures/entity/mob/potionoliger.png");
	}
}