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
package io.github.nahkd123.voxelwrench.shape;

import java.util.Collections;
import java.util.Iterator;

import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;
import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

public class SingleVoxelShape implements Shape {
	private MutableVoxel voxel;

	public SingleVoxelShape(MutableVoxel voxel) {
		if (voxel == null) throw new NullPointerException("voxel can't be null. Use SingleVoxelShape() instead.");
		this.voxel = voxel;
	}

	public SingleVoxelShape() {
		this(new MutableVoxel(0, 0, 0, PlaceboPattern.PLACEBO));
	}

	/**
	 * <p>Get the voxel that can be modified.</p>
	 * <p>This will never returns {@code null}.</p>
	 * @return The voxel.
	 */
	public MutableVoxel getVoxel() {
		return voxel;
	}

	@Override
	public Iterator<ReadonlyVoxel> iterator() {
		return Collections.singleton((ReadonlyVoxel) voxel).iterator();
	}
}
