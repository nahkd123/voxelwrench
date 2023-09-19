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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.github.nahkd123.voxelwrench.context.VoxelwrenchContext;
import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.node.NodeFactory;
import io.github.nahkd123.voxelwrench.node.socket.Socket;
import io.github.nahkd123.voxelwrench.node.socket.SocketDirection;
import io.github.nahkd123.voxelwrench.shape.Shape;
import io.github.nahkd123.voxelwrench.shape.SimpleMergedShape;
import io.github.nahkd123.voxelwrench.util.registry.RegistriesManager;

public class NodeNetwork {
	public final Set<Node> nodes = new HashSet<>();
	public final List<SocketWire> wires = new ArrayList<>();

	public <T extends Socket<T, ?>> void connect(T from, T to) {
		if (from.getDirection() != SocketDirection.OUTPUT) throw new IllegalArgumentException("'from' socket must have output direction");
		if (to.getDirection() != SocketDirection.INPUT) throw new IllegalArgumentException("'to' socket must have input direction");

		Optional<SocketWire> opt = wires.stream().filter(v -> v.from().is(from) && v.to().is(to)).findAny();

		if (opt.isEmpty()) {
			to.addInput(from);
			wires.add(new SocketWire(from.createRef(), to.createRef()));
		}
	}

	public <T extends Socket<T, ?>> void disconnect(T from, T to) {
		if (from.getDirection() != SocketDirection.OUTPUT) throw new IllegalArgumentException("'from' socket must have output direction");
		if (to.getDirection() != SocketDirection.INPUT) throw new IllegalArgumentException("'to' socket must have input direction");

		Optional<SocketWire> opt = wires.stream().filter(v -> v.from().is(from) && v.to().is(to)).findAny();

		if (opt.isPresent()) {
			wires.remove(opt.get());
			to.removeInput(from);
		}
	}

	public Optional<Socket<?, ?>> findSocket(SocketRef ref) {
		if (ref == null) throw new NullPointerException("ref can't be null");
		return nodes.stream()
				.filter(v -> v.getNodeId().equals(ref.nodeId()))
				.flatMap(v -> v.getSockets().stream().filter(u -> u.getSocketId().equals(ref.socketId())))
				.findAny();
	}

	public Shape createShape(VoxelwrenchContext context) {
		return new SimpleMergedShape(nodes.stream()
				.filter(v -> v instanceof NetworkShapeOutputNode)
				.map(v -> ((NetworkShapeOutputNode) v).getShape(context))
				.toArray(Shape[]::new)).flat();
	}

	public void serialize(DataOutput output, RegistriesManager registries) throws IOException {
		output.writeInt(nodes.size());
		output.writeInt(wires.size());

		for (Node node : nodes) {
			output.writeUTF(node.getNodeId());
			output.writeUTF(node.getFactory().getFactoryId());

			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			DataOutputStream wrapped = new DataOutputStream(ba);
			node.serialize(wrapped, registries);

			byte[] nodeData = ba.toByteArray();
			output.writeInt(nodeData.length);
			output.write(nodeData);
		}

		for (SocketWire wire : wires) wire.serialize(output);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static NodeNetwork deserialize(DataInput input, RegistriesManager registries) throws IOException {
		NodeNetwork net = new NodeNetwork();

		int nodesCount = input.readInt();
		int wiresCount = input.readInt();

		for (int i = 0; i < nodesCount; i++) {
			String nodeId = input.readUTF();
			String factoryId = input.readUTF();

			int nodeDataSize = input.readInt();
			byte[] nodeData = new byte[nodeDataSize];
			input.readFully(nodeData);

			Optional<NodeFactory> opt = registries.getNodes().get(factoryId);

			if (opt.isPresent()) {
				DataInputStream wrapped = new DataInputStream(new ByteArrayInputStream(nodeData));
				Node node = opt.get().initializeNewNode(nodeId);
				node.deserialize(wrapped, registries);
				net.nodes.add(node);
			}
		}

		for (int i = 0; i < wiresCount; i++) {
			SocketWire wire = SocketWire.deserialize(input);
			Optional<Socket<?, ?>> fromOpt = net.findSocket(wire.from());
			Optional<Socket<?, ?>> toOpt = net.findSocket(wire.to());
			if (fromOpt.isEmpty() || toOpt.isEmpty()) continue; // TODO log warning

			((Socket) fromOpt.get()).connectTo((Socket) toOpt.get()); // TODO type checking
			net.wires.add(wire);
		}

		return net;
	}
}
