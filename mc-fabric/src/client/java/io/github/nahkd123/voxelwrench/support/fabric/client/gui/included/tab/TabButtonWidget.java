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

import io.github.nahkd123.voxelwrench.support.fabric.client.gui.AbstractWidget;
import io.github.nahkd123.voxelwrench.util.Nullable;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TabButtonWidget extends AbstractWidget {
	{ width = 80; height = 15; }
	protected Text label = Text.empty();
	protected boolean active = false;
	protected boolean hovering = false;

	public Text getLabel() {
		return label;
	}

	public void setLabel(@Nullable Text label) {
		this.label = label == null ? Text.empty() : label;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean mouseHover(int mouseX, int mouseY) {
		return hovering = super.mouseHover(mouseX, mouseY);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta, int globalX, int globalY) {
		int textWidth = renderers.getTextRenderer().getWidth(label);
		int textColor = hovering ? 0xFF5FFFFF : 0xFFFFFFFF;

		context.fill(x, y, x + width, y + height, 0x7F000000);
		context.drawText(renderers.getTextRenderer(), label, x + (width - textWidth) / 2, y + (height - renderers.getTextRenderer().fontHeight + 2) / 2, textColor, true);
		if (active) context.drawBorder(x, y, width, height, textColor);
	}
}
