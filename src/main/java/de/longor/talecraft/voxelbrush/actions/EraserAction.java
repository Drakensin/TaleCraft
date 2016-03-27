package de.longor.talecraft.voxelbrush.actions;

import de.longor.talecraft.util.MutableBlockPos;
import de.longor.talecraft.voxelbrush.IAction;
import net.minecraft.world.World;

public class EraserAction implements IAction {

	@Override
	public String toString() {
		return "Erase";
	}

	@Override
	public void act(World world, MutableBlockPos pos, int x, int y, int z) {
		world.setBlockToAir(pos);
	}
}
