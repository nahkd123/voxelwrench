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
