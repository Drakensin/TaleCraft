package de.longor.talecraft.voxelator.actions;

import de.longor.talecraft.util.GObjectTypeHelper;
import de.longor.talecraft.util.MutableBlockPos;
import de.longor.talecraft.voxelator.CachedWorldDiff;
import de.longor.talecraft.voxelator.VXAction;
import de.longor.talecraft.voxelator.BrushParameter;
import de.longor.talecraft.voxelator.params.BlockstateBrushParameter;
import de.longor.talecraft.voxelator.Voxelator.ActionFactory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class VXActionReplace extends VXAction {
	private static final BrushParameter[] PARAMS = new BrushParameter[]{
		new BlockstateBrushParameter("state", Blocks.STONE)
	};
	
	public static final ActionFactory FACTORY = new ActionFactory() {
		@Override public String getName() {
			return "replace";
		}
		
		@Override public VXAction newAction(NBTTagCompound actionData) {
			return new VXActionReplace(GObjectTypeHelper.findBlockState(actionData.getString("state")));
		}
		@Override public NBTTagCompound newAction(String[] parameters) {
			NBTTagCompound actionData = new NBTTagCompound();
			actionData.setString("type", getName());
			actionData.setString("state", parameters[0]);
			return actionData;
		}
		
		@Override
		public BrushParameter[] getParameters() {
			return PARAMS;
		}
	};
	
	private final IBlockState state;
	public VXActionReplace(IBlockState state) {
		this.state = state;
	}

	@Override
	public void apply(BlockPos pos,BlockPos center,MutableBlockPos offset, CachedWorldDiff fworld) {
		fworld.setBlockState(pos, state);
	}
}
