package net.opencraft.world;

import static net.opencraft.client.renderer.CubeRenderer.renderTerrain;

import java.io.Serializable;

import org.josl.d3util.Vertex3f;

public /* abstract */ class Block implements Serializable {

	private static final long serialVersionUID = 5030544150367015185L;

	final byte id;
	float x;
	float y;
	float z;

	public Block(final int id) {
		this.id = (byte) (id & 0xFF);
	}

	public Block(final int id, float x, float y, float z) {
		this(id);
		
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Block(final int id, Vertex3f pos) {
		this(id, pos.x, pos.y, pos.z);
	}

	public Vertex3f position() {
		return new Vertex3f().set(x, y, z);
	}

	public int getId() {
		return id;
	}

	public void render(Chunk chunk) {
		renderTerrain(chunk, position(), 1f, 0xFFB800);
	}

	public float getSize() {
		return 1.0f;
	}
}
