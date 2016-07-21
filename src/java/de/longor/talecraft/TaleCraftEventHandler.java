package de.longor.talecraft;

import de.longor.talecraft.network.StringNBTCommandPacket;
import de.longor.talecraft.server.ServerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tiffit.talecraft.blocks.world.WorkbenchBlock;
import tiffit.talecraft.client.gui.npc.GuiNPCMerchant;
import tiffit.talecraft.entity.NPC.NPCShop;
import tiffit.talecraft.util.CustomWorldData;
import tiffit.talecraft.util.WorkbenchManager;
import tiffit.talecraft.util.WorldFileDataHelper;

public class TaleCraftEventHandler {
	public TaleCraftEventHandler() {}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void keyEvent(KeyInputEvent event) {
		if(FMLCommonHandler.instance().getSide().isClient()) {
			if(TaleCraft.proxy instanceof de.longor.talecraft.proxy.ClientProxy) {
				TaleCraft.proxy.asClient().getKeyboardHandler().on_key(event);
			}
		}
	}

	@SubscribeEvent
	public void tick(TickEvent event) {
		TaleCraft.proxy.tick(event);
	}

	@SubscribeEvent
	public void tickWorld(WorldTickEvent event) {
		TaleCraft.proxy.tickWorld(event);
	}

	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event) {
		if(event.player instanceof EntityPlayerMP) {
			ServerHandler.getServerMirror(null).playerList().playerJoin((EntityPlayerMP) event.player);
			TaleCraft.network.sendTo(new StringNBTCommandPacket("client.network.join"), (EntityPlayerMP) event.player);
			GuiMerchant e;
		}
		
	}
	
	//CAN BE USED TO FIND CERTAIN EVENTS
//	@SubscribeEvent
//	public void event(Event event){
//		if(event instanceof BiomeEvent) return;
//		if(event instanceof RenderGameOverlayEvent) return;
//		if(event instanceof GuiScreenEvent) return;
//		if(event instanceof EntityEvent.EnteringChunk) return;
//		if(event instanceof LivingEvent.LivingUpdateEvent) return;
//		if(event instanceof EntityViewRenderEvent) return;
//		if(event instanceof RenderLivingEvent) return;
//		if(event instanceof DrawBlockHighlightEvent) return;
//		if(event instanceof RenderWorldLastEvent) return;
//		if(event instanceof RenderHandEvent) return;
//		if(event instanceof TickEvent) return;
//		if(event instanceof InputEvent) return;
//		if(event instanceof MouseEvent) return;
//		if(event instanceof ChunkDataEvent) return;
//		if(event instanceof WorldEvent) return;
//		if(event instanceof FOVUpdateEvent) return;
//		if(event instanceof PlaySoundEvent) return;
//		if(event instanceof PlaySoundSourceEvent) return;
//		if(event instanceof BlockEvent) return;
//		if(event instanceof AttachCapabilitiesEvent) return;
//		if(event instanceof GuiOpenEvent) return;
//		System.out.println(event.getClass().toString());
//	}
	
	private NPCShop lastOpened;
	
	@SubscribeEvent
	public void npcTradeOpen(GuiOpenEvent event){
		if(event.getGui() instanceof GuiMerchant){
			if(((GuiMerchant) event.getGui()).getMerchant() instanceof NPCShop){
				lastOpened = (NPCShop) ((GuiMerchant) event.getGui()).getMerchant();
			}else{
				Minecraft mc = Minecraft.getMinecraft();
				event.setGui(new GuiNPCMerchant(mc.thePlayer.inventory, lastOpened, mc.theWorld));
			}
		}
	}
	
	@SubscribeEvent
	public void villagerInteract(EntityInteractSpecific e){
		if(e.getTarget() instanceof EntityVillager){
			if(e.getSide() == Side.CLIENT){
				e.getEntityPlayer().addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Villager trading is disabled in TaleCraft. Use the NPC instead."));
			}
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void playerLoggedOut(PlayerLoggedOutEvent event) {
		if(event.player instanceof EntityPlayerMP) {
			ServerHandler.getServerMirror(null).playerList().playerLeave((EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public void worldLoad(WorldEvent.Load event) {
		TaleCraft.worldsManager.registerWorld(event.getWorld());
		WorkbenchBlock.recipes = WorkbenchManager.fromNBT(WorldFileDataHelper.getTagFromFile(event.getWorld(), "tc_workbench"));
	}

	@SubscribeEvent
	public void worldUnload(WorldEvent.Unload event) {
		TaleCraft.worldsManager.unregisterWorld(event.getWorld());
		WorldFileDataHelper.saveNBTToWorld(event.getWorld(), "tc_workbench", WorkbenchBlock.recipes.toNBT());
	}

	/*
	@SubscribeEvent
	public void guiRenderPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
		event.getGui().drawString(event.getGui().mc.fontRendererObj, "Hello, World!", 1, 1, 0xFFFFFF);
	}
	 */

	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event) {
		if(!event.getWorld().isRemote) {
			ServerHandler.handleEntityJoin(event.getWorld(), event.getEntity());
			TaleCraft.worldsManager.fetchManager(event.getWorld()).joinWorld(event.getEntity());
		}
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		World world = event.getEntity().worldObj;

		if(world.isRemote) return;

		if(world.getGameRules().getBoolean("disable.damage.*")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.fall && world.getGameRules().getBoolean("disable.damage.fall")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.drown && world.getGameRules().getBoolean("disable.damage.drown")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.lava && world.getGameRules().getBoolean("disable.damage.lava")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.magic && world.getGameRules().getBoolean("disable.damage.magic")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.inFire && world.getGameRules().getBoolean("disable.damage.fire")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.inWall && world.getGameRules().getBoolean("disable.damage.suffocate")) {
			event.setCanceled(true);
			return;
		}

	}

	@SubscribeEvent
	public void playerUseItem(final PlayerInteractEvent.RightClickItem event) {
		if(event.getSide() == Side.CLIENT) return;
		ItemStack stack = event.getItemStack();
		final EntityPlayer player = event.getEntityPlayer();
		boolean hasCommandTag = stack.hasTagCompound() ? stack.getTagCompound().hasKey("command") : false;
		if(hasCommandTag){
			String command = stack.getTagCompound().getString("command");
			FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(new ICommandSender() {
				@Override
				public void setCommandStat(Type type, int amount) {
					player.setCommandStat(type, amount);
				}
				@Override
				public boolean sendCommandFeedback() {
					return false;
				}
				@Override
				public MinecraftServer getServer() {
					return FMLCommonHandler.instance().getMinecraftServerInstance();
				}
				@Override
				public Vec3d getPositionVector() {
					return player.getPositionVector();
				}
				
				@Override
				public BlockPos getPosition() {
					return player.getPosition();
				}
				
				@Override
				public String getName() {
					return player.getName();
				}
				
				@Override
				public World getEntityWorld() {
					return player.getEntityWorld();
				}
				
				@Override
				public ITextComponent getDisplayName() {
					return player.getDisplayName();
				}
				@Override
				public Entity getCommandSenderEntity() {
					return player;
				}
				@Override
				public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
					return true;
				}
				@Override
				public void addChatMessage(ITextComponent component) {
					event.getEntityPlayer().addChatMessage(component);
				}
			}, command);
		}
	}
	 

}
