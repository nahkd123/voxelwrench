/*
 * Copyright (c) 2023 nahkd
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import io.github.nahkd123.voxelwrench.node.param.InputParameter;
import io.github.nahkd123.voxelwrench.node.param.Parameter;

public class NodeNetwork {
	private Map<String, Node> nodes = new HashMap<>();
	private int counter = 0;
	private List<InputParameter<?>> inputs = new ArrayList<>();

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
	public List<InputParameter<?>> getInputs() {
		return inputs;
	}
}
