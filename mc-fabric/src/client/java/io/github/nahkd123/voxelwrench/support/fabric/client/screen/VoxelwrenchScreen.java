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
package io.github.nahkd123.voxelwrench.support.fabric.client.screen;

import io.github.nahkd123.voxelwrench.support.fabric.client.ClientSession;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.WidgetScreen;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.tab.Tab;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.tab.TabsWidget;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.layout.FillLayout;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.node.NodeNetworkWidget;
import io.github.nahkd123.voxelwrench.util.Scope;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class VoxelwrenchScreen extends WidgetScreen {
	private ClientSession session;

	public VoxelwrenchScreen(Text title, ClientSession session) {
		super(title);
		this.session = session;

		TabsWidget tabs = new TabsWidget();
		root.getChildren().add(tabs);
		root.getLayouts().add(Scope.wrap(new FillLayout(), layout -> {
			layout.addTarget(tabs);
			layout.getMargin()[0] = 4;
			layout.getMargin()[1] = 4;
			layout.getMargin()[2] = 4;
			layout.getMargin()[3] = 4;
		}));

		// TODO add text: "Click on handle to edit value"
		Tab interact = new Tab(tabs, Text.literal("Interact"));
		interact.content.setBackground(0x00000000);
		tabs.addTab(interact);

		Tab edit = new Tab(tabs, Text.literal("Edit"));
		NodeNetworkWidget net = new NodeNetworkWidget(session);
		edit.content.getChildren().add(net);
		edit.content.getLayouts().add(new FillLayout().addTarget(net));
		tabs.addTab(edit);

		Tab about = new Tab(tabs, Text.literal("About"));
		tabs.addTab(about);

		edit.switchToThis();
	}

	@Override
	public boolean shouldPause() {
		return false; // Should we allow player to pause?
	}
}
