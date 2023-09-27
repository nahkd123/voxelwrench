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
