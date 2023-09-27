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
package io.github.nahkd123.voxelwrench.instancing;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class MappingInstance implements Instance {
	private Instance underlying;
	private PropertyKey<?> key;
	private UnaryOperator<?> mapper;

	public <T> MappingInstance(Instance underlying, PropertyKey<T> key, UnaryOperator<T> mapper) {
		if (underlying == null) throw new NullPointerException("underlying can't be null");
		if (key == null) throw new NullPointerException("key can't be null");
		if (mapper == null) throw new NullPointerException("mapper can't be null");
		this.underlying = underlying;
		this.key = key;
		this.mapper = (UnaryOperator<?>) mapper;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> Optional<T> get(PropertyKey<T> key) {
		Optional<T> propertyValue = underlying.get(key);
		if (this.key == key && propertyValue.isPresent()) {
			propertyValue = (Optional<T>) Optional.of(((UnaryOperator) mapper).apply(propertyValue.get()));
		}

		return propertyValue;
	}

}
