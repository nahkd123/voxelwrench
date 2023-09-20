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
package io.github.nahkd123.voxelwrench.support.fabric.client.gui.node;

import java.util.ArrayList;
import java.util.Optional;

import org.joml.Matrix4f;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.node.network.NodeNetwork;
import io.github.nahkd123.voxelwrench.node.network.SocketWire;
import io.github.nahkd123.voxelwrench.node.socket.ParameterSocket;
import io.github.nahkd123.voxelwrench.node.socket.ShapeSocket;
import io.github.nahkd123.voxelwrench.node.socket.Socket;
import io.github.nahkd123.voxelwrench.node.socket.SocketDirection;
import io.github.nahkd123.voxelwrench.support.fabric.client.ClientSession;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.CanvasWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;

public class NodeNetworkWidget extends CanvasWidget {
	private ClientSession session;
	private int translateX = 10, translateY = 10;

	public NodeNetworkWidget(ClientSession session) {
		this.session = session;
	}

	@Override
	public void renderCanvas(DrawContext context, int mouseX, int mouseY, float delta) {
		// TODO draw grid

		if (session.getCurrentNetwork().isEmpty()) {
			context.drawText(renderers.getTextRenderer(), "Open a node network to edit...", 4, 4, 0xFFFFFF, true);
			return;
		}

		// Draw all nodes
		context.getMatrices().push();
		context.getMatrices().translate(translateX, translateY, 0);
		NodeNetwork network = session.getCurrentNetwork().get();
		for (Node node : network.nodes) {
			context.getMatrices().push();
			context.getMatrices().translate(node.getEditorX(), node.getEditorY(), 0);
			renderNode(context, mouseX - translateX, mouseY - translateY, delta, node);
			context.getMatrices().pop();
		}
		context.getMatrices().pop();

		// Draw all wires
		context.getMatrices().push();
		context.getMatrices().translate(translateX, translateY, 0);
		for (SocketWire wire : network.wires) renderWire(context, mouseX, mouseY, delta, mouseY, wire);
		context.getMatrices().pop();

		Optional<Node> selected = session.getSelectedNode();
		if (selected.isEmpty()) {
			context.drawText(renderers.getTextRenderer(), "No node selected", 4, 4, 0xFFFFFF, true);
		} else {
			context.drawText(renderers.getTextRenderer(), selected.get().getFactory().getFactoryId(), 4, 4, 0xFFFFFF, true);
			context.drawText(renderers.getTextRenderer(), selected.get().getNodeId(), 4, renderers.getTextRenderer().fontHeight, 0x7F7F7F, true);
		}
	}

	private void renderNode(DrawContext context, int mouseX, int mouseY, float delta, Node node) {
		// Node header
		context.fill(0, 0, node.getEditorWidth(), 15, 0xFF5F5F5F);
		context.drawText(renderers.getTextRenderer(), node.getFactory().getFactoryId(), 4, 4, 0xFFFFFF, false);

		// Node sockets
		context.getMatrices().push();
		int socketRow = 0;
		for (Socket<?, ?> socket : node.getSockets()) {
			context.getMatrices().translate(0, 15, 0);
			renderSocket(context, mouseX, mouseY, delta, node.getEditorWidth(), socket);
			socketRow++;
		}
		context.getMatrices().pop();

		// Node border
		context.drawBorder(0, 0, node.getEditorWidth(), 15 + socketRow * 15, 0xFFFFFFFF);
	}

	private void renderSocket(DrawContext context, int mouseX, int mouseY, float delta, int nodeWidth, Socket<?, ?> socket) {
		context.fill(0, 0, nodeWidth, 15, 0xFF1F1F1F);

		int textWidth = renderers.getTextRenderer().getWidth(socket.getSocketId());
		int socketColor = socket instanceof ParameterSocket
				? 0xFF4FCFFF
				: socket instanceof ShapeSocket ? 0xFFFFFF4F
				: 0xFF7F7F7F;

		if (socket.getDirection() == SocketDirection.INPUT) {
			context.drawText(renderers.getTextRenderer(), socket.getSocketId(), 12, 3, 0xFFFFFF, false);
			context.fill(0, 1, 8, 15, socketColor);
		}

		if (socket.getDirection() == SocketDirection.OUTPUT) {
			context.drawText(renderers.getTextRenderer(), socket.getSocketId(), nodeWidth - textWidth - 12, 3, 0xFFFFFF, false);
			context.fill(nodeWidth - 8, 1, nodeWidth, 15, socketColor);
		}
	}

	private void renderWire(DrawContext context, int mouseX, int mouseY, float delta, int nodeWidth, SocketWire wire) {
		NodeNetwork network = session.getCurrentNetwork().get();
		Matrix4f posMat = context.getMatrices().peek().getPositionMatrix();
		VertexConsumer vc = context.getVertexConsumers().getBuffer(RenderLayer.getGui());

		Optional<Socket<?, ?>> fromSocket = network.findSocket(wire.from());
		if (fromSocket.isEmpty()) return;

		Optional<Socket<?, ?>> toSocket = network.findSocket(wire.to());
		if (toSocket.isEmpty()) return;

		int fromIndex = new ArrayList<>(fromSocket.get().getNode().getSockets()).indexOf(fromSocket.get());
		int toIndex = new ArrayList<>(toSocket.get().getNode().getSockets()).indexOf(toSocket.get());

		int fromX = fromSocket.get().getNode().getEditorX() + fromSocket.get().getNode().getEditorWidth();
		int fromY = fromSocket.get().getNode().getEditorY() + (fromIndex + 1) * 15;
		int toX = toSocket.get().getNode().getEditorX();
		int toY = toSocket.get().getNode().getEditorY() + (toIndex + 1) * 15;

        vc.vertex(posMat, fromX, fromY + 7, 0).color(0xFFFFFFFF).next();
        vc.vertex(posMat, fromX, fromY + 9, 0).color(0xFFFFFFFF).next();
        vc.vertex(posMat, toX, toY + 9, 0).color(0xFFFFFFFF).next();
        vc.vertex(posMat, toX, toY + 7, 0).color(0xFFFFFFFF).next();
	}
}
