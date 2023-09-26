package io.github.nahkd123.voxelwrench.node.param;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.shape.processing.ShapeProcessor;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

/**
 * <p>A parameter. This can be either input or output parameter of a node.</p>
 * @param <T> The parameter value type. Voxelwrench supports these following value types: All Java primitive
 * types (integers and floats), {@link ShapeProcessor}, {@link BlockPos} and {@link Pattern}. Unsupported types
 * can't be controlled from user's interface.
 */
public interface Parameter<T> {
	/**
	 * <p>Get the ID of this parameter, local to {@link Node}.</p>
	 * @return The ID.
	 */
	public String getParameterId();

	public Node getNode();

	/**
	 * <p>Get the value from this parameter, either calculated or cached.</p>
	 * <p>This may trigger calculation.</p>
	 * @return The value. Implementations must <b>not</b> return {@code null}.
	 */
	public T getValue();
}
