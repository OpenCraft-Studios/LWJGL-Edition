package net.op.world.terrain;

public abstract class TerrainGenerator {

	final int terrainWidth;
	final int terrainHeight;
	final int terrainDepth;

	public TerrainGenerator(int terrainWidth, int terrainHeight, int terrainDepth) {
		this.terrainWidth = terrainWidth;
		this.terrainHeight = terrainHeight;
		this.terrainDepth = terrainDepth;
	}
	
	public abstract Terrain generate();
	
}
