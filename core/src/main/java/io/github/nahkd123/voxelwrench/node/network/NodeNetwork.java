package io.github.nahkd123.voxelwrench.node.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.node.NodeRef;
import io.github.nahkd123.voxelwrench.node.ParameterRef;
import io.github.nahkd123.voxelwrench.node.param.Parameter;

public class NodeNetwork {
	private Map<String, Node> nodes = new HashMap<>();
	private int counter = 0;
	private List<Parameter<?>> inputs = new ArrayList<>();

	public String createNewId() {
		return "node-network-" + (counter++);
	}

	public Map<String, Node> getNodes() {
		return Collections.unmodifiableMap(nodes);
	}

	public Optional<Node> getNode(NodeRef ref) {
		return Optional.ofNullable(nodes.get(ref.id()));
	}

	public Optional<Parameter<?>> getParameter(ParameterRef ref) {
		return getNode(ref.nodeRef())
				.flatMap(v -> v.getParameters().stream()
						.filter(u -> u.getParameterId().equals(ref.parameterId()))
						.findAny());
	}

	public void add(Node node) {
		nodes.put(node.getNodeId(), node);
	}

	public void remove(Node node) {
		nodes.remove(node.getNodeId());
	}

	/**
	 * <p>Get a list of input parameters that the editor can interact to.</p>
	 * <p>This list can be modified.</p>
	 * @return A modifiable list.
	 */
	public List<Parameter<?>> getInputs() {
		return inputs;
	}
}
