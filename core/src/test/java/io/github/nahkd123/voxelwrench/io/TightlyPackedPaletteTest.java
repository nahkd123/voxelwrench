package io.github.nahkd123.voxelwrench.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TightlyPackedPaletteTest {

	@Test
	void test() {
		TightlyPackedPalette palette = new TightlyPackedPalette(4, 32);
		assertEquals(palette.getRaw().length, 32 * 4 / 64 + 1);
		palette.set(0, 1);
		palette.set(1, 2);
		palette.set(2, 3);
		palette.set(3, 4);
		palette.set(8, 15);
		System.out.println(Long.toHexString(palette.getRaw()[0]));
		assertEquals(0x0000_000F_0000_4321L, palette.getRaw()[0]);
		assertEquals(1, palette.get(0));
		assertEquals(2, palette.get(1));
		assertEquals(3, palette.get(2));
		assertEquals(4, palette.get(3));
		assertEquals(9, palette.get(8));
	}

}
