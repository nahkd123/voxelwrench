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
package io.github.nahkd123.voxelwrench.node.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import io.github.nahkd123.voxelwrench.node.socket.Socket;

/**
 * <p>A reference to socket that's belongs to a node.</p>
 */
public record SocketRef(String nodeId, String socketId) {
	public SocketRef {
		if (nodeId == null) throw new NullPointerException("nodeId can't be null");
		if (socketId == null) throw new NullPointerException("socketId can't be null");
	}

	public boolean is(Socket<?, ?> socket) {
		return socket.getNode().getNodeId().equals(nodeId()) && socket.getSocketId().equals(socketId());
	}

	public void serialize(DataOutput output) throws IOException {
		output.writeUTF(nodeId());
		output.writeUTF(socketId());
	}

	public static SocketRef deserialize(DataInput input) throws IOException {
		String nodeId = input.readUTF();
		String socketId = input.readUTF();
		return new SocketRef(nodeId, socketId);
	}
}
