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
package io.github.nahkd123.voxelwrench.support.fabric.client;

import java.util.Optional;

import io.github.nahkd123.voxelwrench.node.Node;
import io.github.nahkd123.voxelwrench.node.network.NodeNetwork;
import io.github.nahkd123.voxelwrench.util.Nullable;

/**
 * <p>Store the current client session.</p>
 */
public class ClientSession {
	private @Nullable NodeNetwork currentNetwork = null;
	private @Nullable Node selectedNode = null;

	public Optional<NodeNetwork> getCurrentNetwork() {
		return Optional.ofNullable(currentNetwork);
	}

	public void setCurrentNetwork(@Nullable NodeNetwork currentNetwork) {
		this.currentNetwork = currentNetwork;
	}

	public Optional<Node> getSelectedNode() {
		return Optional.ofNullable(selectedNode);
	}

	public void setSelectedNode(@Nullable Node selectedNode) {
		this.selectedNode = selectedNode;
	}
}
