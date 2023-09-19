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

import net.minecraft.client.gui.DrawContext;

// TODO move this Nahara's Modkit and call it "Nahara's Modkit UI Framework" ;)
public interface Widget {
	public boolean isVisible();
	public void setVisible(boolean visible);

	public int getX();
	public void setX(int x);

	public int getY();
	public void setY(int y);

	public int getWidth();
	public void setWidth(int width);

	public int getHeight();
	public void setHeight(int height);

	public void useRenderers(Renderers renderers);
	public Renderers getRenderers();
	public void render(DrawContext context, int mouseX, int mouseY, float delta, int globalX, int globalY);

	default boolean mouseTest(int mouseX, int mouseY) {
		if (!isVisible()) return false;
		return mouseX >= getX() && mouseX < getX() + getWidth() && mouseY >= getY() && mouseY < getY() + getHeight();
	}

	// Events
	default boolean mouseHover(int mouseX, int mouseY) {
		return mouseTest(mouseX, mouseY);
	}

	default boolean mouseDown(int mouseX, int mouseY, int button) {
		return mouseTest(mouseX, mouseY);
	}

	default boolean mouseUp(int mouseX, int mouseY, int button) {
		return mouseTest(mouseX, mouseY);
	}
}
