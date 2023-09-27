/*
 * Copyright (c) 2023 nahkd
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.nahkd123.voxelwrench.shape;

import io.github.nahkd123.voxelwrench.instancing.Instancer;
import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.shape.impl.memory.MemoryShape;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;
import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

/**
 * <p>Shapes are "templates" that can be placed to the world.</p>
 * <p>Shapes allows you to place "placeholder pattern", which can be replaced with different pattern when using
 * with {@link Instancer}.</p>
 */
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
