package io.github.nahkd123.voxelwrench.shape.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.nahkd123.voxelwrench.pattern.NamespacedPattern;
import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.shape.impl.memory.MemoryShape;

class ShapeImplTest {
	@Test
	void test() {
		MemoryShape shape = new MemoryShape();
		Pattern pat = new NamespacedPattern("a", "b");

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
