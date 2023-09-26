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
