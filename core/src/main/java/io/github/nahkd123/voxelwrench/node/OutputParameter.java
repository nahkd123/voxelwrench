package io.github.nahkd123.voxelwrench.node;

import java.util.function.Supplier;

public class OutputParameter<T> implements Parameter<T> {
	private Node node;
	private String id;
	private Supplier<T> compute;

	public OutputParameter(Node node, String id, Supplier<T> compute) {
		if (node == null) throw new NullPointerException("node can't be null");
		if (id == null) throw new NullPointerException("id can't be null");
		if (compute == null) throw new NullPointerException("compute can't be null");
		this.node = node;
		this.id = id;
		this.compute = compute;
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
		return compute.get();
	}
}
