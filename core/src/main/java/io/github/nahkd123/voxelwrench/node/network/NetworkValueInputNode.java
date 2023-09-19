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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import io.github.nahkd123.voxelwrench.node.AbstractNode;
import io.github.nahkd123.voxelwrench.node.NodeFactory;
import io.github.nahkd123.voxelwrench.node.socket.ParameterSocket;
import io.github.nahkd123.voxelwrench.util.Nullable;
import io.github.nahkd123.voxelwrench.util.registry.GlobalRegistries;
import io.github.nahkd123.voxelwrench.util.registry.RegistriesManager;

public class NetworkValueInputNode extends AbstractNode {
	public static final NodeFactory FACTORY = new NodeFactory("voxelwrench:network_value_input", NetworkValueInputNode::new);
	public final ParameterSocket<Double> output;
	private String displayName = "Value Input";
	private double value = 0d;

	public NetworkValueInputNode(String nodeId) {
		super(FACTORY, nodeId);
		sockets.add(output = new ParameterSocket<Double>(this, "output", 0d, context -> value));
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(@Nullable String displayName) {
		if (displayName == null) displayName = "";
		this.displayName = displayName;
	}

	@Override
	public void serialize(DataOutput output, RegistriesManager registries) throws IOException {
		output.writeUTF(displayName);
		output.writeDouble(value);
	}

	@Override
	public void deserialize(DataInput input, RegistriesManager registries) throws IOException {
		displayName = input.readUTF();
		value = input.readDouble();
	}

	static {
		GlobalRegistries.REGISTRIES.getNodes().register(FACTORY);
	}
}
