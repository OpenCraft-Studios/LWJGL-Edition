package net.opencraft.world.terrain;

import net.opencraft.world.Block;

public class Terrain {

	private Block[] blocks;
	
	public Terrain(Block[] blocks) {
		this.blocks = blocks;
	}
	
	public static Terrain of(Block[] cubes) {
		return new Terrain(cubes);
	}

	public Block[] getBlocks() {
		return blocks;
	}
	
	@Override
	public String toString() {
		return "Terrain(" + blocks.length + " blocks, 0 entities)";
	}

	
}
