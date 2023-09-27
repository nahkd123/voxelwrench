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
package io.github.nahkd123.voxelwrench.instancing.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import io.github.nahkd123.voxelwrench.instancing.Instance;

public interface InstanceStream {
	/**
	 * <p>Get next {@link Instance}, or empty if end of the stream is reached.</p>
	 * @return The next instance.
	 */
	public Optional<Instance> next();

	default void collectRemaining(Consumer<Instance> collector) {
		for (Optional<Instance> instance = next(); instance.isPresent(); instance = next()) {
			collector.accept(instance.get());
		}
	}

	default List<Instance> collectRemaining() {
		List<Instance> list = new ArrayList<>();
		collectRemaining(list::add);
		return list; // TODO make this unmodifiable?
	}

	/**
	 * <p>Map the instance 1 by N, where N is the number of instances generated from generator.</p>
	 * @param generator The generator. This will be applied on each instance.
	 * @return New stream with generator applied.
	 */
	default InstanceStream generate(BiConsumer<Instance, Consumer<Instance>> generator) {
		return new InstanceGenerateStream(this, generator);
	}

	/**
	 * <p>Map the instance 1 by 1.</p>
	 * @param mapper The mapping function.
	 * @return New stream with mapping function applied.
	 */
	default InstanceStream map(UnaryOperator<Instance> mapper) {
		return new InstanceGenerateStream(this, (instance, consumer) -> consumer.accept(mapper.apply(instance)));
	}
}
