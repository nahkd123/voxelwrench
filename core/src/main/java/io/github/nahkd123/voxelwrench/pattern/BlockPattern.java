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
package io.github.nahkd123.voxelwrench.pattern;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;

import io.github.nahkd123.voxelwrench.util.Nullable;

/**
 * <p>A pattern with namespace, key and extra data, similar to Minecraft block states. For example:
 * {@code minecraft:chest[rotation=south]}.</p>
 */
public class BlockPattern implements Pattern {
	public static final PatternFactory FACTORY = new PatternFactory("voxelwrench:namespaced", input -> BlockPattern.tryParse(input).map(v -> (Pattern) v));
	private static final java.util.regex.Pattern REGEX = java.util.regex.Pattern.compile("^([a-z0-9][a-z0-9_-]*)(:([a-z0-9_-]+))?(\\[.*\\])?$");
	public final String namespace;
	public final String key;
	public final String extra;

	public BlockPattern(@Nullable String namespace, String key, @Nullable String extra) {
		if (namespace == null) namespace = "minecraft";
		this.namespace = namespace;

		if (key == null) throw new NullPointerException("key can't be null");
		this.key = key;

		this.extra = extra != null ? extra : "";
	}

	public BlockPattern(@Nullable String namespace, String key) {
		this(namespace, key, null);
	}

	public static Optional<BlockPattern> tryParse(String input) {
		Matcher matcher = REGEX.matcher(input);
		if (!matcher.matches()) return Optional.empty();

		String a = matcher.group(1);
		String b = matcher.group(3);
		String c = matcher.group(4);
		if (b == null) return Optional.of(new BlockPattern(null, a, c));
		else return Optional.of(new BlockPattern(a, b, c));
	}

	@Override
	public String toFactoryString() {
		return namespace + ":" + key + "[" + extra + "]";
	}

	@Override
	public String toString() {
		return toFactoryString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, namespace, extra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BlockPattern)) {
			return false;
		}
		BlockPattern other = (BlockPattern) obj;
		return Objects.equals(key, other.key) && Objects.equals(namespace, other.namespace) && Objects.equals(extra, other.extra);
	}
}
