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
package io.github.nahkd123.voxelwrench.node.param;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.util.Nullable;

public class InputParameter<T> implements Parameter<T> {
	private Node node;
	private String id;
	private Optional<Parameter<T>> connectedFrom = Optional.empty();
	private T defaultValue;
	private Optional<T> currentValue = Optional.empty();

	public InputParameter(Node node, String id, T defaultValue) {
		if (node == null) throw new NullPointerException("node can't be null");
		if (id == null) throw new NullPointerException("id can't be null");
		if (defaultValue == null) throw new NullPointerException("defaultValue can't be null");
		this.node = node;
		this.id = id;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getParameterId() {
		return id;
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public T getValue() {
		return connectedFrom.map(v -> v.getValue()).or(() -> currentValue).orElse(defaultValue);
	}

	public void setValue(@Nullable T currentValue) {
		this.currentValue = Optional.ofNullable(currentValue);
	}

	public T getDefaultValue() {
		return defaultValue;
	}

	public void connectToThis(@Nullable OutputParameter<T> inputParam) {
		connectedFrom = Optional.ofNullable(inputParam);
	}
}
