package io.github.nahkd123.voxelwrench.instancing;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

/**
 * <p>An instance contains more than just coordinates: it can contains reference to shape, color information,
 * patterns to use and more.</p> 
 */
public interface Instance {
	public <T> Optional<T> get(PropertyKey<T> key);

	default BlockPos getPosition() {
		return get(PropertyKey.POSITION).get();
	}
}
