package de.longor.talecraft.voxelator.shapes;

import de.longor.talecraft.util.BlockRegion;
import de.longor.talecraft.util.MutableBlockPos;
import de.longor.talecraft.voxelator.BrushParameter;
import de.longor.talecraft.voxelator.CachedWorldDiff;
import de.longor.talecraft.voxelator.VXShape;
import de.longor.talecraft.voxelator.Voxelator.ShapeFactory;
import de.longor.talecraft.voxelator.params.BooleanBrushParameter;
import de.longor.talecraft.voxelator.params.FloatBrushParameter;
import de.longor.talecraft.voxelator.params.IntegerBrushParameter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class VXShapeCylinder extends VXShape {
	private static final BrushParameter[] PARAMS = new BrushParameter[]{
		new FloatBrushParameter("radius", 1, 64, 5),
		new IntegerBrushParameter("height", 0, 64, 5),
		new BooleanBrushParameter("hollow", false)
	};
	
	public static ShapeFactory FACTORY = new ShapeFactory() {
		@Override
		public String getName() {
			return "cylinder";
		}
		@Override
		public VXShape newShape(NBTTagCompound shapeData, BlockPos origin) {
			int px = shapeData.getInteger("position.x") + origin.getX();
			int py = shapeData.getInteger("position.y") + origin.getY();
			int pz = shapeData.getInteger("position.z") + origin.getZ();
			
			float r = shapeData.getFloat("radius");
			int h = shapeData.getInteger("height");
			boolean hollow = shapeData.getBoolean("hollow");
			
			return new VXShapeCylinder(new BlockPos(px, py, pz), r, h, hollow);
		}
		
		@Override
		public NBTTagCompound newShape(String[] parameters) {
			if(parameters.length == 1) {
				NBTTagCompound shapeData = new NBTTagCompound();
				shapeData.setString("type", getName());
				shapeData.setFloat("radius", Float.parseFloat(parameters[0]));
				shapeData.setInteger("height", Integer.parseInt(parameters[1]));
				shapeData.setBoolean("hollow", Boolean.parseBoolean(parameters[2]));
				return shapeData;
			}
			
			return null;
		}
		@Override
		public BrushParameter[] getParameters() {
			return PARAMS;
		}
	};
	
	private final BlockPos position;
	private final float radius;
	private final float radiusSquared;
	private final int height;
	private final boolean hollow;

	public VXShapeCylinder(BlockPos position, float radius, int height, boolean hollow) {
		this.position = position;
		this.radius = radius;
		this.radiusSquared = radius*radius;
		this.height = height;
		this.hollow = hollow;
	}

	@Override
	public BlockPos getCenter() {
		return position;
	}

	@Override
	public BlockRegion getRegion() {
		int sx = position.getX();
		int sy = position.getY();
		int sz = position.getZ();
		int r = (int) (radius + 0.5d);
		return new BlockRegion(
		sx - r +1, sy, sz - r +1,
		sx + r -1, sy + height -1, sz + r -1
		);
	}

	@Override
	public boolean test(BlockPos pos, BlockPos center, MutableBlockPos offset, CachedWorldDiff fworld) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int sx = position.getX();
		int sy = position.getY();
		int sz = position.getZ();
		int dx = x - sx;
		int dy = y - sy;
		int dz = z - sz;
		dy = dy < 0 ? -dy : dy;
		return dy < height && Math.sqrt(dx*dx + dz*dz) <= radius && (hollow ? !(new VXShapeCylinder(position, radius-1, height-1, false).test(pos, center, offset, fworld)) : true);
	}

}
