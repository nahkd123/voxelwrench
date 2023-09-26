package io.github.nahkd123.voxelwrench.node.provided;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.instancing.Instance;
import io.github.nahkd123.voxelwrench.instancing.InstanceStream;
import io.github.nahkd123.voxelwrench.instancing.Instancer;
import io.github.nahkd123.voxelwrench.instancing.SimpleInstance;
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
			return () -> new InstanceStream() {
				boolean accessed = false;

				@Override
				public Optional<Instance> next() {
					if (accessed) return Optional.empty();
					accessed = true;
					return Optional.of(new SimpleInstance(position.getValue()));
				}
			};
		});

		params.add(position);
		params.add(instancer);
	}
}
