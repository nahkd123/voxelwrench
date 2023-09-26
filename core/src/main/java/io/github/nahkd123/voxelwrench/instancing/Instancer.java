package io.github.nahkd123.voxelwrench.instancing;

/**
 * <p>Instancers are parameter object that creates a new "instance" that can be used in the nodes network.</p>
 * <p>The simplest form of instancer is "Coordinates Instancer", which only contains a single instance located
 * at defined position.</p>
 * <p>There's also "Cuboid Instancer", which fills an entire volume with instances; "Scatter Instancer" that
 * removes a portion of the input instances; "Stack Instancer", which stacks incoming volume towards a specified
 * direction.</p>
 */
public interface Instancer {
	public InstanceStream stream();
}
