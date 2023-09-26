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
package io.github.nahkd123.voxelwrench.shape_legacy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

public class SimpleMergedShape implements Shape {
	private Shape[] shapes;

	public SimpleMergedShape(Shape... shapes) {
		this.shapes = shapes;
	}

	/**
	 * <p>Flatten this merged shape.</p>
	 * @return The flatten shape.
	 */
	public SimpleMergedShape flat() {
		List<Shape> list = new ArrayList<>();

		for (Shape shape : shapes) {
			if (shape instanceof SimpleMergedShape sms) list.addAll(Arrays.asList(sms.shapes));
			else list.add(shape);
		}

		return new SimpleMergedShape(list.toArray(Shape[]::new));
	}

	@Override
	public Iterator<ReadonlyVoxel> iterator() {
		return Stream.of(shapes).flatMap(v -> StreamSupport.stream(v.spliterator(), false)).iterator();
	}
}
