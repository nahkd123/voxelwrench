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
