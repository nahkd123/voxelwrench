package io.github.nahkd123.voxelwrench.instancing;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

public class SimpleInstance implements Instance {
	private BlockPos position;

	public SimpleInstance(BlockPos position) {
		if (position == null) throw new NullPointerException("position can't be null");
		this.position = position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Optional<T> get(PropertyKey<T> key) {
		if (key == PropertyKey.POSITION) return (Optional<T>) Optional.of(position);
		return Optional.empty();
	}
}
