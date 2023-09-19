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
package io.github.nahkd123.voxelwrench.node.socket;

import java.util.Optional;
import java.util.function.Function;

import io.github.nahkd123.voxelwrench.context.VoxelwrenchContext;
import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.util.Nullable;

public class ParameterSocket<V> implements ControllableSocket<ParameterSocket<V>, V> {
	private Node node;
	private String socketId;
	private V defaultValue;
	private Class<V> valueType;
	private @Nullable Function<VoxelwrenchContext, V> computeFunc;
	private Optional<V> currentValue = Optional.empty();
	private Optional<ParameterSocket<V>> input = Optional.empty();

	@SuppressWarnings("unchecked")
	public ParameterSocket(Node node, String socketId, V defaultValue) {
		if (node == null) throw new NullPointerException("node can't be null");
		if (socketId == null) throw new NullPointerException("socketId can't be null");
		if (defaultValue == null) throw new NullPointerException("defaultValue can't be null");
		this.node = node;
		this.socketId = socketId;
		this.defaultValue = defaultValue;
		this.valueType = (Class<V>) defaultValue.getClass();
	}

	@SuppressWarnings("unchecked")
	public ParameterSocket(Node node, String socketId, V defaultValue, Function<VoxelwrenchContext, V> computeFunc) {
		if (node == null) throw new NullPointerException("node can't be null");
		if (socketId == null) throw new NullPointerException("socketId can't be null");
		if (computeFunc == null) throw new NullPointerException("computeFunc can't be null");
		if (defaultValue == null) throw new NullPointerException("defaultValue can't be null");
		this.node = node;
		this.socketId = socketId;
		this.valueType = (Class<V>) defaultValue.getClass();
		this.computeFunc = computeFunc;
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public String getSocketId() {
		return socketId;
	}

	@Override
	public SocketDirection getDirection() {
		return computeFunc == null ? SocketDirection.INPUT : SocketDirection.OUTPUT;
	}

	@Override
	public Class<V> getValueType() {
		return valueType;
	}

	@Override
	public V getDefaultControlledValue() {
		return defaultValue;
	}

	@Override
	public void setControlledValue(@Nullable V value) {
		currentValue = Optional.ofNullable(value);
	}

	@Override
	public V getControlledValue() {
		return currentValue.orElseGet(this::getDefaultControlledValue);
	}

	@Override
	public void addInput(ParameterSocket<V> inputSocket) {
		input = Optional.of(inputSocket);
	}

	@Override
	public void removeInput(ParameterSocket<V> inputSocket) {
		if (input.isPresent() && input.get() == inputSocket) input = Optional.empty();
	}

	@Override
	public V compute(VoxelwrenchContext context) {
		if (computeFunc != null) return computeFunc.apply(context);
		return input.map(v -> v.compute(context)).orElseGet(this::getControlledValue);
	}
}
