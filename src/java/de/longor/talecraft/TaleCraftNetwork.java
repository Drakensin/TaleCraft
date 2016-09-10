package de.longor.talecraft;

import static de.longor.talecraft.TaleCraft.network;
import static net.minecraftforge.fml.relauncher.Side.CLIENT;
import static net.minecraftforge.fml.relauncher.Side.SERVER;

import de.longor.talecraft.network.PlayerNBTDataMergePacket;
import de.longor.talecraft.network.StringNBTCommandPacket;
import de.longor.talecraft.network.StringNBTCommandPacketClient;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;
import tiffit.talecraft.packet.DecoratorGuiPacket;
import tiffit.talecraft.packet.DecoratorPacket;
import tiffit.talecraft.packet.DoorPacket;
import tiffit.talecraft.packet.FadePacket;
import tiffit.talecraft.packet.ForceF1Packet;
import tiffit.talecraft.packet.GunReloadPacket;
import tiffit.talecraft.packet.NPCDataPacket;
import tiffit.talecraft.packet.NPCDataUpdatePacket;
import tiffit.talecraft.packet.NPCOpenPacket;
import tiffit.talecraft.packet.SoundsPacket;
import tiffit.talecraft.packet.VoxelatorGuiPacket;
import tiffit.talecraft.packet.VoxelatorPacket;
import tiffit.talecraft.packet.WorkbenchCraftingPacket;

public class TaleCraftNetwork {

	private static int discriminator = 0;
	
	public static void preInit(){
		network = NetworkRegistry.INSTANCE.newSimpleChannel("TalecraftNet");

		register(StringNBTCommandPacket.Handler.class, StringNBTCommandPacket.class, SERVER);
		register(PlayerNBTDataMergePacket.Handler.class, PlayerNBTDataMergePacket.class, CLIENT);
		register(VoxelatorGuiPacket.Handler.class, VoxelatorGuiPacket.class, CLIENT);
		register(DoorPacket.Handler.class, DoorPacket.class, CLIENT);
		register(VoxelatorPacket.Handler.class, VoxelatorPacket.class, SERVER);
		register(NPCDataPacket.Handler.class, NPCDataPacket.class, SERVER);
		register(NPCOpenPacket.Handler.class, NPCOpenPacket.class, CLIENT);
		register(NPCDataUpdatePacket.Handler.class, NPCDataUpdatePacket.class, CLIENT);
		register(SoundsPacket.Handler.class, SoundsPacket.class, CLIENT);
		register(FadePacket.Handler.class, FadePacket.class, CLIENT);
		register(GunReloadPacket.Handler.class, GunReloadPacket.class, SERVER);
		register(WorkbenchCraftingPacket.Handler.class, WorkbenchCraftingPacket.class, SERVER);
		register(StringNBTCommandPacketClient.Handler.class, StringNBTCommandPacketClient.class, CLIENT);
		register(ForceF1Packet.Handler.class, ForceF1Packet.class, CLIENT);
		register(DecoratorPacket.Handler.class, DecoratorPacket.class, SERVER);
		register(DecoratorGuiPacket.Handler.class, DecoratorGuiPacket.class, CLIENT);
	}
	
	private static <REQ extends IMessage, REPLY extends IMessage> void register(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> packet, Side side){
		if(FMLCommonHandler.instance().getSide() == Side.SERVER && side == Side.CLIENT) return;
		network.registerMessage(handler, packet, discriminator, side);
		discriminator++;
	}
	
}
