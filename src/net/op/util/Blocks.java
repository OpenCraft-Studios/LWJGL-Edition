package net.op.util;

import net.op.render.Block;
import net.op.render.model.CubeModel;

public class Blocks {

	public static final Block AIR = new Block(0, CubeModel.INVISIBLE);
	public static final Block TEST = new Block(1, CubeModel.COLORIZED);
	
	private Blocks() {
	}
	
	public static Block find(byte id) {
		if (id == 0)
			return Blocks.AIR;
		
		return null;
	}
}
