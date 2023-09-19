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

public class MutableVoxel implements ReadonlyVoxel {
	private int x, y, z;
	private Pattern pattern;

	public MutableVoxel(int x, int y, int z, Pattern pattern) {
		if (pattern == null) throw new NullPointerException("pattern can't be null. Use PlaceboPattern.PLACEBO instead.");
		this.x = x;
		this.y = y;
		this.z = z;
		this.pattern = pattern;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void setPattern(Pattern pattern) {
		if (pattern == null) throw new NullPointerException("pattern can't be null. Use PlaceboPattern.PLACEBO instead.");
		this.pattern = pattern;
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return "(" + x + "; " + y + "; " + z + "; " + pattern + ")";
	}
}
