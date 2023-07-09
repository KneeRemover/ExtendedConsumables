package com.kneeremover.extendedconsumables.util;

import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.item.custom.ECCrossbow;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {
	public static void addCustomItemProperties() {
		makeCrossbow(ModItems.EC_CROSSBOW.get());
	}
	private static void makeCrossbow(Item item) {
		ItemProperties.register(item, new ResourceLocation("pull"), (stack, level, player, number) -> {
			if (player == null) {
				return 0.0F;
			} else {
				return player.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - player.getUseItemRemainingTicks()) / 20.0F;
			}
		});
		ItemProperties.register(item, new ResourceLocation("pulling"), (stack, level, player, number) -> player != null && player.isUsingItem() && player.getUseItem() == stack ? 1.0F : 0.0F);
		ItemProperties.register(item, new ResourceLocation("firework"), (stack, level, player, number) -> !ECCrossbow.getChargedProjectiles(stack).isEmpty() && ECCrossbow.getChargedProjectiles(stack).get(0).getItem() == Items.FIREWORK_ROCKET ? 1.0F : 0.0F);
		ItemProperties.register(item, new ResourceLocation("tipped"), (stack, level, player, number) -> !ECCrossbow.getChargedProjectiles(stack).isEmpty() && ECCrossbow.getChargedProjectiles(stack).get(0).getItem() == ModItems.TIPPED_BOLT_ITEM.get() ? 1.0F : 0.0F);
		ItemProperties.register(item, new ResourceLocation("charged"), (stack, level, player, number) -> !ECCrossbow.getChargedProjectiles(stack).isEmpty() ? 1.0F : 0.0F);
	}
}
