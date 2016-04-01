package tiffit.talecraft.packet;

import java.util.UUID;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.TaleCraftItems;
import io.netty.buffer.ByteBuf;
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

public class VoxelatorPacket implements IMessage {

	NBTTagCompound data;
	UUID uuid;
	int slot;

	public VoxelatorPacket() {
	}

	public VoxelatorPacket(UUID uuid, int slot, NBTTagCompound tag) {
		data = tag;
		this.uuid = uuid;
		this.slot = slot;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
		uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		slot = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
		ByteBufUtils.writeUTF8String(buf, uuid.toString());
		buf.writeInt(slot);
	}

	public static class Handler implements IMessageHandler<VoxelatorPacket, IMessage> {

		@Override
		public IMessage onMessage(VoxelatorPacket message, MessageContext ctx) {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(message.uuid);
			
			ItemStack item = player.inventory.getCurrentItem();
			if(item.getItem() == TaleCraftItems.voxelbrush){
				item.getTagCompound().setTag("brush", message.data);
			}else TaleCraft.logger.error("Currently Held Item Is Not A VoxelBrush");
			return null;
		}
	}
}
