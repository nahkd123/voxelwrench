package io.github.nahkd123.voxelwrench.node.provided;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.instancing.Instance;
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
				yield.accept(new Instance() {
					@SuppressWarnings("unchecked")
					@Override
					public <T> Optional<T> get(PropertyKey<T> key) {
						if (key == PropertyKey.POSITION) return (Optional<T>) Optional.of(new MutableBlockPos(
								offset.getX() * stack2,
								offset.getY() * stack2,
								offset.getZ() * stack2));
						return instance.get(key);
					}
				});
			}
		}));

		params.add(origins);
		params.add(stackOffset);
		params.add(stackCount);
		params.add(instancer);
	}
}
