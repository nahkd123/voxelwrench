package io.github.nahkd123.voxelwrench.shape.impl.memory;

import java.util.HashMap;
import java.util.Map;

import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;

public class MemoryShape implements Shape {
	private ShapePalette palette = new ShapePalette();
	private Map<BlockPos, ShapeChunk> chunks = new HashMap<>();

	@Override
	public void set(int x, int y, int z, Pattern pattern) {
		int cx = x >> 4, cy = y >> 4, cz = z >> 4;
		int lx = ((x % 16) + 16) % 16, ly = ((y % 16) + 16) % 16, lz = ((z % 16) + 16) % 16;
		ShapeChunk chunk = chunks.get(new MutableBlockPos(cx, cy, cz));
		if (chunk == null) chunks.put(new MutableBlockPos(cx, cy, cz), chunk = new ShapeChunk(palette, new MutableBlockPos(cx, cy, cz)));
		chunk.set(lx, ly, lz, pattern);
	}

	@Override
	public MutableVoxel get(MutableVoxel voxel) {
		int x = voxel.getX(), y = voxel.getY(), z = voxel.getZ();
		int cx = x >> 4, cy = y >> 4, cz = z >> 4;
		int lx = ((x % 16) + 16) % 16, ly = ((y % 16) + 16) % 16, lz = ((z % 16) + 16) % 16;
		ShapeChunk chunk = chunks.get(new MutableBlockPos(cx, cy, cz));
		voxel.setPattern(chunk != null ? chunk.get(lx, ly, lz).getPattern() : PlaceboPattern.PLACEBO);
		return voxel;
	}
}
