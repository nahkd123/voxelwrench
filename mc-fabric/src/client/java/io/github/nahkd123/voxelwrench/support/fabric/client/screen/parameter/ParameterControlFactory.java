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
package io.github.nahkd123.voxelwrench.support.fabric.client.screen.parameter;

import java.util.function.Consumer;

import io.github.nahkd123.voxelwrench.node.param.InputParameter;
import io.github.nahkd123.voxelwrench.support.fabric.util.Holder;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import nahara.modkit.gui.v1.widget.included.Button;
import nahara.modkit.gui.v1.widget.included.DrawableContainer;
import nahara.modkit.gui.v1.widget.included.Label;
import nahara.modkit.gui.v1.widget.included.Textbox;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public interface ParameterControlFactory<T> {
	/**
	 * <p>Initialize controls.</p>
	 * @param initial The initial value.
	 * @param container The drawable container where you can add drawable controls to it.
	 * @param updater The consumer that you can use to set new value.
	 */
	public void initialize(T initial, DrawableContainer container, Consumer<T> updater);

	default DrawableContainer create(T initial, Consumer<T> updater) {
		DrawableContainer container = new DrawableContainer();
		initialize(initial, container, updater);
		return container;
	}

	default DrawableContainer createParameter(InputParameter<T> param) {
		return create(param.getValue(), param::setValue);
	}

	/**
	 * <p>Create drawable control that's not attached to updating function.</p>
	 * @param initial Initial value.
	 * @return The control.
	 */
	default DrawableContainer createDetached(T initial) {
		Holder<T> holder = new Holder<>(initial);
		return create(initial, holder::setValue);
	}

	public static final ParameterControlFactory<BlockPos> BLOCK_POS = (initial, container, updater) -> {
		MutableBlockPos internal = initial.copyAsBlockPos();
		Label lx, ly, lz;
		Textbox tx, ty, tz;

		container.width(60).height(75).add(
				lx = new Label().text(Text.literal("X")).x(2).y(3).width(8).height(14),
				tx = new Textbox() {
					@Override
					public void setContent(String content) {
						super.setContent(content);
						try {
							internal.setX(Integer.parseInt(content));
							updater.accept(internal);
							lx.color(0xFFFFFF);
						} catch (NumberFormatException e) {
							// NO-OP
							// TODO make the textbox red
							lx.color(0xFF0000);
						}
					}
				}.content(Integer.toString(initial.getX())).x(10).y(0).width(48).height(14),

				ly = new Label().text(Text.literal("Y")).x(2).y(18).width(8).height(14),
				ty = new Textbox() {
					@Override
					public void setContent(String content) {
						super.setContent(content);
						try {
							internal.setY(Integer.parseInt(content));
							updater.accept(internal);
							ly.color(0xFFFFFF);
						} catch (NumberFormatException e) {
							// NO-OP
							// TODO make the textbox red
							ly.color(0xFF0000);
						}
					}
				}.content(Integer.toString(initial.getY())).x(10).y(15).width(48).height(14),

				lz = new Label().text(Text.literal("Z")).x(2).y(33).width(8).height(14),
				tz = new Textbox() {
					@Override
					public void setContent(String content) {
						super.setContent(content);
						try {
							internal.setZ(Integer.parseInt(content));
							updater.accept(internal);
							lz.color(0xFFFFFF);
						} catch (NumberFormatException e) {
							// NO-OP
							// TODO make the textbox red
							lz.color(0xFF0000);
						}
					}
				}.content(Integer.toString(initial.getZ())).x(10).y(30).width(48).height(14),

				new Button() {
					@SuppressWarnings("resource")
					@Override
				 	public boolean onMouseDown(float mouseX, float mouseY, float delta, int button) {
				 		if (super.onMouseDown(mouseX, mouseY, delta, button)) {
				 			if (manager.getClient().player != null) {
				 				net.minecraft.util.math.BlockPos clientPos = manager.getClient().player.getBlockPos();
				 				tx.setContent(Integer.toString(clientPos.getX()));
				 				ty.setContent(Integer.toString(clientPos.getY()));
				 				tz.setContent(Integer.toString(clientPos.getZ()));
				 			}
				 			return true;
				 		} else {
				 			return false;
				 		}
				 	}
				}.label(Text.literal("Current")).x(0).y(45).width(60).height(14),

				new Button() {
					@SuppressWarnings("resource")
					@Override
				 	public boolean onMouseDown(float mouseX, float mouseY, float delta, int button) {
				 		if (super.onMouseDown(mouseX, mouseY, delta, button)) {
				 			if (manager.getClient().player != null) {
				 				HitResult hr = manager.getClient().player.raycast(8d, delta, false);
				 				if (hr instanceof BlockHitResult bhr) {
					 				tx.setContent(Integer.toString(bhr.getBlockPos().getX()));
					 				ty.setContent(Integer.toString(bhr.getBlockPos().getY()));
					 				tz.setContent(Integer.toString(bhr.getBlockPos().getZ()));
				 				} else {
					 				tx.setContent(Integer.toString((int) Math.floor(hr.getPos().x)));
					 				ty.setContent(Integer.toString((int) Math.floor(hr.getPos().y)));
					 				tz.setContent(Integer.toString((int) Math.floor(hr.getPos().z)));
					 			}
				 			}
				 			return true;
				 		} else {
				 			return false;
				 		}
				 	}
				}.label(Text.literal("Crosshair")).x(0).y(60).width(60).height(14)
				);
	};
}
