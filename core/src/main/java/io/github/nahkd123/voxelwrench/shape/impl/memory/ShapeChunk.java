package io.github.nahkd123.voxelwrench.shape.impl.memory;

import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;

public class ShapeChunk implements Shape {
	private ShapePalette globalPalette;
	private BlockPos chunkXYZ;
	private int[] data;

	public ShapeChunk(ShapePalette globalPalette, BlockPos chunkXYZ) {
		this.globalPalette = globalPalette;
		this.chunkXYZ = chunkXYZ;
		this.data = new int[16 * 16 * 16];
	}
	
	public BlockPos getChunkXYZ() {
		return chunkXYZ;
	}

	@Override
	public MutableVoxel get(MutableVoxel voxel) {
		voxel.setPattern(globalPalette.getPatternFor(data[voxel.getY() * 16 * 16 + voxel.getZ() * 16 + voxel.getX()]));
		return voxel;
	}

	@Override
	public void set(int x, int y, int z, Pattern pattern) {
		data[y * 16 * 16 + z * 16 + x] = globalPalette.getIdFor(pattern);
	}
}
