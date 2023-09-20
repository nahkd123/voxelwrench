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
package io.github.nahkd123.voxelwrench.support.fabric;

import org.lwjgl.glfw.GLFW;

import io.github.nahkd123.voxelwrench.node.included.CuboidNode;
import io.github.nahkd123.voxelwrench.node.network.NetworkShapeOutputNode;
import io.github.nahkd123.voxelwrench.node.network.NodeNetwork;
import io.github.nahkd123.voxelwrench.pattern.NamespacedPattern;
import io.github.nahkd123.voxelwrench.support.fabric.client.ClientSession;
import io.github.nahkd123.voxelwrench.support.fabric.client.screen.VoxelwrenchScreen;
import io.github.nahkd123.voxelwrench.util.blockpos.MutableBlockPos;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class VoxelwrenchModClient implements ClientModInitializer {
	private KeyBinding keybind;
	private ClientSession session;

	@Override
	public void onInitializeClient() {
		keybind = new KeyBinding("key.voxelwrench.editor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "category.voxelwrench.main");

		ClientPlayConnectionEvents.INIT.register((handler, client) -> {
			// TODO hook to client join event in main thread instead
			client.execute(() -> {
				VoxelwrenchMod.LOGGER.info("Setting up Voxelwrench client session...");
				session = new ClientSession();

				NodeNetwork net = new NodeNetwork();

				// Make a basic network
				CuboidNode cuboid = new CuboidNode("node000");
				net.nodes.add(cuboid);

				NetworkShapeOutputNode output = new NetworkShapeOutputNode("node001");
				output.setEditorX(200);
				net.nodes.add(output);

				cuboid.pos1Input.setControlledValue(new MutableBlockPos(-5, -5, -5));
				cuboid.pos2Input.setControlledValue(new MutableBlockPos(5, 5, 5));
				cuboid.patternInput.setControlledValue(new NamespacedPattern("minecraft", "diamond_block"));
				net.connect(cuboid.shapeOutput, output.shapeInput);

				session.setCurrentNetwork(net);
			});
		});

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			client.execute(() -> {
				if (client.player.isCreativeLevelTwoOp()) {
					client.inGameHud.getChatHud().addMessage(Text.translatableWithFallback("voxelwrench.chat.welcome", "Welcome to Voxelwrench!"));
					client.inGameHud.getChatHud().addMessage(Text.translatableWithFallback("voxelwrench.chat.welcome.0", "Press %s to open Voxelwrench editor", Text.keybind("key.voxelwrench.editor")));
				}
			});
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keybind.wasPressed()) {
				while (keybind.wasPressed()) {}
				openEditor(client);
			}
		});
	}

	private void openEditor(MinecraftClient client) {
		if (!client.player.isCreativeLevelTwoOp()) return;
		client.inGameHud.getChatHud().addMessage(Text.literal("debug: open editor"));
		client.setScreen(new VoxelwrenchScreen(Text.literal("hi"), session));
	}
}
