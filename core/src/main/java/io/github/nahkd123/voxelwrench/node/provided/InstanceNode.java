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
package io.github.nahkd123.voxelwrench.node.provided;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.instancing.Instance;
import io.github.nahkd123.voxelwrench.instancing.Instancer;
import io.github.nahkd123.voxelwrench.instancing.SimpleInstance;
import io.github.nahkd123.voxelwrench.instancing.stream.InstanceStream;
import io.github.nahkd123.voxelwrench.node.AbstractNode;
import io.github.nahkd123.voxelwrench.node.param.InputParameter;
import io.github.nahkd123.voxelwrench.node.param.OutputParameter;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;

public class InstanceNode extends AbstractNode {
	public final InputParameter<BlockPos> position;
	public final OutputParameter<Instancer> instancer;

	public InstanceNode(String id) {
		super(id);
		position = new InputParameter<>(this, "position", new MutableBlockPos(0, 0, 0));
		instancer = new OutputParameter<>(this, "instancer", () -> {
			return context -> new InstanceStream() {
				boolean accessed = false;

				@Override
				public Optional<Instance> next() {
					if (accessed) return Optional.empty();
					accessed = true;
					return Optional.of(new SimpleInstance(false).set(Instance.PROPERTY_POSITION, position.getValue()));
				}
			};
		});

		params.add(position);
		params.add(instancer);
	}
}
