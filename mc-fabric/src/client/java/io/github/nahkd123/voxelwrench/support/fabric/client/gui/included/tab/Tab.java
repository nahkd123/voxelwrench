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

import io.github.nahkd123.voxelwrench.support.fabric.client.gui.included.ContainerWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class Tab {
	private TabsWidget parent;
	public final TabButtonWidget button;
	public final ContainerWidget content;

	public Tab(TabsWidget parent, Text label) {
		this.parent = parent;
		button = new TabButtonWidget() {
			@Override
			public boolean mouseDown(int mouseX, int mouseY, int button) {
				if (super.mouseDown(mouseX, mouseY, button)) {
					switchToThis();
					MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master((RegistryEntry<SoundEvent>)SoundEvents.UI_BUTTON_CLICK, 1.0f));
					return true;
				} else {
					return false;
				}
			}
		};
		button.setLabel(label);

		content = new ContainerWidget();
		content.setBackground(0x7f000000);
		content.setVisible(false);
	}

	public boolean isVisible() {
		return button.isActive();
	}

	public void setVisible(boolean visible) {
		button.setActive(visible);
		content.setVisible(visible);
	}

	public void switchToThis() {
		parent.switchTo(this);
	}
}
