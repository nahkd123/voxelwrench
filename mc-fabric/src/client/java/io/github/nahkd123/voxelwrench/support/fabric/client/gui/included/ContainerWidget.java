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
package io.github.nahkd123.voxelwrench.support.fabric.client.gui.included;

import java.util.ArrayList;
import java.util.List;

import io.github.nahkd123.voxelwrench.support.fabric.client.gui.AbstractWidget;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.ParentWidget;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.Widget;
import io.github.nahkd123.voxelwrench.support.fabric.client.gui.layout.Layout;
import net.minecraft.client.gui.DrawContext;

public class ContainerWidget extends AbstractWidget implements ParentWidget {
	protected List<Widget> children = new ArrayList<>(); // TODO mouse handling: backward
	protected List<Layout> layouts = new ArrayList<>();
	protected int background = 0x00000000;
	protected boolean usingScissor = true;
	protected int translateX = 0, translateY = 0;

	public List<Layout> getLayouts() {
		return layouts;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public boolean isUsingScissor() {
		return usingScissor;
	}

	public void setUsingScissor(boolean usingScissor) {
		this.usingScissor = usingScissor;
	}

	public int getTranslateX() {
		return translateX;
	}

	public void setTranslateX(int translateX) {
		this.translateX = translateX;
	}

	public int getTranslateY() {
		return translateY;
	}

	public void setTranslateY(int translateY) {
		this.translateY = translateY;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta, int globalX, int globalY) {
		context.getMatrices().push();
		context.getMatrices().translate(x + translateX, y + translateY, 0);

		if (usingScissor) {
			context.enableScissor(globalX + x, globalY + y, globalX + x + width, globalY + y + height);
		}

		context.fill(0, 0, width, height, background);
		for (Layout layout : layouts) layout.applyLayout(children, width, height);

		for (Widget child : children) {
			if (!child.isVisible()) continue;
			child.useRenderers(getRenderers());
			child.render(context, mouseX, mouseY, delta, globalX + x, globalY + y);
		}

		if (usingScissor) context.disableScissor();
		context.getMatrices().pop();
	}

	@Override
	public List<Widget> getChildren() {
		return children;
	}
}
