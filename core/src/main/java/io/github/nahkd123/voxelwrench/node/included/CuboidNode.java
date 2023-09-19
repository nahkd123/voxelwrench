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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import io.github.nahkd123.voxelwrench.context.VoxelwrenchContext;
import io.github.nahkd123.voxelwrench.node.AbstractNode;
import io.github.nahkd123.voxelwrench.node.NodeCategory;
import io.github.nahkd123.voxelwrench.node.NodeFactory;
import io.github.nahkd123.voxelwrench.node.socket.ParameterSocket;
import io.github.nahkd123.voxelwrench.node.socket.ShapeSocket;
import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.shape.CuboidShape;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import io.github.nahkd123.voxelwrench.util.registry.GlobalRegistries;
import io.github.nahkd123.voxelwrench.util.registry.RegistriesManager;

public class CuboidNode extends AbstractNode {
	public static final NodeFactory FACTORY = new NodeFactory("voxelwrench:cuboid", CuboidNode::new, NodeCategory.GENERATE);
	public final ParameterSocket<BlockPos> pos1Input;
	public final ParameterSocket<BlockPos> pos2Input;
	public final ParameterSocket<Pattern> patternInput;
	public final ShapeSocket shapeOutput;

	public CuboidNode(String nodeId) {
		super(FACTORY, nodeId);
		sockets.add(pos1Input = new ParameterSocket<BlockPos>(this, "pos1", new MutableBlockPos(0, 0, 0)));
		sockets.add(pos2Input = new ParameterSocket<BlockPos>(this, "pos2", new MutableBlockPos(0, 0, 0)));
		sockets.add(patternInput = new ParameterSocket<Pattern>(this, "pattern", PlaceboPattern.PLACEBO));
		sockets.add(shapeOutput = new ShapeSocket(this, "shape", this::computeShape));
	}

	public Shape computeShape(VoxelwrenchContext context) {
		return new CuboidShape(pos1Input.compute(context), pos2Input.compute(context), patternInput.compute(context));
	}

	@Override
	public void serialize(DataOutput output, RegistriesManager registries) throws IOException {
		pos1Input.getControlledValue().serializeBlockPos(output);
		pos2Input.getControlledValue().serializeBlockPos(output);
		output.writeUTF(patternInput.getControlledValue().toFactoryString());
	}

	@Override
	public void deserialize(DataInput input, RegistriesManager registries) throws IOException {
		pos1Input.setControlledValue(MutableBlockPos.deserialize(input));
		pos2Input.setControlledValue(MutableBlockPos.deserialize(input));
		patternInput.setControlledValue(registries.parsePattern(input.readUTF()).orElse(null));
	}

	static {
		GlobalRegistries.REGISTRIES.getNodes().register(FACTORY);
	}
}
