package io.github.nahkd123.voxelwrench.node;

import java.util.List;

import io.github.nahkd123.voxelwrench.node.param.Parameter;

public interface Node {
	public String getNodeId();
	public List<Parameter<?>> getParameters();
}
