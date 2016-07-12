package tiffit.talecraft.packet;

import java.util.UUID;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.TaleCraftItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;
import tiffit.talecraft.client.gui.npc.NPCEditorGui;
import tiffit.talecraft.entity.NPC.EntityNPC;

public class NPCOpenPacket implements IMessage {

	String interact;
	String update;
	String death;
	int id;
	

	public NPCOpenPacket() {
	}

	public NPCOpenPacket(int id, String interact, String update, String death) {
		this.interact = interact;
		this.update = update;
		this.death = death;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		interact = ByteBufUtils.readUTF8String(buf);
		update = ByteBufUtils.readUTF8String(buf);
		death = ByteBufUtils.readUTF8String(buf);
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, interact);
		ByteBufUtils.writeUTF8String(buf, update);
		ByteBufUtils.writeUTF8String(buf, death);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<NPCOpenPacket, IMessage> {

		@Override
		public IMessage onMessage(NPCOpenPacket message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityNPC npc = (EntityNPC) mc.theWorld.getEntityByID(message.id);
			mc.displayGuiScreen(new NPCEditorGui(npc.getNPCData(), npc.getUniqueID(), message.interact, message.update, message.death));
			return null;
		}
	}
}
