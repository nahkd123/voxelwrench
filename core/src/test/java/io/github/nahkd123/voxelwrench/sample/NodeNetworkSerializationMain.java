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
package io.github.nahkd123.voxelwrench.sample;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.node.included.CuboidNode;
import io.github.nahkd123.voxelwrench.node.included.ScatterNode;
import io.github.nahkd123.voxelwrench.node.network.NetworkShapeOutputNode;
import io.github.nahkd123.voxelwrench.node.network.NodeNetwork;
import io.github.nahkd123.voxelwrench.node.socket.ControllableSocket;
import io.github.nahkd123.voxelwrench.node.socket.Socket;
import io.github.nahkd123.voxelwrench.pattern.NamespacedPattern;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import io.github.nahkd123.voxelwrench.util.registry.GlobalRegistries;

/**
 * <p>Serialize and deserialize node network example.</p>
 * <p>Example use case: sending node network from client to Minecraft server and apply changes on the server.</p>
 */
public class NodeNetworkSerializationMain {
	public static void main(String[] args) throws IOException {
		NodeNetwork net = new NodeNetwork();

		CuboidNode cuboid = new CuboidNode("node000");
		cuboid.pos1Input.setControlledValue(new MutableBlockPos(-5, -5, -5));
		cuboid.pos2Input.setControlledValue(new MutableBlockPos(5, 5, 5));
		cuboid.patternInput.setControlledValue(new NamespacedPattern("minecraft", "stone"));

		ScatterNode scatter = new ScatterNode("node001");
		scatter.scatterRateInput.setControlledValue(0.2);

		NetworkShapeOutputNode output = new NetworkShapeOutputNode("node002");

		net.nodes.add(cuboid);
		net.nodes.add(scatter);
		net.nodes.add(output);
		net.connect(cuboid.shapeOutput, scatter.volumeInput);
		net.connect(scatter.shapeOutput, output.shapeInput);

		File file = new File("test/node_network_serialization.bin");
		new File(file, "..").mkdirs();
		if (file.exists()) file.delete();

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
			System.out.println("Serializing...");
			net.serialize(raf, GlobalRegistries.REGISTRIES);

			System.out.println("Deserializing...");
			raf.seek(0);
			NodeNetwork net2 = NodeNetwork.deserialize(raf, GlobalRegistries.REGISTRIES);

			System.out.println("--- Node network");
			System.out.println("   --- HEADER");
			System.out.println("    - Nodes: " + net.nodes.size());
			System.out.println("    - Wires: " + net.wires.size());
			System.out.println("   --- END OF HEADER");
			System.out.println("   --- NODES");
			net2.nodes.forEach(node -> dumpNodeToStdout(node));
			System.out.println("   --- END OF NODES");
			System.out.println("   --- WIRES");
			net2.wires.forEach(wire -> System.out.println("      Wire '" + wire.from().nodeId() + "/" + wire.from().socketId() + "' => '" + wire.to().nodeId() + "/" + wire.to().socketId() + "'"));
			System.out.println("   --- END OF WIRES");
			System.out.println("--- END OF NETWORK");
		}
	}

	public static void dumpNodeToStdout(Node node) {
		Collection<Socket<?, ?>> sockets = node.getSockets();

		System.out.println("      --- Node information - " + node.getNodeId() + " (Type = " + node.getFactory().getFactoryId() + ")");
		System.out.println("       - Sockets (" + sockets.size() + "):");

		for (Socket<?, ?> socket : sockets) {
			String valueDisp = socket instanceof ControllableSocket<?, ?> cs
					? (": " + cs.getControlledValue().toString())
					: "";
			System.out.println("        + " + socket.getDirection() + ": " + socket.getSocketId() + valueDisp);
		}

		System.out.println("      --- END OF NODE");
	}
}
