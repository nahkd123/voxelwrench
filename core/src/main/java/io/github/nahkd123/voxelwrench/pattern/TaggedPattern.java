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
