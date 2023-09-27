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
package io.github.nahkd123.voxelwrench.context;

import java.util.random.RandomGenerator;

import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.shape.Shape;

/**
 * <p>Generate context is used for generating voxels for nodes.</p>
 * <p>Generate contexts are usually initialized with a seed, which will be used for random number generator.</p>
 */
public interface GenerateContext {
	/**
	 * <p>Get the random number generator of this context.</p>
	 * @return The random number generator.
	 */
	public RandomGenerator getRandomGenerator();

	/**
	 * <p>Get world accessor. This can be a file from the underlying file system, or a Minecraft world loaded
	 * in the memory.</p>
	 * <p>Removing voxels can be done by setting the pattern to {@link PlaceboPattern}.</p>
	 * @return The world accessor.
	 */
	public Shape getWorld();
}
