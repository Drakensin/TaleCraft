package de.longor.talecraft.items;

import java.util.List;

import org.mozilla.javascript.Scriptable;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.invoke.EnumTriggerState;
import de.longor.talecraft.invoke.IInvoke;
import de.longor.talecraft.invoke.IInvokeSource;
import de.longor.talecraft.invoke.Invoke;
import de.longor.talecraft.invoke.NullInvoke;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Finish implementing this item.
public class ScriptItem extends TCItem {

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
			return EnumActionResult.PASS;

		NBTTagCompound compound = getNBT(stack);

		// get invoke
		IInvoke invoke = IInvoke.Serializer.read(compound.getCompoundTag("invoke_on_use"));

		// make sure to not waste time
		if(invoke == null) return EnumActionResult.PASS;
		if(invoke instanceof NullInvoke) return EnumActionResult.PASS;

		// execute invoke
		Invoke.invoke(invoke, new TempItemStackInvokeSource(world, new BlockPos(hitX, hitY, hitZ), player), null, EnumTriggerState.ON);

		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(world.isRemote)
			return ActionResult.newResult(EnumActionResult.PASS, stack);

		NBTTagCompound compound = getNBT(stack);

		// get invoke
		IInvoke invoke = IInvoke.Serializer.read(compound.getCompoundTag("invoke_on_rclick"));

		// make sure to not waste time
		if(invoke == null) return ActionResult.newResult(EnumActionResult.PASS, stack);
		if(invoke instanceof NullInvoke) return ActionResult.newResult(EnumActionResult.PASS, stack);

		// execute invoke
		Invoke.invoke(invoke, new TempItemStackInvokeSource(world, player.getPosition(), player), null, EnumTriggerState.ON);

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		NBTTagCompound compound = getNBT(stack);

		// get invoke
		IInvoke invoke = IInvoke.Serializer.read(compound.getCompoundTag("invoke_on_lclick"));

		// make sure to not waste time
		if(invoke == null) return false;
		if(invoke instanceof NullInvoke) return false;

		// execute invoke
		Invoke.invoke(invoke, new TempItemStackInvokeSource(player.worldObj, player.getPosition(), player), null, EnumTriggerState.ON);

		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		// Should I allow this?
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		NBTTagCompound compound = getNBT(stack);

		NBTTagList lore = compound.getTagList("lore", NBT.TAG_STRING);
		if(lore.hasNoTags()) return;

		for(int i = 0; i < lore.tagCount(); i++) {
			tooltip.add(lore.getStringTagAt(i));
		}
	}

	private static final NBTTagCompound getNBT(ItemStack stack) {
		NBTTagCompound comp = stack.getTagCompound();

		if(comp == null) {
			comp = new NBTTagCompound();
			stack.setTagCompound(comp);
		}

		return comp;
	}

	private static class TempItemStackInvokeSource implements IInvokeSource, ICommandSender {
		World world;
		Entity holder;
		BlockPos position;
		Scriptable scriptScope;

		public TempItemStackInvokeSource(World worldIn, BlockPos positionIn, Entity holderIn) {
			this.world = worldIn;
			this.holder = holderIn;
			this.position = positionIn;
		}

		@Override
		public Scriptable getInvokeScriptScope() {
			if(scriptScope == null) {

				// if(holder != null) ... ?

				scriptScope = TaleCraft.globalScriptManager.createNewWorldScope(world);
			}

			return scriptScope;
		}

		@Override
		public ICommandSender getInvokeAsCommandSender() {
			return this;
		}

		@Override
		public BlockPos getInvokePosition() {
			return position;
		}

		@Override
		public World getInvokeWorld() {
			return world;
		}

		@Override
		public void getInvokes(List<IInvoke> invokes) {
			// nope
		}

		@Override
		public String getName() {
			return "ItemStack Invoke Source";
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TextComponentString(getName());
		}

		@Override
		public void addChatMessage(ITextComponent message) {
			if(holder != null)
				holder.addChatMessage(message);
		}

		@Override
		public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
			return true;
		}

		@Override
		public BlockPos getPosition() {
			return position;
		}

		@Override
		public Vec3d getPositionVector() {
			return new Vec3d(position.getX(), position.getY(), position.getZ());
		}

		@Override
		public World getEntityWorld() {
			return world;
		}

		@Override
		public Entity getCommandSenderEntity() {
			return holder;
		}

		@Override
		public boolean sendCommandFeedback() {
			return true;
		}

		@Override
		public void setCommandStat(Type type, int amount) {
			if(holder != null)
				holder.setCommandStat(type, amount);
		}

		@Override
		public void getInvokeColor(float[] color) {
			color[0] = 0.0f;
			color[1] = 0.0f;
			color[2] = 1.0f;
		}

		@Override
		public MinecraftServer getServer() {
			return FMLCommonHandler.instance().getMinecraftServerInstance();
		}

	}

}
