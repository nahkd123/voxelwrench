package io.github.nahkd123.voxelwrench.shape.processing;

import io.github.nahkd123.voxelwrench.shape.Shape;

/**
 * <p>Shape processors are processors that permutes the {@link Shape}.</p>
 */
@FunctionalInterface
public interface ShapeProcessor {
	public void process(Shape shape);

	default ShapeProcessor then(ShapeProcessor processor) {
		return shape -> {
			this.process(shape);
			processor.process(shape);
		};
	}
}
