package io.github.nahkd123.voxelwrench.instancing;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class MappingInstance implements Instance {
	private Instance underlying;
	private PropertyKey<?> key;
	private UnaryOperator<?> mapper;

	public <T> MappingInstance(Instance underlying, PropertyKey<T> key, UnaryOperator<T> mapper) {
		if (underlying == null) throw new NullPointerException("underlying can't be null");
		if (key == null) throw new NullPointerException("key can't be null");
		if (mapper == null) throw new NullPointerException("mapper can't be null");
		this.underlying = underlying;
		this.key = key;
		this.mapper = (UnaryOperator<?>) mapper;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> Optional<T> get(PropertyKey<T> key) {
		Optional<T> propertyValue = underlying.get(key);
		if (this.key == key && propertyValue.isPresent()) {
			propertyValue = (Optional<T>) Optional.of(((UnaryOperator) mapper).apply(propertyValue.get()));
		}

		return propertyValue;
	}

}
