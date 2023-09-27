package io.github.nahkd123.voxelwrench.instancing.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.nahkd123.voxelwrench.instancing.Instance;

public class InstanceGenerateStream implements InstanceStream {
	private InstanceStream underlying;
	private BiConsumer<Instance, Consumer<Instance>> generator;
	private List<Instance> pending = new ArrayList<>();

	public InstanceGenerateStream(InstanceStream underlying, BiConsumer<Instance, Consumer<Instance>> generator) {
		this.underlying = underlying;
		this.generator = generator;
	}

	public InstanceStream getUnderlying() {
		return underlying;
	}

	@Override
	public Optional<Instance> next() {
		while (pending.isEmpty()) {
			Optional<Instance> next = underlying.next();
			if (next.isEmpty()) return Optional.empty();
			generator.accept(next.get(), pending::add);
		}

		return Optional.of(pending.remove(0));
	}
}
