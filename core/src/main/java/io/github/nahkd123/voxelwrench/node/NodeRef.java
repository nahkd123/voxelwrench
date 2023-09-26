package io.github.nahkd123.voxelwrench.node;

public record NodeRef(String id) {
	public NodeRef {
		if (id == null) throw new NullPointerException("id can't be null");
	}

	public NodeRef(Node node) {
		this(node.getNodeId());
	}
}
