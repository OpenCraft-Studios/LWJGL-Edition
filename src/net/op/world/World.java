package net.op.world;

import net.op.world.terrain.Terrain;

public class World {

	private Terrain terrain;
	// private Collection<Entity> entities;
	
	public World(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
}
