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
package io.github.nahkd123.voxelwrench.shape.impl.memory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.nahkd123.voxelwrench.pattern.Pattern;
import io.github.nahkd123.voxelwrench.pattern.PlaceboPattern;

public class ShapePalette {
	private Map<Integer, Pattern> patternsById = new HashMap<>();
	private Map<Pattern, Integer> idsByPattern = new HashMap<>();

	public ShapePalette() {
		getIdFor(PlaceboPattern.PLACEBO);
	}

	public int getIdFor(Pattern pattern) {
		Integer i = idsByPattern.get(pattern);
		if (i == null) {
			i = idsByPattern.size();
			idsByPattern.put(pattern, i);
			patternsById.put(i, pattern);
		}

		return i;
	}

	public Pattern getPatternFor(int id) {
		return patternsById.get(id);
	}

	public Set<Pattern> getPatterns() {
		return Collections.unmodifiableSet(idsByPattern.keySet());
	}
}