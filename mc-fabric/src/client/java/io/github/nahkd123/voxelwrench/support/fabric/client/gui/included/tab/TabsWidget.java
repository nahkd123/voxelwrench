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
package io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.tab;

import java.util.ArrayList;
import java.util.List;

import io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.ContainerWidget;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.layout.FlowLayout;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.layout.FlowLayout.Direction;
import net.minecraft.client.gui.DrawContext;

public class TabsWidget extends ContainerWidget {
	{ width = 200; height = 200; }
	protected List<Tab> tabs = new ArrayList<>();

	protected ContainerWidget tabButtons = new ContainerWidget(); {
		tabButtons.setHeight(15);
		tabButtons.getLayouts().add(new FlowLayout(FlowLayout.Axis.X, Direction.CENTER));
	}

	public TabsWidget() {
		children.add(tabButtons);
	}

	public void addTab(Tab tab) {
		tabs.add(tab);
		tabButtons.getChildren().add(tab.button);
		children.add(tab.content);
		switchTo(tab);
	}

	public void switchTo(Tab tab) {
		for (Tab existing : tabs) existing.setVisible(false);
		tab.setVisible(true);
		ensureVisible(tab);
	}

	public void ensureVisible(Tab tab) {
		tab.content.setX(0);
		tab.content.setY(15);
		tab.content.setWidth(width);
		tab.content.setHeight(height - 15);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta, int globalX, int globalY) {
		tabButtons.setWidth(width);

		if (tabs.size() > 0) {
			for (Tab tab : tabs) if (tab.isVisible()) {
				ensureVisible(tab);
				break;
			}
		}

		super.render(context, mouseX, mouseY, delta, globalX, globalY);
	}
}
