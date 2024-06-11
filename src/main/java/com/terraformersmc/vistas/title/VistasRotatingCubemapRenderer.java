package com.terraformersmc.vistas.title;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.util.Identifier;

public class VistasRotatingCubemapRenderer extends RotatingCubeMapRenderer {
	private final MinecraftClient client;

	public VistasRotatingCubemapRenderer(CubeMapRenderer defaultRenderer) {
		super(defaultRenderer);

		this.client = MinecraftClient.getInstance();
	}

	@Override
	public void render(DrawContext context, int width, int height, float alpha, float tickDelta) {
		VistasCubemapRenderer.time += tickDelta;

		VistasTitle.CURRENT.getValue().getCubemaps().forEach((cubemap) -> {
			VistasCubemapRenderer panoramaRenderer = new VistasCubemapRenderer(cubemap);
			Identifier overlayId = panoramaRenderer.getCubemap().getCubemapId().withSuffixedPath("_overlay.png");

			panoramaRenderer.draw(this.client, alpha);

			if (this.client.getResourceManager().getResource(overlayId).isPresent()) {
				RenderSystem.enableBlend();
				context.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
				context.drawTexture(overlayId, 0, 0, width, height, 0.0f, 0.0f, 16, 128, 16, 128);
				context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.disableBlend();
			}
		});
	}
}
