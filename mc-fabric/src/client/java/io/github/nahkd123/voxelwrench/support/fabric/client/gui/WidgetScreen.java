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
package io.github.nahkd123.voxelwrench.support.fabric.client.gui;

import io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.ContainerWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WidgetScreen extends Screen implements Renderers {
	protected final ContainerWidget root;

	public WidgetScreen(Text title) {
		super(title);
		root = new ContainerWidget();
	}

	@Override
	protected void init() {
		root.x = 0;
		root.y = 0;
		root.width = width;
		root.height = height;
	}

	public ContainerWidget getRoot() {
		return root;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		root.useRenderers(this);
		root.mouseHover(mouseX, mouseY);
		root.render(context, mouseX, mouseY, delta, 0, 0);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		root.useRenderers(this);
		return root.mouseDown((int) Math.round(mouseX), (int) Math.round(mouseY), button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		root.useRenderers(this);
		return root.mouseUp((int) Math.round(mouseX), (int) Math.round(mouseY), button);
	}

	@Override
	public TextRenderer getTextRenderer() {
		return textRenderer;
	}
}
