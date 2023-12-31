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

import java.util.Optional;
import java.util.regex.Matcher;

import io.github.nahkd123.voxelwrench.shape.Shape;

/**
 * <p>Tagged patterns are patterns with {@code #} as prefix. These patterns can be used in {@link Shape} and
 * "Replace Pattern Instancer" to replace tagged patterns with different one.</p>
 */
public class TaggedPattern implements Pattern {
	public static final PatternFactory FACTORY = new PatternFactory("voxelwrench:tagged", input -> TaggedPattern.tryParse(input).map(v -> (Pattern) v));
	private static final java.util.regex.Pattern REGEX = java.util.regex.Pattern.compile("^#([a-z0-9-_]+)$");
	public final String tag;

	public TaggedPattern(String tag) {
		this.tag = tag;
	}

	@Override
	public String toFactoryString() {
		return "#" + tag;
	}

	public static Optional<TaggedPattern> tryParse(String input) {
		Matcher matcher = REGEX.matcher(input);
		if (!matcher.matches()) return Optional.empty();
		return Optional.of(new TaggedPattern(matcher.group(1)));
	}
}
