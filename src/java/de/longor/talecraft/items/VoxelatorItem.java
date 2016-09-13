package de.longor.talecraft.items;

import java.util.List;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.voxelator.VXAction;
import de.longor.talecraft.voxelator.VXPredicate;
import de.longor.talecraft.voxelator.VXShape;
import de.longor.talecraft.voxelator.Voxelator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tiffit.talecraft.packet.VoxelatorGuiPacket;

public class VoxelatorItem extends TCItem {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(world.isRemote)
			return;

		if(world.getGameRules().hasRule("disableTCVoxelBrush") && world.getGameRules().getBoolean("disableTCVoxelBrush")) {
			return;
		}

		NBTTagCompound stackCompound = stack.getTagCompound();

		if(stackCompound == null) {
			stackCompound = new NBTTagCompound();
			stack.setTagCompound(stackCompound);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(world.isRemote)
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		
		if(!player.capabilities.isCreativeMode)
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		
		if(!player.capabilities.allowEdit){
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		}
		
		if(!stack.hasTagCompound()){
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		}
		
		NBTTagCompound data = stack.getTagCompound().getCompoundTag("brush_data");
		
		if(player.isSneaking()){
			TaleCraft.network.sendTo(new VoxelatorGuiPacket(data), (EntityPlayerMP) player);
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		}
		
		if(data.hasNoTags())
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		
		float lerp = 1F;
		float dist = 256;
		Vec3d start = this.getPositionEyes(lerp, player);
		Vec3d direction = player.getLook(lerp);
		Vec3d end = start.addVector(direction.xCoord * dist, direction.yCoord * dist, direction.zCoord * dist);

		RayTraceResult result = world.rayTraceBlocks(start, end, false, false, false);

		if(result == null) {
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		}
		NBTTagCompound NBTshape = data.getCompoundTag("shape");
		NBTTagCompound NBTfilter = data.getCompoundTag("filter");
		NBTTagCompound NBTaction = data.getCompoundTag("action");
		System.out.println(data);
		VXShape shape = Voxelator.newShape(NBTshape, result.getBlockPos());
		VXPredicate filter = Voxelator.newFilter(NBTfilter);
		VXAction action = Voxelator.newAction(NBTaction);
		Voxelator.apply(shape, filter, action, world);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		if(!stack.hasTagCompound()){
			super.addInformation(stack, player, tooltip, advanced);
			return;
		}

		NBTTagCompound data = stack.getTagCompound().getCompoundTag("brush_data");
		addDesc(data, tooltip);
		super.addInformation(stack, player, tooltip, advanced);
	}

	public Vec3d getPositionEyes(float partialTicks, EntityPlayer player) {
		if(partialTicks == 1.0F) {
			return new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		} else {
			double d0 = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double d1 = player.prevPosY + (player.posY - player.prevPosY) * partialTicks + player.getEyeHeight();
			double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
			return new Vec3d(d0, d1, d2);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void addDesc(NBTTagCompound data, List<String> tooltip) {
		if(data.hasNoTags()){
			tooltip.add(TextFormatting.RED + "Not Defined Yet");
			return;
		}
		NBTTagCompound shape = data.getCompoundTag("shape");
		NBTTagCompound filter = data.getCompoundTag("filter");
		NBTTagCompound action = data.getCompoundTag("action");
		tooltip.add(shape.getString("type"));
		tooltip.add(filter.getString("type"));
		tooltip.add(action.getString("type"));
	}

}
