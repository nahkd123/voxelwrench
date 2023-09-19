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
package io.github.nahkd123.voxelwrench.util.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Transitive registry allows some entries to be present depending on current context.</p>
 * @param <T> The registry entry type.
 */
public class TransitiveRegistry<T extends Registerable> implements Registry<T> {
	private Registry<T> parent;
	private Map<String, T> map = new HashMap<>();

	public TransitiveRegistry(Registry<T> parent) {
		if (parent == null) throw new NullPointerException("parent can't be null");
		this.parent = parent;
	}

	public Registry<T> getParent() {
		return parent;
	}

	@Override
	public void register(T entry) {
		if (entry == null) throw new NullPointerException("entry can't be null");

		if (map.containsKey(entry.getRegistryId()) || parent.get(entry.getRegistryId()).isPresent()) {
			throw new IllegalArgumentException("'" + entry.getRegistryId() + "' is already registered!");
		}

		map.put(entry.getRegistryId(), entry);
	}

	@Override
	public Optional<T> get(String id) {
		if (id == null) throw new NullPointerException("id can't be null");
		return Optional.ofNullable(map.get(id)).or(() -> parent.get(id));
	}

	@Override
	public Iterator<T> iterator() {
		if (map.size() == 0) return parent.iterator();
		return new Iterator<T>() {
			Iterator<T> parentIter = parent.iterator();
			Iterator<T> layeredIter = map.values().iterator();

			Iterator<T> getCurrent() {
				return parentIter.hasNext() ? parentIter : layeredIter;
			}

			@Override
			public boolean hasNext() {
				return getCurrent().hasNext();
			}

			@Override
			public T next() {
				return getCurrent().next();
			}
		};
	}
}
