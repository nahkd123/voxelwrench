package io.github.nahkd123.voxelwrench.node.param;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.util.Nullable;

public class InputParameter<T> implements Parameter<T> {
	private Node node;
	private String id;
	private Optional<Parameter<T>> connectedFrom = Optional.empty();
	private T defaultValue;
	private Optional<T> currentValue = Optional.empty();

	public InputParameter(Node node, String id, T defaultValue) {
		if (node == null) throw new NullPointerException("node can't be null");
		if (id == null) throw new NullPointerException("id can't be null");
		if (defaultValue == null) throw new NullPointerException("defaultValue can't be null");
		this.node = node;
		this.id = id;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getParameterId() {
		return id;
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public T getValue() {
		return connectedFrom.map(v -> v.getValue()).or(() -> currentValue).orElse(defaultValue);
	}

	public void setValue(@Nullable T currentValue) {
		this.currentValue = Optional.ofNullable(currentValue);
	}

	public T getDefaultValue() {
		return defaultValue;
	}
}
