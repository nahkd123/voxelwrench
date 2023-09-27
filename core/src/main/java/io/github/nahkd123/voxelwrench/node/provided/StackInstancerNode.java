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

import io.github.nahkd123.voxelwrench.instancing.Instancer;
import io.github.nahkd123.voxelwrench.instancing.PropertyKey;
import io.github.nahkd123.voxelwrench.node.AbstractNode;
import io.github.nahkd123.voxelwrench.node.param.InputParameter;
import io.github.nahkd123.voxelwrench.node.param.OutputParameter;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;

/**
 * <p>Stack instancer node: Create instances based on origin and stacking offset.</p>
 * <p>Useful for: creating an array of buildings (heck you can make it random with instance property
 * randomizer).</p>
 */
public class StackInstancerNode extends AbstractNode {
	public final InputParameter<Instancer> origins;
	public final InputParameter<BlockPos> stackOffset;
	public final InputParameter<Integer> stackCount;
	public final OutputParameter<Instancer> instancer;

	public StackInstancerNode(String id) {
		super(id);

		origins = new InputParameter<>(this, "origins", () -> () -> Optional.empty());
		stackOffset = new InputParameter<>(this, "stackOffset", new MutableBlockPos(1, 0, 0));
		stackCount = new InputParameter<Integer>(this, "stackCount", 1);

		instancer = new OutputParameter<Instancer>(this, "instancer", () -> () -> origins.getValue().stream().generate((instance, yield) -> {
			int stackCount = this.stackCount.getValue();
			if (stackCount == 0) return;

			BlockPos offset = this.stackOffset.getValue();
			yield.accept(instance);

			for (int stack = 1; stack < stackCount; stack++) {
				int stack2 = stack;
				yield.accept(instance.map(PropertyKey.POSITION, p -> new MutableBlockPos(
						p.getX() + offset.getX() * stack2,
						p.getY() + offset.getY() * stack2,
						p.getZ() + offset.getZ() * stack2)));
			}
		}));

		params.add(origins);
		params.add(stackOffset);
		params.add(stackCount);
		params.add(instancer);
	}
}
