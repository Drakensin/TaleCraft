package de.longor.talecraft.client.render.renderers;

import org.lwjgl.opengl.GL11;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.client.ClientRenderer.VisualMode;
import de.longor.talecraft.client.render.RenderModeHelper;
import de.longor.talecraft.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.IRenderHandler;

public class CustomSkyRenderer extends IRenderHandler {
	public static final CustomSkyRenderer instance = new CustomSkyRenderer();
	private boolean useDebugSky = false;

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		VisualMode visualizationMode = ((ClientProxy)TaleCraft.proxy).getRenderer().getVisualizationMode();

		if(useDebugSky) {
			renderDebugSky(partialTicks, world, mc);
			if(visualizationMode != VisualMode.Default) {
				RenderModeHelper.ENABLE(visualizationMode);
			}
			return;
		}

		if(visualizationMode != VisualMode.Default) {
			RenderModeHelper.ENABLE(visualizationMode);
		}
	}

	private void renderDebugSky(float partialTicks, WorldClient world, Minecraft mc) {
		GlStateManager.pushAttrib();
		GlStateManager.disableCull();
		GlStateManager.disableDepth();
		GlStateManager.disableFog();
		GlStateManager.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

		final float B = 8;

		Tessellator tess = Tessellator.getInstance();
		VertexBuffer ren = tess.getBuffer();

		ren.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		ren.setTranslation(0, 1, 0);
		ren.color(0, 0, 0, 255);

		// TOP
		ren.pos(+B, +B, -B);
		ren.pos(+B, +B, +B);
		ren.pos(-B, +B, +B);
		ren.pos(-B, +B, -B);
		// BOTTOM
		ren.pos(+B, -B, -B);
		ren.pos(+B, -B, +B);
		ren.pos(-B, -B, +B);
		ren.pos(-B, -B, -B);
		// ??? x
		ren.pos(-B, +B, -B);
		ren.pos(-B, +B, +B);
		ren.pos(-B, -B, +B);
		ren.pos(-B, -B, -B);
		// ??? x
		ren.pos(+B, +B, +B);
		ren.pos(+B, +B, -B);
		ren.pos(+B, -B, -B);
		ren.pos(+B, -B, +B);
		// ??? z
		ren.pos(+B, +B, -B);
		ren.pos(-B, +B, -B);
		ren.pos(-B, -B, -B);
		ren.pos(+B, -B, -B);
		// ??? z
		ren.pos(-B, +B, +B);
		ren.pos(+B, +B, +B);
		ren.pos(+B, -B, +B);
		ren.pos(-B, -B, +B);
		// end
		tess.draw();
		ren.setTranslation(0, 0, 0);

		GlStateManager.enableCull();
		GlStateManager.enableDepth();
		GlStateManager.popAttrib();
	}

	public void setDebugSky(boolean b) {
		useDebugSky = b;
	}

}
