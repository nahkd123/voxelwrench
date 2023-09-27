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

import io.github.nahkd123.voxelwrench.context.GenerateContext;
import io.github.nahkd123.voxelwrench.instancing.stream.InstanceStream;

/**
 * <p>Instancers are parameter object that creates a new "instance" that can be used in the nodes network.</p>
 * <p>The simplest form of instancer is "Coordinates Instancer", which only contains a single instance located
 * at defined position.</p>
 * <p>There's also "Cuboid Instancer", which fills an entire volume with instances; "Scatter Instancer" that
 * removes a portion of the input instances; "Stack Instancer", which stacks incoming volume towards a specified
 * direction.</p>
 */
public interface Instancer {
	public InstanceStream stream(GenerateContext context);
}
