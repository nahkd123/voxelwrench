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

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.github.nahkd123.voxelwrench.support.magicavoxel.util.LittleEndian;
import io.github.nahkd123.voxelwrench.util.Nullable;

// It is likely that the RIFF-like format will never change for future version
public class VoxChunk {
	public final String name;
	public final byte[] content;
	public final VoxChunk[] children;

	public VoxChunk(String name, @Nullable byte[] content, VoxChunk... children) {
		if (name == null) throw new NullPointerException("name can't be null");
		if (name.length() > 4) name = name.substring(0, 4);
		while (name.length() < 4) name += " ";
		this.name = name;

		if (content == null) content = new byte[0];
		this.content = content;

		this.children = children;
	}

	public void serialize(DataOutput output) throws IOException {
		output.writeBytes(name);
		LittleEndian.writeInt(output, content.length);
		LittleEndian.writeInt(output, children.length);

		output.write(content);
		for (VoxChunk child : children) child.serialize(output);
	}

	public static VoxChunk build(String name, VoxChunkBuildFunction builder) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream wrapped = new DataOutputStream(bytes);
		List<VoxChunk> children = new ArrayList<>();
		builder.build(wrapped, children::add);
		wrapped.close();
		return new VoxChunk(name, bytes.toByteArray(), children.toArray(VoxChunk[]::new));
	}

	@FunctionalInterface
	public static interface VoxChunkBuildFunction {
		public void build(DataOutput output, Consumer<VoxChunk> children) throws IOException;
	}
}
