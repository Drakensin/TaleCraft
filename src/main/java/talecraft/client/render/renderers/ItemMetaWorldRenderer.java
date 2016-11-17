package talecraft.client.render.renderers;

import java.util.HashMap;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import talecraft.client.render.metaworld.IMetadataRender;
import talecraft.proxy.ClientProxy;

public class ItemMetaWorldRenderer {
	
	public static HashMap<Item, IMetadataRender> ITEM_RENDERS = new HashMap<Item, IMetadataRender>();
	
	// CLIENT
	public static ClientProxy clientProxy;
	public static Tessellator tessellator;
	public static VertexBuffer vertexbuffer;
	public static double partialTicks;
	public static float partialTicksF;
	// CLIENT.PLAYER
	public static BlockPos playerPosition;
	public static EntityPlayerSP player;
	public static WorldClient world;

	// RENDER
	public static void render(Item item, ItemStack stack) {

		if(ITEM_RENDERS.containsKey(item)){
			IMetadataRender renderer = ITEM_RENDERS.get(item);
			renderer.render(item, stack, tessellator, vertexbuffer, partialTicks, playerPosition, player, world);
		}
//		if(itemType instanceof PasteItem) {
//			renderPasteItem(itemStack);
//		}

	}

//	@Deprecated
//	private static void renderPasteItem(ItemStack itemStack) {
//		ClipboardItem clip = TaleCraft.asClient().getClipboard();
//
//		if(clip == null)
//			return;
//
//		float lenMul = ClientProxy.settings.getInteger("item.paste.reach");
//		Vec3d plantPos = player.getLook(partialTicksF);
//		plantPos = new Vec3d(
//				plantPos.xCoord*lenMul,
//				plantPos.yCoord*lenMul,
//				plantPos.zCoord*lenMul
//				).add(player.getPositionEyes(partialTicksF));
//
//		float dimX = 0;
//		float dimY = 0;
//		float dimZ = 0;
//
//		NBTTagCompound blocks = NBTHelper.getOrNull(clip.getData(), ClipboardTagNames.$REGION);
//		NBTTagCompound entity = NBTHelper.getOrNull(clip.getData(), ClipboardTagNames.$ENTITY);
//
//		if(clip.getData().hasKey(ClipboardTagNames.$OFFSET, clip.getData().getId())) {
//			NBTTagCompound offset = clip.getData().getCompoundTag(ClipboardTagNames.$OFFSET);
//			plantPos = new Vec3d(
//					plantPos.xCoord + offset.getFloat("x"),
//					plantPos.yCoord + offset.getFloat("y"),
//					plantPos.zCoord + offset.getFloat("z")
//					);
//		}
//
//		float snap = ClientProxy.settings.getInteger("item.paste.snap");
//		if(snap > 1) {
//			plantPos = new Vec3d(
//					Math.floor(plantPos.xCoord / snap) * snap,
//					Math.floor(plantPos.yCoord / snap) * snap,
//					Math.floor(plantPos.zCoord / snap) * snap
//					);
//		}
//
//		float color = 0;
//
//		if(blocks != null) {
//			color = -2;
//
//			dimX = blocks.getInteger(ClipboardTagNames.$REGION_WIDTH);
//			dimY = blocks.getInteger(ClipboardTagNames.$REGION_HEIGHT);
//			dimZ = blocks.getInteger(ClipboardTagNames.$REGION_LENGTH);
//
//			plantPos = new Vec3d(
//					Math.floor(plantPos.xCoord),
//					Math.floor(plantPos.yCoord),
//					Math.floor(plantPos.zCoord)
//					);
//		}
//
//		if(entity != null) {
//			color = -3;
//
//			float width = entity.getFloat("tc_width");
//			float height = entity.getFloat("tc_height");
//
//			dimX = width;
//			dimY = height;
//			dimZ = width;
//
//			float shift = 0.5f;
//			plantPos = plantPos.subtract(width/2, 0, width/2);
//		}
//
//		float minX = (float) plantPos.xCoord;
//		float minY = (float) plantPos.yCoord;
//		float minZ = (float) plantPos.zCoord;
//		float maxX = minX + dimX;
//		float maxY = minY + dimY;
//		float maxZ = minZ + dimZ;
//
//		float error = 1f / 16f;
//		minX -= error;
//		minY -= error;
//		minZ -= error;
//		maxX += error;
//		maxY += error;
//		maxZ += error;
//
//		ClientProxy.mc.renderEngine.bindTexture(ClientResources.textureSelectionBoxFF);
//		// BoxRenderer.renderBox(tessellator, worldrenderer, minX, minY, minZ, maxX, maxY, maxZ, 0, 1, 0, 1);
//		BoxRenderer.renderSelectionBox(tessellator, vertexbuffer, minX, minY, minZ, maxX, maxY, maxZ, color);
//
//		if(snap > 1) {
//			final int s = (int) snap;
//			final int r = 1 * s;
//			final float bsi = 0.5f - 0.05f;
//			final float bsa = 0.5f + 0.05f;
//
//			int midX = (int) Math.floor(minX);
//			int midY = (int) Math.floor(minY);
//			int midZ = (int) Math.floor(minZ);
//
//			int startX = midX - r;
//			int startY = midY - r;
//			int startZ = midZ - r;
//
//			int endX = midX + r + 1;
//			int endY = midY + r + 1;
//			int endZ = midZ + r + 1;
//
//			ClientProxy.mc.renderEngine.bindTexture(ClientResources.texColorWhite);
//
//			vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
//			vertexbuffer.color(1, 1, 1, 1);
//			//vertexbuffer.setBrightness(0xEE); //TODO FIX
//
//			for(int y = startY; y <= endY; y++) {
//				for(int z = startZ; z <= endZ; z++) {
//					for(int x = startX; x <= endX; x++) {
//						if(x%snap==0&&y%snap==0&&z%snap==0)
//							BoxRenderer.renderBoxEmb(tessellator, vertexbuffer, x+bsi, y+bsi, z+bsi, x+bsa, y+bsa, z+bsa);
//					}
//				}
//			}
//			tessellator.draw();
//		}
//	}

	
}
