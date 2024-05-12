package net.op.world.terrain;

import org.joml.Vector3i;

import net.op.util.Blocks;

public class PlainTerrainGenerator extends TerrainGenerator {

	public PlainTerrainGenerator(int terrainWidth, int terrainHeight, int terrainDepth) {
		super(terrainWidth, terrainHeight, terrainDepth);
	}

	@Override
	public Terrain generate() {
		var terrain = new Terrain(terrainWidth, terrainHeight, terrainDepth);
		terrain.setBlock(Blocks.TEST, new Vector3i(0, 0, 0));
		
		return terrain;
	}

}
