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

import java.util.Iterator;

import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;
import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

/**
 * <p>A cuboid shape. All voxels in this shape will have the same pattern.</p>
 */
public class CuboidShape implements Shape {
	private int minX, minY, minZ, maxX, maxY, maxZ;
	private Pattern pattern;

	public CuboidShape(int x1, int y1, int z1, int x2, int y2, int z2, Pattern pattern) {
		if (pattern == null) throw new NullPointerException("pattern can't be null. Use PlaceboPattern.PLACEBO instead.");
		minX = Math.min(x1, x2);
		minY = Math.min(y1, y2);
		minZ = Math.min(z1, z2);
		maxX = Math.max(x1, x2);
		maxY = Math.max(y1, y2);
		maxZ = Math.max(z1, z2);
		this.pattern = pattern;
	}

	public CuboidShape(BlockPos pos1, BlockPos pos2, Pattern pattern) {
		this(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), pattern);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		if (pattern == null) throw new NullPointerException("pattern can't be null. Use PlaceboPattern.PLACEBO instead.");
		this.pattern = pattern;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMinZ() {
		return minZ;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMaxZ() {
		return maxZ;
	}

	public void setNewGeometry(int x1, int y1, int z1, int x2, int y2, int z2) {
		minX = Math.min(x1, x2);
		minY = Math.min(y1, y2);
		minZ = Math.min(z1, z2);
		maxX = Math.max(x1, x2);
		maxY = Math.max(y1, y2);
		maxZ = Math.max(z1, z2);
	}

	public void setNewGeometry(BlockPos pos1, BlockPos pos2) {
		setNewGeometry(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
	}

	@Override
	public Iterator<ReadonlyVoxel> iterator() {
		final int width = maxX - minX + 1;
		final int height = maxY - minY + 1;
		final int depth = maxZ - minZ + 1;
		final int floorSize = width * depth;
		final int volume = width * height * depth;

		return new Iterator<ReadonlyVoxel>() {
			int pointer = 0;
			MutableVoxel voxel = new MutableVoxel(0, 0, 0, getPattern());

			void updateVoxel() {
                voxel.setX(minX + (pointer % floorSize) % width);
                voxel.setY(minY + (pointer / floorSize));
                voxel.setZ(minZ + (pointer % floorSize) / width);
			}

			@Override
			public ReadonlyVoxel next() {
				updateVoxel();
				pointer++;
				return voxel;
			}
			
			@Override
			public boolean hasNext() {
				return pointer < volume;
			}
		};
	}
}
