package net.opencraft.world;

import java.io.Serializable;

import net.opencraft.world.terrain.Terrain;
import net.opencraft.world.terrain.TerrainGenerator;

public class Chunk implements Serializable {

	private static final long serialVersionUID = 6691170498805821180L;

	int x;
	int z;

	Block[] cubes = new Block[16 * 256 * 16];

	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public Block[] getCubes() {
		return cubes;
	}

	public void setCubes(Block[] cubes) {
		this.cubes = cubes;
	}

	public Block getCube(int x, int y, int z) {
		int index = x + (y * 16) + (z * 16 * 16);
		if (index < 0 || index >= cubes.length || cubes[index] == null)
			return new Block(0, x, y, z);

		return cubes[index];
	}

	public void generateTerrain(TerrainGenerator terrainGenerator) {
		Terrain terrain = terrainGenerator.generateTerrain(x, z);
		setCubes(terrain.getBlocks());
	}

	public void render() {
		for (Block cube : cubes) {
			if (cube != null)
				cube.render(this);
		}
	}
}
