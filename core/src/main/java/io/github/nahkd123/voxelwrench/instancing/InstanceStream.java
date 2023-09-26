package io.github.nahkd123.voxelwrench.instancing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface InstanceStream {
	/**
	 * <p>Get next {@link Instance}, or empty if end of the stream is reached.</p>
	 * @return The next instance.
	 */
	public Optional<Instance> next();

	default InstanceStream generate(BiConsumer<Instance, Consumer<Instance>> generator) {
		InstanceStream self = this;

		return new InstanceStream() {
			private List<Instance> pending = new ArrayList<>();

			@Override
			public Optional<Instance> next() {
				while (pending.isEmpty()) {
					Optional<Instance> next = self.next();
					if (next.isEmpty()) return Optional.empty();
					generator.accept(next.get(), pending::add);
				}

				return Optional.of(pending.remove(0));
			}
		};
	}
}
