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
package io.github.nahkd123.voxelwrench.util.voxel;

import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.shape_legacy.Shape;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;

/**
 * <p>Read-only voxel interface. Mainly used for iterating on every single voxel in a {@link Shape}.</p>
 * <p>Please don't cache this object. The underlying value can be changed depending on current context.</p>
 * <p>If you really want to cache, you can use {@link #copy()}. But beware: this will increase memory usage
 * on large shapes.</p>
 */
public interface ReadonlyVoxel extends BlockPos {
	/**
	 * <p>Get the pattern associated with this voxel.</p>
	 * <p>This method will never returns {@code null}.</p>
	 * @return The pattern.
	 */
	public Pattern getPattern();

	/**
	 * <p>Copy values from this voxel to mutable voxel variant.</p>
	 * @return The copied voxel.
	 */
	default MutableVoxel copyToMutableVoxel() {
	    return new MutableVoxel(getX(), getY(), getZ(), getPattern());
	}
}
