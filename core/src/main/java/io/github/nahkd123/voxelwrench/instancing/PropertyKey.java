package io.github.nahkd123.voxelwrench.instancing;

import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

public class PropertyKey<T> {
	public static final PropertyKey<BlockPos> POSITION = new PropertyKey<>(BlockPos.class, "voxelwrench:position");

	private Class<T> clazz;
	private String id;

	public PropertyKey(Class<T> clazz, String id) {
		this.clazz = clazz;
		this.id = id;
	}

	public Class<T> getPropertyClass() {
		return clazz;
	}

	public String getId() {
		return id;
	}
}
