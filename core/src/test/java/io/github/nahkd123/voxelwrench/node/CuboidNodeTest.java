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
package io.github.nahkd123.voxelwrench.node;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.nahkd123.voxelwrench.context.SimpleVoxelwrenchSocket;
import io.github.nahkd123.voxelwrench.node.included.CuboidNode;
import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;

class CuboidNodeTest {
	@Test
	void testDirectInputs() {
		CuboidNode node = new CuboidNode("abcdef");
		node.pos1Input.setControlledValue(new MutableBlockPos(-1, -1, -1));
		node.pos2Input.setControlledValue(new MutableBlockPos(1, 1, 1));

		Shape shape = node.shapeOutput.compute(new SimpleVoxelwrenchSocket());
	    Set<String> voxels = new HashSet<>();
		shape.forEach(v -> voxels.add(v.toString()));

		assertEquals(27, voxels.size());
        assertTrue(voxels.contains(new MutableVoxel(-1, -1, -1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(-1, -1, 1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(1, -1, -1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(1, -1, 1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(-1, 1, -1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(-1, 1, 1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(1, 1, -1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(1, 1, 1, PlaceboPattern.PLACEBO).toString()));
        assertTrue(voxels.contains(new MutableVoxel(0, 0, 0, PlaceboPattern.PLACEBO).toString()));
	}
}
