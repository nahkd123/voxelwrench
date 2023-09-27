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

import io.github.nahkd123.voxelwrench.shape.Shape;

/**
 * <p>An instance contains more than just coordinates: it can contains reference to shape, color information,
 * patterns to use and more.</p>
 * <p>Instances are used to locate where to fill the world with {@link Shape}. The pattern information stored
 * in instance can be used to replace a specific pattern in given {@link Shape}.</p>
 */
public interface Instance {
	public <T> Optional<T> get(PropertyKey<T> key);

	default <T> Instance map(PropertyKey<T> key, UnaryOperator<T> mapper) {
		return new MappingInstance(this, key, mapper);
	}
}
