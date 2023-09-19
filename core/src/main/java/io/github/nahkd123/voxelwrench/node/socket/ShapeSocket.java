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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import io.github.nahkd123.voxelwrench.context.VoxelwrenchContext;
import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.shape.SimpleMergedShape;
import io.github.nahkd123.voxelwrench.shape.VoidShape;
import io.github.nahkd123.voxelwrench.util.Nullable;

public class ShapeSocket implements ControllableSocket<ShapeSocket, Shape> {
	private Node node;
	private String socketId;
	private @Nullable Function<VoxelwrenchContext, Shape> computeFunc;
	private Set<ShapeSocket> inputs = new HashSet<>();
	private @Nullable Shape currentValue = null;

	public ShapeSocket(Node node, String socketId, @Nullable Function<VoxelwrenchContext, Shape> computeFunc) {
		if (node == null) throw new NullPointerException("node can't be null");
		if (socketId == null) throw new NullPointerException("socketId can't be null");
		this.node = node;
		this.socketId = socketId;
		this.computeFunc = computeFunc;
	}

	public ShapeSocket(Node node, String socketId) {
		this(node, socketId, null);
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
		return computeFunc != null ? SocketDirection.OUTPUT : SocketDirection.INPUT;
	}

	@Override
	public Shape compute(VoxelwrenchContext context) {
		if (computeFunc != null) return computeFunc.apply(context);
		if (inputs.size() == 0) return VoidShape.VOID;
		if (inputs.size() == 1) {
			for (ShapeSocket input : inputs) return input.compute(context);
		}

		return new SimpleMergedShape(inputs.stream().map(v -> v.compute(context)).toArray(Shape[]::new));
	}

	@Override
	public void addInput(ShapeSocket inputSocket) {
		inputs.add(inputSocket);
	}

	@Override
	public void removeInput(ShapeSocket inputSocket) {
		inputs.remove(inputSocket);
	}

	@Override
	public Class<Shape> getValueType() {
		return Shape.class;
	}

	@Override
	public Shape getDefaultControlledValue() {
		return VoidShape.VOID;
	}

	@Override
	public void setControlledValue(Shape value) {
		currentValue = value; // TODO clone shape
	}

	@Override
	public Shape getControlledValue() {
		return currentValue != null ? currentValue : VoidShape.VOID;
	}
}
