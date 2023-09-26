package io.github.nahkd123.voxelwrench.shape;

import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.shape.impl.memory.MemoryShape;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;
import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

public interface Shape {
	public void set(int x, int y, int z, Pattern pattern);

	default void set(BlockPos pos, Pattern pattern) {
		set(pos.getX(), pos.getY(), pos.getZ(), pattern);
	}

	default void set(ReadonlyVoxel voxel) {
		set(voxel.getX(), voxel.getY(), voxel.getZ(), voxel.getPattern());
	}

	/**
	 * <p>Read XYZ from voxel, get pattern at given coordinates and set the pattern to voxel.</p>
	 * <p>If the pattern at given coordinates does not exists, it will returns {@link PlaceboPattern}.</p>
	 * @param voxel The voxel data to find and receive.
	 * @return The same voxel data.
	 */
	public MutableVoxel get(MutableVoxel voxel);

	default MutableVoxel get(int x, int y, int z) {
		return get(new MutableVoxel(x, y, z, PlaceboPattern.PLACEBO));
	}

	default MutableVoxel get(BlockPos pos) {
		return get(pos.getX(), pos.getY(), pos.getZ());
	}

	/**
	 * <p>Create a new shape that stores in the memory.</p>
	 * @return The new memory shape.
	 */
	public static Shape newMemoryShape() {
		return new MemoryShape();
	}
}
