package net.op.world;

import net.op.block.Block;

public class PlainTerrainGenerator implements TerrainGenerator {

	private int tWidth = 0;
	private int tHeight = 0;
	private int tDepth = 0;

	@Override
	public void generate(Terrain terrain) {
		if (tWidth == 0
				| tHeight == 0 
				| tDepth == 0)
			return;
		
		terrain.setBlock(Block.TEST, 0, 0, 0);
	}

	@Override
	public void clip(int width, int height, int depth) {
		if (width < 0
				| height < 0
				| depth < 0)
			throw new RuntimeException("negative coordinates not allowed!");
		
		this.tWidth = width;
		this.tHeight = height;
		this.tDepth = depth;
	}


}
