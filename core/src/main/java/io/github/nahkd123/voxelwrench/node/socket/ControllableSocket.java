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
package io.github.nahkd123.voxelwrench.node.socket;

import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.util.Nullable;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

/**
 * <p>A socket with its initial value can be controlled from user interface.</p>
 * @param <T> The type of socket.
 * @param <V> The type of value. The following types will be supported by default: {@link Byte}, {@link Short},
 * {@link Integer}, {@link Long}, {@link Double}, {@link Float}, {@link BlockPos} and {@link Shape}.
 */
public interface ControllableSocket<T extends ControllableSocket<T, V>, V> extends Socket<T, V> {
	/**
	 * <p>Get the value type. This will be used by user interfaces.</p>
	 * @return The type of value.
	 */
	public Class<V> getValueType();

	public V getDefaultControlledValue();

	public void setControlledValue(@Nullable V value);

	/**
	 * <p>Get controlled value.</p>
	 * @return The controlled value.
	 */
	public V getControlledValue();
}
