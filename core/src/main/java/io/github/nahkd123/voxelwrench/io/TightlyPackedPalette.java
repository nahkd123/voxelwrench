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
package io.github.nahkd123.voxelwrench.io;

public class TightlyPackedPalette {
	private int bitsSize;
	private int size;
	private int elementsPerEntry;
	private long[] raw;
	private long mask;

	public TightlyPackedPalette(int bitsSize, int size) {
		this.bitsSize = bitsSize;
		this.size = size;
		this.elementsPerEntry = 64 / bitsSize;
		this.raw = new long[(size / this.elementsPerEntry) + 1];
		this.mask = ~0 >>> (64L - bitsSize);
	}

	public int getBitsSize() {
		return bitsSize;
	}

	public int getSize() {
		return size;
	}

	public long get(int index) {
		int elemIdx = index % elementsPerEntry;
		int entryIdx = index / elementsPerEntry;
		long a = (raw[entryIdx] & (mask << ((long) bitsSize * elemIdx))) >> ((long) bitsSize * elemIdx);
		return a;
	}

	public void set(int index, long value) {
		int elemIdx = index % elementsPerEntry;
		int entryIdx = index / elementsPerEntry;
		raw[entryIdx] &= ~(mask << ((long) bitsSize * elemIdx));
		raw[entryIdx] |= (value & mask) << ((long) bitsSize * elemIdx);
	}

	/**
	 * <p>Get direct access to underlying {@code long array}.</p>
	 * @return The {@code long} array backing this palette.
	 */
	public long[] getRaw() {
		return raw;
	}
}
