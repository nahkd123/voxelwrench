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
