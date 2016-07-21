package de.longor.talecraft.items;

import de.longor.talecraft.util.WorldHelper;
import de.longor.talecraft.util.WorldHelper.BlockRegionIterator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MetaSwapperItem extends TCItem {

	private static final BlockRegionIterator swapFunction = new BlockRegionIterator() {
		@Override
		public void $(World world, IBlockState state, BlockPos pos) {
			IBlockState oldState = state;
			Block block = oldState.getBlock();

			int oldMeta = block.getMetaFromState(oldState);
			int newMeta = (oldMeta + 1) & 0xF;

			IBlockState newState = block.getStateFromMeta(newMeta);
			world.setBlockState(pos, newState);
		}
	};

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote)
			return EnumActionResult.PASS;

		swapFunction.$(worldIn, worldIn.getBlockState(pos), pos);

		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(world.isRemote)
			return ActionResult.newResult(EnumActionResult.PASS, stack);

		if(player.isSneaking()) {
			int[] bounds = WandItem.getBoundsFromPlayerOrNull(player);

			if(bounds == null) {
				player.addChatMessage(new TextComponentString("No region selected with wand."));
			}

			WorldHelper.foreach(world, bounds, swapFunction);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

}
