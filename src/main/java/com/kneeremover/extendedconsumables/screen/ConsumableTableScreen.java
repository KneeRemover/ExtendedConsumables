package com.kneeremover.extendedconsumables.screen;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ConsumableTableScreen extends AbstractContainerScreen<ConsumableTableMenu> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ExtendedConsumables.MOD_ID, "textures/gui/consumable_table_gui.png");

	public ConsumableTableScreen(ConsumableTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@Override
	protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;

		this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
		int fuel = menu.getScaledFuel();
		blit(pPoseStack, x + 132, y + 27 + 14 - fuel, 176, 15 + 14 - fuel, 14, fuel);

		if (menu.isCrafting()) {
			blit(pPoseStack, x + 84, y + 61, 176, 0, menu.getScaledProgress(), 15);
		}
	}

	@Override
	public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
		renderBackground(pPoseStack);
		super.render(pPoseStack, mouseX, mouseY, delta);
		renderTooltip(pPoseStack, mouseX, mouseY);
	}
}
