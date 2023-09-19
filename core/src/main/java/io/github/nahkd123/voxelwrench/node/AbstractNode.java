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
package io.github.nahkd123.voxelwrench.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.nahkd123.voxelwrench.node.socket.Socket;

public abstract class AbstractNode implements Node {
	private NodeFactory factory;
	private String nodeId;
	protected final List<Socket<?, ?>> sockets = new ArrayList<>();
	private int x = 0, y = 0, width = 100;

	public AbstractNode(NodeFactory factory, String nodeId) {
		this.factory = factory;
		this.nodeId = nodeId;
	}

	@Override
	public NodeFactory getFactory() {
		return factory;
	}

	@Override
	public String getNodeId() {
		return nodeId;
	}

	@Override
	public Collection<Socket<?, ?>> getSockets() {
		return sockets;
	}

	@Override
	public int getEditorX() {
		return x;
	}

	@Override
	public void setEditorX(int value) {
		x = value;
	}

	@Override
	public int getEditorY() {
		return y;
	}

	@Override
	public void setEditorY(int value) {
		y = value;
	}

	@Override
	public int getEditorWidth() {
		return width;
	}

	@Override
	public void setEditorWidth(int width) {
		this.width = width;
	}
}
