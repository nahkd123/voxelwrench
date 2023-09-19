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

import io.github.nahkd123.voxelwrench.context.VoxelwrenchContext;
import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.node.network.SocketRef;

public interface Socket<T extends Socket<T, V>, V> {
	/**
	 * <p>Get the node that holding this socket.</p>
	 * @return The node.
	 */
	public Node getNode();

	/**
	 * <p>The ID of this socket.</p>
	 * <p>This value must be unique comparing to other sockets in the same node.</p>
	 * @return The socket ID.
	 */
	public String getSocketId();

	/**
	 * <p>Obtain the direction of this socket.</p>
	 * @return The direction of this socket.
	 */
	public SocketDirection getDirection();

	/**
	 * <p>Compute the output of this socket from a set of inputs.</p>
	 * @param context The context.
	 * @return The computed value.
	 */
	public V compute(VoxelwrenchContext context);

	/**
	 * <p>Connect a socket to this socket.</p>
	 * @param inputSocket Input socket.
	 */
	public void addInput(T inputSocket);

	/**
	 * <p>Disconnect a socket from this socket.</p>
	 * @param inputSocket Input socket.
	 */
	public void removeInput(T inputSocket);

	/**
	 * <p>Connect this socket to a socket.</p>
	 * @param destination The destination socket to connect.
	 * @return The destination socket.
	 */
	@SuppressWarnings("unchecked")
	default T connectTo(T destination) {
		destination.addInput((T) this);
		return destination;
	}

	/**
	 * <p>Get the maximum number of inputs that can be connected to this socket. Default is 1.</p>
	 * <p>Use {@code -1} for unlimited number of inputs.</p>
	 * @return The maximum number of inputs.
	 */
	default int getMaximumInputs() {
		return 1;
	}

	default SocketRef createRef() {
		return new SocketRef(getNode().getNodeId(), getSocketId());
	}
}
