package net.op.render;

import net.op.world.terrain.Terrain;

public class TerrainRenderer {

	public void renderTerrain(Terrain terrain) {
		terrain.getBlock(0, 0, 0).render(0, 0, 0);
	}
	
	
}
