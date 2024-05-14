package net.op.render;

import net.op.block.Block;
import net.op.world.Terrain;

public class TerrainRenderer {

	private int width = 0;
	private int height = 0;
	private int depth = 0;
	
	public void render(final Terrain terrain) {
		if (width == 0
				| height == 0
				| depth == 0)
			return;
		
		try {
			this.renderTerrain0(terrain);
		} catch (Exception ignored) {
		}
	}
	
	public void clip(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	private void renderTerrain0(final Terrain terrain) throws ArrayIndexOutOfBoundsException {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < depth; z++) {
					Block block = null;
					try {
						block = terrain.getBlock(x, y, z);
					} catch (Exception ignored) {
						continue;
					}
					
					if (block == null)
						continue;
					
					block.render(x, y, z);
				}
			}
		}
	}
	
	
}
