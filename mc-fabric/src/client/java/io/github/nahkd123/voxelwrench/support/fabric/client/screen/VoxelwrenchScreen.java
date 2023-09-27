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

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.nahkd123.voxelwrench.node.network.NodeNetwork;
import io.github.nahkd123.voxelwrench.node.param.InputParameter;
import io.github.nahkd123.voxelwrench.node.param.Parameter;
import io.github.nahkd123.voxelwrench.support.fabric.client.ClientSession;
import io.github.nahkd123.voxelwrench.support.fabric.client.render.ApplicableToWorldRendering;
import io.github.nahkd123.voxelwrench.support.fabric.client.render.VoxelwrenchWorldRenderer;
import io.github.nahkd123.voxelwrench.support.fabric.client.screen.parameter.ParameterControlFactory;
import io.github.nahkd123.voxelwrench.util.blockpos.BlockPos;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import nahara.modkit.gui.v1.NaharaScreen;
import nahara.modkit.gui.v1.layout.Axis;
import nahara.modkit.gui.v1.widget.included.Box;
import nahara.modkit.gui.v1.widget.included.Button;
import nahara.modkit.gui.v1.widget.included.DrawableContainer;
import nahara.modkit.gui.v1.widget.included.FlowContainer;
import nahara.modkit.gui.v1.widget.included.Label;
import nahara.modkit.gui.v1.widget.included.ScrollProxy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class VoxelwrenchScreen extends NaharaScreen implements ApplicableToWorldRendering {
	private ClientSession session;

	@SuppressWarnings("unchecked")
	public VoxelwrenchScreen(Text title, ClientSession session) {
		super(title);
		this.session = session;

		// We'll create "Interact" screen first
		add(new FlowContainer().x(4).y(4).height(14).add(
				// Interact: Edit parameters by interacting the world or controls
				new Button().label(Text.literal("Interact")).width(50).height(14).pressed(true),
				FlowContainer.spacer(2),

				// Node: The "geometry node" editor
				new Button().label(Text.literal("Node")).width(50).height(14).pressed(false),
				FlowContainer.spacer(2),

				// Explore: Find and search for templates
				// It will send GET request to http://<server>/explore?q=<search query>
				// The template can be obtained by sending GET to https://<server>/template/<template hash>.<format>
				// (format can be vwt or schem)
				// Downloaded templates can be used inside node editor or use it directly like schematics
				new Button().label(Text.literal("Explore")).width(50).height(14).pressed(false),
				FlowContainer.spacer(2),

				// About: More information about Voxelwrench
				new Button().label(Text.literal("About")).width(50).height(14).pressed(false),
				FlowContainer.spacer(2)
				));

		FlowContainer controls = new FlowContainer().flowAxis(Axis.Y);
		add(new DrawableContainer().x(4).y(20).width(60).height(200).add(
				new Box().x(0).y(0).layout(l -> l.setFillAxes(Axis.X, Axis.Y)).backgroundColor(0x7F000000),
				new ScrollProxy(controls.x(0).y(0).width(60)).x(0).y(0).layout(l -> l.setFillAxes(Axis.X, Axis.Y))
				));

		if (session.getCurrentNetwork().isPresent()) {
			NodeNetwork network = session.getCurrentNetwork().get();

			for (Parameter<?> param : network.getInputs()) {
				controls.add(
						FlowContainer.spacer(4),
						new Label().text(Text.literal(param.getNode().getClass().getSimpleName())).x(2).width(56).height(8),
						new Label().text(Text.literal(param.getParameterId())).x(2).width(56).height(8).layout(l -> l.hasFillAxis(Axis.X)),
						FlowContainer.spacer(2));

				if (param.getValue() instanceof BlockPos) {
					controls.add(ParameterControlFactory.BLOCK_POS.createParameter((InputParameter<BlockPos>) param));
				} else {
					controls.add(new Label()
							.text(Text.literal("(can't modify)"))
							.height(8)
							.layout(l -> {}));
				}

				controls.add(FlowContainer.spacer(2));
			}
		}

		debugging = true;
	}

	@Override
	public boolean shouldPause() {
		return false; // Should we allow player to pause?
	}
	
	@Override
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
		if (client.world != null) return; // Don't render background
		super.renderBackground(context, mouseX, mouseY, delta);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
		else {
			// Input passthrough: Allow player to move with WASD when this screen is opened
			KeyBinding[] movementKeys = {
					client.options.forwardKey,
					client.options.backKey,
					client.options.leftKey,
					client.options.rightKey,
					client.options.sprintKey,
					client.options.jumpKey,
					client.options.sneakKey,
					client.options.togglePerspectiveKey,
			};

			for (KeyBinding kb : movementKeys) {
				if (kb.matchesKey(keyCode, scanCode)) {
					kb.setPressed(true);
					break;
				}
			}

			return false;
		}
	}

	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (super.keyReleased(keyCode, scanCode, modifiers)) return true;
		else {
			// Input passthrough: Allow player to move with WASD when this screen is opened
			KeyBinding[] movementKeys = {
					client.options.forwardKey,
					client.options.backKey,
					client.options.leftKey,
					client.options.rightKey,
					client.options.sprintKey,
					client.options.jumpKey,
					client.options.sneakKey,
			};

			for (KeyBinding kb : movementKeys) {
				if (kb.matchesKey(keyCode, scanCode)) {
					kb.setPressed(false);
					break;
				}
			}

			return false;
		}
	}

	@Override
	public void worldRenderAfter(WorldRenderContext context) {
		Camera camera = context.camera();
		float[][] colorSets = {
				{ 1f, 0f, 0f },
				{ 0f, 1f, 0f },
				{ 0f, 0f, 1f },
				{ 1f, 1f, 0f },
				{ 0f, 1f, 1f },
				{ 1f, 0f, 1f },
		};

		if (session.getCurrentNetwork().isPresent()) {
			NodeNetwork network = session.getCurrentNetwork().get();
			for (int i = 0; i < network.getInputs().size(); i++) {
				float[] colors = colorSets[i % colorSets.length];
				InputParameter<?> param = network.getInputs().get(i);

				if (param.getValue() instanceof BlockPos bp) {
					context.matrixStack().push();
					context.matrixStack().translate(bp.getX(), bp.getY(), bp.getZ());
					context.matrixStack().translate(0.5 - camera.getPos().x, 0.5 - camera.getPos().y, 0.5 - camera.getPos().z);
					RenderSystem.enableBlend();
					RenderSystem.disableDepthTest();

					context.matrixStack().push();
					context.matrixStack().scale(1.01f, 1.01f, 1.01f);
					VoxelwrenchWorldRenderer.block(context.matrixStack(), colors[0], colors[1], colors[2], 0.5f);
					context.matrixStack().pop();

					context.matrixStack().push();
					context.matrixStack().multiply(camera.getRotation());
					float distanceToBlock = -Math.max(camera.getPos().toVector3f().distance(bp.getX(), bp.getY(), bp.getZ()), 1f) / 200f;
					context.matrixStack().scale(distanceToBlock, distanceToBlock, distanceToBlock);

					String nodeName = param.getNode().getClass().getSimpleName();
					int nodeNameLength = client.textRenderer.getWidth(nodeName);
					client.textRenderer.draw(nodeName, -nodeNameLength / 2, -16f, 0xFFFFFF, false, context.matrixStack().peek().getPositionMatrix(), context.consumers(), TextLayerType.SEE_THROUGH, 0x00000000, LightmapTextureManager.pack(15, 15));

					String paramName = param.getParameterId();
					int paramNameLength = client.textRenderer.getWidth(paramName);
					client.textRenderer.draw(paramName, -paramNameLength / 2, -8f, 0xFFFFFF, false, context.matrixStack().peek().getPositionMatrix(), context.consumers(), TextLayerType.SEE_THROUGH, 0x00000000, LightmapTextureManager.pack(15, 15));

					String value = bp.toString();
					int valueLength = client.textRenderer.getWidth(value);
					client.textRenderer.draw(value, -valueLength / 2, 0f, 0xFFFFFF, false, context.matrixStack().peek().getPositionMatrix(), context.consumers(), TextLayerType.SEE_THROUGH, 0x00000000, LightmapTextureManager.pack(15, 15));

					context.matrixStack().pop();

					RenderSystem.disableBlend();
					context.matrixStack().pop();
				}
			}
		}
	}
}
