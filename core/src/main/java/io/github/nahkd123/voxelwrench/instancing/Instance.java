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

import io.github.nahkd123.voxelwrench.shape.Shape;

/**
 * <p>An instance contains more than just coordinates: it can contains reference to shape, color information,
 * patterns to use and more.</p>
 * <p>Instances are used to locate where to fill the world with {@link Shape}. The pattern information stored
 * in instance can be used to replace a specific pattern in given {@link Shape}.</p>
 */
public interface Instance {
	public static final String PROPERTY_POSITION = "position";

	/**
	 * <p>Get a property from this instance.</p>
	 * @param <T> The property type.
	 * @param name The name of property. All system properties are defined as static fields in {@link Instance},
	 * like {@link #PROPERTY_POSITION} for example. If you are using custom fields, I recommend adding
	 * {@code namespace:} prefix to the name, like {@code mymod:pattern} for example.
	 * @param type The property type. If the type is mismatched, this will returns empty {@link Optional}.
	 * @return The {@link Optional}. As like any other APIs from Voxelwrench, this will never returns {@code null}
	 * unless specified <b>or</b> the implementation did something wrong.
	 */
	public <T> Optional<T> get(String name, Class<T> type);

	/**
	 * <p><b>Create</b> a new {@link Instance} with added property.</p>
	 * <p>Note that this method <b>creates</b> a new {@link Instance}, not modifying the current instance. If you want
	 * to modify, consider checking out {@link SimpleInstance}.</p>
	 * @param name The name of new property. If it is the same as existing property, it will overrides the previous value.
	 * @param value The property value.
	 * @return The new {@link Instance}.
	 */
	default Instance then(String name, Object value) {
		return new SimpleInstance(this, false).set(name, value);
	}
}
