package io.github.nahkd123.voxelwrench.node;

import io.github.nahkd123.voxelwrench.node.param.Parameter;

public record ParameterRef(String nodeId, String parameterId) {
	public ParameterRef {
		if (nodeId == null) throw new NullPointerException("nodeId can't be null");
		if (parameterId == null) throw new NullPointerException("parameterId can't be null");
	}

	public ParameterRef(NodeRef node, String parameterId) {
		this(node.id(), parameterId);
	}

	public ParameterRef(Parameter<?> parameter) {
		this(parameter.getNode().getNodeId(), parameter.getParameterId());
	}
}
