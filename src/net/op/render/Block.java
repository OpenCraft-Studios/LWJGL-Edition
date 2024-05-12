package net.op.render;

import static org.lwjgl.opengl.GL11.glTranslatef;

import org.joml.Vector3f;

import net.op.render.model.CubeModel;

public final class Block {

	private final CubeModel model;
	public final byte id;

	public Block(final int id, final CubeModel model) {
		this.model = model;
		this.id = (byte) (id & 0xFF);
	}

	public Block(final int id) {
		this(id, CubeModel.COLORIZED);
	}

	public void render() {
		if (model == null)
			return;

		model.render();
	}

	public void render(float x, float y, float z) {
		glTranslatef(x, y, z);
		render();
	}

	public void render(Vector3f pos) {
		this.render(pos.x, pos.y, pos.z);
	}

}
