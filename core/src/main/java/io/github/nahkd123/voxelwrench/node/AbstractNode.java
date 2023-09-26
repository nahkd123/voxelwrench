package io.github.nahkd123.voxelwrench.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.nahkd123.voxelwrench.node.param.Parameter;

public abstract class AbstractNode implements Node {
	protected String id;
	protected List<Parameter<?>> params = new ArrayList<>();

	public AbstractNode(String id) {
		this.id = id;
	}

	@Override
	public String getNodeId() {
		return id;
	}

	@Override
	public List<Parameter<?>> getParameters() {
		return Collections.unmodifiableList(params);
	}
}
