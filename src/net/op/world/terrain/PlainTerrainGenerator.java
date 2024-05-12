package net.op.world.terrain;

import net.op.util.Blocks;

public class PlainTerrainGenerator extends TerrainGenerator {

	public PlainTerrainGenerator(int terrainWidth, int terrainHeight, int terrainDepth) {
		super(terrainWidth, terrainHeight, terrainDepth);
	}

	@Override
	public Terrain generate() {
		var terrain = new Terrain(terrainWidth, terrainHeight, terrainDepth);
		for (int x = 0; x < terrainWidth; x++) {
			for (int z = 0; z < terrainDepth; z++) {
				terrain.setBlock(Blocks.TEST, x, 0, z);
			}
		}
		
		return terrain;
	}

}
