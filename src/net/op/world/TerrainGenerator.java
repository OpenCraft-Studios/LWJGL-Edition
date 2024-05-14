package net.op.world;

public interface TerrainGenerator {
	
	public abstract void clip(int width, int height, int depth);
	
	public abstract void generate(Terrain terrain);
	
}
