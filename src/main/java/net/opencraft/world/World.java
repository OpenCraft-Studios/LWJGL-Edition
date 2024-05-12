package net.opencraft.world;

import java.io.Serializable;

import net.opencraft.world.terrain.TerrainGenerator;

public final class World implements Serializable {
	
	private static final long serialVersionUID = -7851540933321672837L;
	
	Chunk[][] chunks = new Chunk[16][16];

	public World() {
	}
	
	public void generateTerrain(TerrainGenerator terrainGenerator) {
		for (int x = 0; x < 10; x++) {
			for (int z = 0; z < 5; z++) {
				this.chunks[x][z] = new Chunk(x, z);
				this.chunks[x][z].generateTerrain(terrainGenerator);
			}
		}
	}

	public void render() {
		// Go through every chunk and render them
		
		for (int x = 0; x < 10; x++) {
			for (int z = 0; z < 5; z++) {
				this.chunks[x][z].render();
			}
		}
	}
	
}
