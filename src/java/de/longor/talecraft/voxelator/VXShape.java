package de.longor.talecraft.voxelator;

import de.longor.talecraft.util.BlockRegion;
import de.longor.talecraft.util.MutableBlockPos;
import de.longor.talecraft.voxelator.shapes.VXShapeBox;
import de.longor.talecraft.voxelator.shapes.VXShapeSphere;
import net.minecraft.util.math.BlockPos;

public abstract class VXShape {
	
	public static enum VXShapes{
		Sphere("Sphere"), Box("Box"), Cylinder("Cylinder");
		
		String name;
		
		VXShapes(String name){
			this.name = name;
		}
	
		public String toString(){
			return name;
		}
		
		public int getID(){
			return ordinal();
		}
		
		public static VXShapes get(int id){
			return VXShapes.values()[id];
		}
		
	}
	
	public abstract BlockPos getCenter();
	public abstract BlockRegion getRegion();

	/** @return TRUE, if the given <i>pos</i> is inside the shape. FALSE if not. **/
	public abstract boolean test(BlockPos pos, BlockPos center, MutableBlockPos offset, CachedWorldDiff fworld);

}