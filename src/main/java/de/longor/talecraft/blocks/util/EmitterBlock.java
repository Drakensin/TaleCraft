package de.longor.talecraft.blocks.util;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.blocks.TCBlockContainer;
import de.longor.talecraft.blocks.TCITriggerableBlock;
import de.longor.talecraft.blocks.util.tileentity.EmitterBlockTileEntity;
import de.longor.talecraft.client.gui.blocks.GuiEmitterBlock;
import de.longor.talecraft.invoke.EnumTriggerState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EmitterBlock extends TCBlockContainer implements TCITriggerableBlock {

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new EmitterBlockTileEntity();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			return true;
		if(!TaleCraft.proxy.isBuildMode())
			return false;
		if(playerIn.isSneaking())
			return true;

		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new GuiEmitterBlock((EmitterBlockTileEntity)worldIn.getTileEntity(pos)));

		return true;
	}

	@Override
	public void trigger(World world, BlockPos position, EnumTriggerState triggerState) {
		if (world.isRemote)
			return;

		TileEntity tileentity = world.getTileEntity(position);

		if (tileentity instanceof EmitterBlockTileEntity) {
			switch (triggerState) {
			case ON: ((EmitterBlockTileEntity) tileentity).setActive(true); break;
			case OFF: ((EmitterBlockTileEntity) tileentity).setActive(false); break;
			case INVERT: ((EmitterBlockTileEntity) tileentity).toggleActive(); break;
			case IGNORE: ((EmitterBlockTileEntity) tileentity).setActive(true); break;
			default: break;
			}
		}
	}

}
