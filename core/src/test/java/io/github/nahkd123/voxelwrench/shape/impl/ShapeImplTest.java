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
package io.github.nahkd123.voxelwrench.shape.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.nahkd123.voxelwrench.pattern.BlockPattern;
import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.shape.impl.memory.MemoryShape;

class ShapeImplTest {
	@Test
	void test() {
		MemoryShape shape = new MemoryShape();
		Pattern pat = new BlockPattern("a", "b");

		shape.set(0, 0, 0, pat);
		shape.set(-1, 0, 0, pat);
		shape.set(0, -1, 0, pat);
		shape.set(0, 0, -1, pat);
		shape.set(-17, 0, 0, pat);
		shape.set(0, -17, 0, pat);
		shape.set(0, 0, -17, pat);
		shape.set(1, 0, 0, pat);
		shape.set(0, 1, 0, pat);
		shape.set(0, 0, 1, pat);
		shape.set(17, 0, 0, pat);
		shape.set(0, 17, 0, pat);
		shape.set(0, 0, 17, pat);

		System.out.println(shape.get(0, 0, 0));
		System.out.println(shape.get(1, 0, 0));
		System.out.println(shape.get(2, 0, 0));
	}
}
