package net.op.world;

import net.op.block.Block;

public final class World implements Terrain {

	byte[] blocks;
	
	private final int width;
	private final int height;
	private final int depth;
	
	public World(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		this.blocks = new byte[width * height * depth];
	}
	
	public World(int w, int h, int d, TerrainGenerator terrainGen) {
		this(w, h, d);
		generate(terrainGen);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		return depth;
	}
	
	@Override
	public void setBlock(byte blockId, int x, int y, int z) {
		try {
			int index = x + y * width + z * width * height;
			this.blocks[index] = blockId;
		} catch (Exception ignored) {
			System.err.printf("WARN: block out of bounds x=%d,y=%d,z=%d\n", x, y, z);
		}
	}

	@Override
	public void setBlock(Block block, int x, int y, int z) {
		this.setBlock(block.id, x, y, z);	
	}	

	@Override
	public byte getBlockId(int x, int y, int z) {
		int index = x + y * width + z * width * height;
		if (index < 0)
			return 0;
		
		return this.blocks[index];
	}
	
	@Override
	public Block getBlock(int x, int y, int z) {
		return Block.BLOCKS.get(getBlockId(x, y, z));
	}
	
	public void generate(TerrainGenerator terrainGen) {
		terrainGen.generate(this);
	}	
}
