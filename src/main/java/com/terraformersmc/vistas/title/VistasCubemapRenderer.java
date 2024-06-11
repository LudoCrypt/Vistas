package com.terraformersmc.vistas.title;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import com.terraformersmc.vistas.panorama.Cubemap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public class VistasCubemapRenderer {
	protected static double time = 0.0D;

	private static final int FACES_COUNT = 6;
	private final Identifier[] faces = new Identifier[FACES_COUNT];

	private final Cubemap cubemap;

	public VistasCubemapRenderer(Cubemap cubemap) {
		Identifier faces = cubemap.getCubemapId();
		for (int face = 0; face < FACES_COUNT; ++face) {
			this.faces[face] = faces.withSuffixedPath("_" + face + ".png");
		}

		this.cubemap = cubemap;
	}

	public void draw(MinecraftClient client, float alpha) {
		Tessellator tessellator = Tessellator.getInstance();
		Matrix4f matrix4f = new Matrix4f().setPerspective((float) Math.toRadians(this.cubemap.getVisualControl().getFov()), (float) client.getWindow().getFramebufferWidth() / (float) client.getWindow().getFramebufferHeight(), 0.05F, 100.0F);
		RenderSystem.backupProjectionMatrix();
		RenderSystem.setProjectionMatrix(matrix4f, VertexSorter.BY_DISTANCE);
		Matrix4fStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.pushMatrix();
		matrixStack.rotationX((float) Math.PI);
		RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
		RenderSystem.enableBlend();
		RenderSystem.disableCull();
		RenderSystem.depthMask(false);

		int r = Math.round((float) this.cubemap.getVisualControl().getColorR());
		int g = Math.round((float) this.cubemap.getVisualControl().getColorG());
		int b = Math.round((float) this.cubemap.getVisualControl().getColorB());
		int a = Math.round((float) this.cubemap.getVisualControl().getColorA() * alpha);

		float w = (float) this.cubemap.getVisualControl().getWidth() / 2.0f;
		float h = (float) this.cubemap.getVisualControl().getHeight() / 2.0f;
		float d = (float) this.cubemap.getVisualControl().getDepth() / 2.0f;

		matrixStack.pushMatrix();
		matrixStack.translate((float) this.cubemap.getVisualControl().getAddedX(), (float) this.cubemap.getVisualControl().getAddedY(), (float) this.cubemap.getVisualControl().getAddedZ());
		matrixStack.rotate(RotationAxis.POSITIVE_X.rotationDegrees((float) this.cubemap.getRotationControl().getPitch(cubemap.getRotationControl().isFrozen() ? 0.0D : time)));
		matrixStack.rotate(RotationAxis.POSITIVE_Y.rotationDegrees((float) this.cubemap.getRotationControl().getYaw(cubemap.getRotationControl().isFrozen() ? 0.0D : time)));
		matrixStack.rotate(RotationAxis.POSITIVE_Z.rotationDegrees((float) this.cubemap.getRotationControl().getRoll(cubemap.getRotationControl().isFrozen() ? 0.0D : time)));
		RenderSystem.applyModelViewMatrix();

		for (int n = 0; n < 6; ++n) {
			RenderSystem.setShaderTexture(0, this.faces[n]);
			BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

			if (n == 0) {
				bufferBuilder.vertex(-w, -h, d).texture(0.0F, 0.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, h, d).texture(0.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, h, d).texture(1.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, -h, d).texture(1.0F, 0.0F).color(r, g, b, a);
			}

			if (n == 1) {
				bufferBuilder.vertex(w, -h, d).texture(0.0F, 0.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, h, d).texture(0.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, h, -d).texture(1.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, -h, -d).texture(1.0F, 0.0F).color(r, g, b, a);
			}

			if (n == 2) {
				bufferBuilder.vertex(w, -h, -d).texture(0.0F, 0.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, h, -d).texture(0.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, h, -d).texture(1.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, -h, -d).texture(1.0F, 0.0F).color(r, g, b, a);
			}

			if (n == 3) {
				bufferBuilder.vertex(-w, -h, -d).texture(0.0F, 0.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, h, -d).texture(0.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, h, d).texture(1.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, -h, d).texture(1.0F, 0.0F).color(r, g, b, a);
			}

			if (n == 4) {
				bufferBuilder.vertex(-w, -h, -d).texture(0.0F, 0.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, -h, d).texture(0.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, -h, d).texture(1.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, -h, -d).texture(1.0F, 0.0F).color(r, g, b, a);
			}

			if (n == 5) {
				bufferBuilder.vertex(-w, h, d).texture(0.0F, 0.0F).color(r, g, b, a);
				bufferBuilder.vertex(-w, h, -d).texture(0.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, h, -d).texture(1.0F, 1.0F).color(r, g, b, a);
				bufferBuilder.vertex(w, h, d).texture(1.0F, 0.0F).color(r, g, b, a);
			}

			BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
		}

		matrixStack.popMatrix();
		RenderSystem.applyModelViewMatrix();

		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.restoreProjectionMatrix();
		matrixStack.popMatrix();
		RenderSystem.applyModelViewMatrix();
		RenderSystem.depthMask(true);
		RenderSystem.enableCull();
		RenderSystem.enableDepthTest();
	}

	public Cubemap getCubemap() {
		return cubemap;
	}
}
