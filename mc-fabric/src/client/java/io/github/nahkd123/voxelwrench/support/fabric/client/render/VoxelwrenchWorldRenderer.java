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
package io.github.nahkd123.voxelwrench.support.fabric.client.render;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;

public class VoxelwrenchWorldRenderer {
	public static void block(MatrixStack matrices, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		buffer.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		Matrix4f mat = matrices.peek().getPositionMatrix();

		buffer.vertex(mat, 0.5f, -0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, -0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, -0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, -0.5f, -0.5f).color(r, g, b, a).next();

		buffer.vertex(mat, -0.5f, 0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, 0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, 0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, 0.5f, -0.5f).color(r, g, b, a).next();

		buffer.vertex(mat, -0.5f, -0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, 0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, 0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, -0.5f, -0.5f).color(r, g, b, a).next();

		buffer.vertex(mat, 0.5f, -0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, 0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, 0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, -0.5f, 0.5f).color(r, g, b, a).next();

		buffer.vertex(mat, 0.5f, 0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, 0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, -0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, 0.5f, -0.5f, -0.5f).color(r, g, b, a).next();

		buffer.vertex(mat, -0.5f, -0.5f, -0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, -0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, 0.5f, 0.5f).color(r, g, b, a).next();
		buffer.vertex(mat, -0.5f, 0.5f, -0.5f).color(r, g, b, a).next();

		tessellator.draw();
	}
}
