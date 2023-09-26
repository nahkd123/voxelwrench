package io.github.nahkd123.voxelwrench.node.provided;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.instancing.Instance;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;

public class StackInstancerNodeDemo {
	public static void main(String[] args) {
		InstanceNode instance = new InstanceNode("instance");
		StackInstancerNode stack = new StackInstancerNode("stacker");

		instance.instancer.connectTo(stack.origins);
		stack.stackCount.setValue(12);
		stack.stackOffset.setValue(new MutableBlockPos(5, 1, 0));

		var stream = stack.instancer.getValue().stream();
		Optional<Instance> i;
		while ((i = stream.next()).isPresent()) System.out.println(i.get().getPosition());
	}
}
