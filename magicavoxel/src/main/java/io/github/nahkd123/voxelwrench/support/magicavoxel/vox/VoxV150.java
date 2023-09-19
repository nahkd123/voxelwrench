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
package io.github.nahkd123.voxelwrench.support.magicavoxel.vox;

import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.github.nahkd123.voxelwrench.support.magicavoxel.util.LittleEndian;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;

public class VoxV150 implements Vox {
	public Map<BlockPos, Integer> globalVoxelPos = new HashMap<>();

	@Override
	public void set(int x, int y, int z, int palette) {
		if (palette == -1) {
			globalVoxelPos.remove(new MutableBlockPos(x, y, z));
			return;
		}

		if (palette < 0 || palette >= 256) throw new IndexOutOfBoundsException("palette must be >= 0 and < 256 (MagicaVoxel limitation)");
		globalVoxelPos.put(new MutableBlockPos(x, y, z), palette);
	}

	@Override
	public void serialize(DataOutput output) throws IOException {
		// Compute global position model
		MutableBlockPos origin = null;
		MutableBlockPos max = null;
		for (BlockPos voxelPos : globalVoxelPos.keySet()) {
			if (origin == null) {
				origin = voxelPos.copyAsBlockPos();
				max = voxelPos.copyAsBlockPos();
				continue;
			}

			if (voxelPos.getX() < origin.getX()) origin.setX(voxelPos.getX());
			if (voxelPos.getY() < origin.getY()) origin.setY(voxelPos.getY());
			if (voxelPos.getZ() < origin.getZ()) origin.setZ(voxelPos.getZ());
			if (voxelPos.getX() > max.getX()) max.setX(voxelPos.getX());
			if (voxelPos.getY() > max.getY()) max.setY(voxelPos.getY());
			if (voxelPos.getZ() > max.getZ()) max.setZ(voxelPos.getZ());
		}

		if (origin == null) origin = new MutableBlockPos(0, 0, 0);
		if (max == null) max = origin.copyAsBlockPos();
		MutableBlockPos size = new MutableBlockPos(max.getX() - origin.getX() + 1, max.getY() - origin.getY() + 1, max.getZ() - origin.getZ() + 1);
		final MutableBlockPos finalOrigin = origin;

		// Header
		output.writeBytes("VOX ");
		LittleEndian.writeInt(output, 150);

		/*
		 * Main 'MAIN' {
		 *     Model {
		 *         Size 'SIZE' { int x, int y, int z }
		 *         Geometry 'XYZI' { int numVoxels, int[numVoxels] voxels }
		 *     }
		 *     Palette 'RGBA'
		 * }
		 */

		VoxChunk.build("MAIN", ($1, mainChildren) -> {
			// Custom chunk: Voxelwrench World Origin
			mainChildren.accept(VoxChunk.build("ctVW", (data, $2) -> {
				LittleEndian.writeInt(data, finalOrigin.getX());
				LittleEndian.writeInt(data, finalOrigin.getY());
				LittleEndian.writeInt(data, finalOrigin.getZ());
			}));

			mainChildren.accept(VoxChunk.build("SIZE", (data, $2) -> {
				LittleEndian.writeInt(data, size.getX());
				LittleEndian.writeInt(data, size.getY());
				LittleEndian.writeInt(data, size.getZ());
			}));
			mainChildren.accept(VoxChunk.build("XYZI", (data, $2) -> {
				LittleEndian.writeInt(data, globalVoxelPos.size());

				for (BlockPos pos : globalVoxelPos.keySet()) {
					data.write(pos.getX() - finalOrigin.getX());
					data.write(pos.getY() - finalOrigin.getY());
					data.write(pos.getZ() - finalOrigin.getZ());
					data.write(globalVoxelPos.get(pos));
				}
			}));
		})
		.serialize(output);

		// TODO palette
	}
}
