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
package io.github.nahkd123.voxelwrench.node.param;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

/**
 * <p>A parameter. This can be either input or output parameter of a node.</p>
 * @param <T> The parameter value type. Voxelwrench supports these following value types: All Java primitive
 * types (integers and floats), {@link String}, {@link BlockPos} and {@link Pattern}. Unsupported types are
 * still usable, but they can't be controlled from user's interface.
 */
public interface Parameter<T> {
	/**
	 * <p>Get the ID of this parameter, local to {@link Node}.</p>
	 * @return The ID.
	 */
	public String getParameterId();

	public Node getNode();

	/**
	 * <p>Get the value from this parameter, either calculated or cached.</p>
	 * <p>This may trigger calculation.</p>
	 * @return The value. Implementations must <b>not</b> return {@code null}.
	 */
	public T getValue();
}
