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
package io.github.nahkd123.voxelwrench.support.magicavoxel.vox;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import io.github.nahkd123.voxelwrench.support.magicavoxel.pattern.PalettePattern;
import io.github.nahkd123.voxelwrench.util.voxel.ReadonlyVoxel;

/**
 * <p>Voxel model instance for MagicaVoxel.</p>
 * @see #create()
 * @see #serialize(DataOutput)
 */
public interface Vox {
	/**
	 * <p>Set a voxel in the model. The coordinates of the voxel can be negative.</p>
	 * <p>The position of the model in world coordinates will be stored in {@code ctVW} chunk.</p>
	 * @param x The X position of voxel.
	 * @param y The Y position of voxel.
	 * @param z The Z position of voxel.
	 * @param palette The index in model color palette. If this value is {@code -1}, it will delete the voxel from
	 * the model.
	 */
	public void set(int x, int y, int z, int palette);

	default void set(ReadonlyVoxel voxel) {
		if (voxel.getPattern() instanceof PalettePattern palette) set(voxel.getX(), voxel.getY(), voxel.getZ(), palette.index);
		else delete(voxel.getX(), voxel.getY(), voxel.getZ());
	}

	default void set(Iterator<ReadonlyVoxel> iterator) {
		while (iterator.hasNext()) set(iterator.next());
	}

	default void set(Iterable<ReadonlyVoxel> iterable) {
		set(iterable.iterator());
	}

	default void delete(int x, int y, int z) {
		set(x, y, z, -1);
	}

	/**
	 * <p>Serialize this instance into a binary data that can be used in MagicaVoxel.</p>
	 * <p>The data is prefixed with {@code "VOX " ((int) 150)} (see {@link VoxV150}) header.</p>
	 * @param output Output stream to serialize this instance.
	 * @throws IOException
	 */
	public void serialize(DataOutput output) throws IOException;

	/**
	 * <p>Create a new MagicaVoxel model instance. You should use this method over constructors like {@link VoxV150} to
	 * ensure your library/application works with future version of Voxelwrench.</p>
	 * @return A new MagicaVoxel model instance.
	 */
	public static Vox create() {
		return new VoxV150();
	}
}
