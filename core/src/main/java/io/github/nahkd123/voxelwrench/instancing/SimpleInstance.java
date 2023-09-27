package io.github.nahkd123.voxelwrench.instancing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.github.nahkd123.voxelwrench.util.Nullable;

public class SimpleInstance implements Instance {
	private Instance parent = null;
	private List<Map.Entry<String, Object>> list = null;
	private Map<String, Object> map = null;

	/**
	 * <p>Create a new simple instance that can have its properties modified.</p>
	 * @param parent Parent instance, or {@code null} if you don't want to do "property mapping".
	 * @param useHashMap Set this to {@code true} to use {@link HashMap} for properties. If you have a lot of
	 * properties, you might want to use this. I think if you only have up to 5 properties, you better use
	 * {@code false} for this.
	 */
	public SimpleInstance(@Nullable Instance parent, boolean useHashMap) {
		this.parent = parent;
		if (useHashMap) map = new HashMap<>();
		else list = new ArrayList<>(); // TODO do we use LinkedList here? see "set()"
	}

	public SimpleInstance(boolean useHashMap) {
		this(null, useHashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Optional<T> get(String name, Class<T> type) {
		if (list != null) return (Optional<T>) list.stream()
				.filter(v -> v.getKey().equals(name) && type.isAssignableFrom(v.getValue().getClass()))
				.findFirst()
				.map(v -> v.getValue());

		if (map != null) {
			Object v = map.get(name);
			return type.isAssignableFrom(v.getClass()) ? Optional.of((T) v) : Optional.empty();
		}

		return parent != null ? parent.get(name, type) : Optional.empty();
	}

	public SimpleInstance set(String name, Object value) {
		if (list != null) list.add(0, Map.entry(name, value)); // TODO check before adding
		if (map != null) map.put(name, value);
		return this;
	}

	public Optional<Instance> getParent() {
		return Optional.ofNullable(parent);
	}
}
