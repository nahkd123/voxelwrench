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
package io.github.nahkd123.voxelwrench.shape_legacy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.random.RandomGenerator;

import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;
import io.github.nahkd123.voxelwrench.util.voxel.MutableVoxel;
import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

/**
 * <p>Scatter voxels from given volume.</p>
 */
public class ScatterShape implements Shape {
	private Shape volume;
	private RandomGenerator randomGenerator;
	private double scatterRate;

	/**
	 * <p>Create a new scatter shape.</p>
	 * @param volume The volume that will contains the scattered voxels from this shape.
	 * @param randomGenerator The random number generator.
	 * @param scatterRate The scattering rate from {@code 0.0} to {@code 1.0}.
	 */
	public ScatterShape(Shape volume, RandomGenerator randomGenerator, double scatterRate) {
		this.volume = volume;
		this.randomGenerator = randomGenerator;
		this.scatterRate = scatterRate;
	}

	public Shape getVolume() {
		return volume;
	}

	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}

	public double getScatterRate() {
		return scatterRate;
	}

	public void setScatterRate(double scatterRate) {
		this.scatterRate = scatterRate;
	}

	@Override
	public Iterator<ReadonlyVoxel> iterator() {
		return new Iterator<ReadonlyVoxel>() {
			Iterator<ReadonlyVoxel> underlyingIter = volume.iterator();
			ReadonlyVoxel next = underlyingIter.hasNext() ? underlyingIter.next() : null;
			MutableVoxel scatterVoxel = new MutableVoxel(0, 0, 0, PlaceboPattern.PLACEBO);

			@Override
			public ReadonlyVoxel next() {
				if (next == null) throw new NoSuchElementException();
				scatterVoxel.setX(next.getX());
				scatterVoxel.setY(next.getY());
				scatterVoxel.setZ(next.getZ());
				scatterVoxel.setPattern(next.getPattern());

				next = null;
				while (underlyingIter.hasNext()) {
					ReadonlyVoxel nextFull = underlyingIter.next();
					if (randomGenerator.nextDouble() < scatterRate) {
						next = nextFull;
						break;
					}
				}

				return scatterVoxel;
			}

			@Override
			public boolean hasNext() {
				return next != null;
			}
		};
	}
}
