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

import java.util.function.Function;

import io.github.nahkd123.voxelwrench.util.Nullable;
import io.github.nahkd123.voxelwrench.util.registry.Registerable;

public class NodeFactory implements Registerable {
	private String factoryId;
	private Function<String, Node> initializer;
	private NodeCategory category;

	public NodeFactory(String factoryId, Function<String, Node> initializer, @Nullable NodeCategory category) {
		if (factoryId == null) throw new NullPointerException("factoryId can't be null");
		this.factoryId = factoryId;

		if (initializer == null) throw new NullPointerException("initializer can't be null");
		this.initializer = initializer;

		if (category == null) category = NodeCategory.UNCATEGORIZED;
		this.category = category;
	}

	public NodeFactory(String factoryId, Function<String, Node> initializer) {
		this(factoryId, initializer, null);
	}

	public String getFactoryId() {
		return factoryId;
	}

	public NodeCategory getCategory() {
		return category;
	}

	/**
	 * <p>Initialize a fresh new node.</p>
	 * @param nodeId The ID of the node that will be created. Will never be {@code null}.
	 * @return The fresh new node.
	 */
	public Node initializeNewNode(String nodeId) {
		return initializer.apply(nodeId);
	}

	@Override
	public String getRegistryId() {
		return getFactoryId();
	}
}
