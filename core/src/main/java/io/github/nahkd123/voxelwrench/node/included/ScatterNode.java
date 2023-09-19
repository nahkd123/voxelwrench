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
package io.github.nahkd123.voxelwrench.node.included;

import io.github.nahkd123.voxelwrench.context.VoxelwrenchContext;
import io.github.nahkd123.voxelwrench.node.AbstractNode;
import io.github.nahkd123.voxelwrench.node.NodeCategory;
import io.github.nahkd123.voxelwrench.node.NodeFactory;
import io.github.nahkd123.voxelwrench.node.socket.ParameterSocket;
import io.github.nahkd123.voxelwrench.node.socket.ShapeSocket;
import io.github.nahkd123.voxelwrench.shape.ScatterShape;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.util.registry.GlobalRegistries;

public class ScatterNode extends AbstractNode {
	public static final NodeFactory FACTORY = new NodeFactory("voxelwrench:scatter", ScatterNode::new, NodeCategory.FILTER);
	public final ShapeSocket volumeInput;
	public final ParameterSocket<Double> scatterRateInput;
	public final ShapeSocket shapeOutput;

	public ScatterNode(String nodeId) {
		super(FACTORY, nodeId);
		sockets.add(volumeInput = new ShapeSocket(this, "shapeInput"));
		sockets.add(scatterRateInput = new ParameterSocket<Double>(this, "scatterRate", 0.5));
		sockets.add(shapeOutput = new ShapeSocket(this, "shapeOutput", this::computeShape));
	}

	public Shape computeShape(VoxelwrenchContext context) {
		return new ScatterShape(volumeInput.compute(context), context.getRandomGenerator(), scatterRateInput.compute(context));
	}

	static {
		GlobalRegistries.REGISTRIES.getNodes().register(FACTORY);
	}
}
