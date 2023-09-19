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
package io.github.nahkd123.voxelwrench.support.fabric.client.gui.layout;

import java.util.List;

import io.github.nahkd123.voxelwrench.support.fabric.client.gui.Widget;

public class FlowLayout implements Layout {
	private Axis axis;
	private Direction direction;

	public enum Axis {
		X,
		Y;
	}

	public enum Direction {
		FIRST,
		CENTER,
		LAST;
	}

	public FlowLayout(Axis axis, Direction direction) {
		this.axis = axis;
		this.direction = direction;
	}

	public Axis getAxis() {
		return axis;
	}

	public void setAxis(Axis axis) {
		this.axis = axis;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void applyLayout(List<Widget> children, int width, int height) {
		int current = 0;
		Direction dir = this.direction;

		if (this.direction == Direction.CENTER) {
			dir = Direction.FIRST;
			int childrenSize = 0;

			for (Widget child : children) {
				if (!child.isVisible()) continue;

				switch (axis) {
				case X: childrenSize += child.getWidth(); break;
				case Y: childrenSize += child.getHeight(); break;
				}
			}

			current = (width - childrenSize) / 2;
		} else if (this.direction == Direction.LAST) {
			current = width;
		}

		for (Widget child : children) {
			if (!child.isVisible()) continue;

			if (dir == Direction.FIRST) {
				switch (axis) {
				case X: child.setX(current); current += child.getWidth(); break;
				case Y: child.setY(current); current += child.getHeight(); break;
				}
			} else if (dir == Direction.LAST) {
				switch (axis) {
				case X: child.setX(current - child.getWidth()); current -= child.getWidth(); break;
				case Y: child.setY(current - child.getHeight()); current -= child.getHeight(); break;
				}
			}
		}

		// TODO overflow fix?
	}
}
