package net.op.block;

import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import net.op.render.model.CubeModel;

public class Block {
	
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final Block AIR = new Block(0, CubeModel.INVISIBLE);
	public static final Block TEST = new Block(1, CubeModel.COLORIZED);
	
	private final CubeModel model;
	public final byte id;

	protected Block(final int id, final CubeModel model) {
		this.model = model;
		this.id = (byte) (id & 0xFF);
		
		Block.BLOCKS.add(id, this);
	}
	
	protected Block(final CubeModel model) {
		this.model = model;
		
		Block.BLOCKS.add(this);
		id = (byte) Block.BLOCKS.indexOf(this);
	}

	protected Block(final int id) {
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
	
	public CubeModel getModel() {
		return model;
	}
	
	public void update() {	
	}
	
	public boolean isSolid() {
		return true;
	}

	public void render(Vector3f pos) {
		this.render(pos.x, pos.y, pos.z);
	}
	
	@Override
	public String toString() {
		return "Block(" + id + ")";
	}

}