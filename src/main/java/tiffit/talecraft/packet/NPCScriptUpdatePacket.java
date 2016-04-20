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
import tiffit.talecraft.entity.NPC.EntityNPC;
import tiffit.talecraft.entity.NPC.NPCEditorGui;

public class NPCScriptUpdatePacket implements IMessage {

	String script;
	int id;
	

	public NPCScriptUpdatePacket() {
	}

	public NPCScriptUpdatePacket(int id, String script) {
		this.script = script;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		script = ByteBufUtils.readUTF8String(buf);
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, script);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<NPCScriptUpdatePacket, IMessage> {

		@Override
		public IMessage onMessage(NPCScriptUpdatePacket message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityNPC npc = (EntityNPC) mc.theWorld.getEntityByID(message.id);
			mc.displayGuiScreen(new NPCEditorGui(npc.getNPCData(), npc.getUniqueID(), message.script));
			return null;
		}
	}
}
