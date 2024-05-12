package net.opencraft.world.terrain;

import com.raylabz.opensimplex.OpenSimplexNoise;
import com.raylabz.opensimplex.RangedValue;

import net.opencraft.world.Block;

public class TerrainGenerator {

	public float amplitude = 30.0f;
	public float roughness = 0.05f;

	private OpenSimplexNoise noise;

	public TerrainGenerator() {
		this.noise = new OpenSimplexNoise();
	}
	
	public TerrainGenerator(final long seed) {
		this.noise = new OpenSimplexNoise(seed);
	}

	public float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}

	public float getRoughness() {
		return roughness;
	}

	public void setRoughness(float roughness) {
		this.roughness = roughness;
	}

	public Terrain generateTerrain(int chunkX, int chunkZ) {
		final int CHUNK_SIZE_X = 16;
		final int CHUNK_SIZE_Y = 256;
		final int CHUNK_SIZE_Z = 16;

		Block[] cubes = new Block[CHUNK_SIZE_X * CHUNK_SIZE_Y * CHUNK_SIZE_Z];

		for (int x = 0; x < CHUNK_SIZE_X; x++) {
			for (int z = 0; z < CHUNK_SIZE_Z; z++) {
				int worldX = chunkX * CHUNK_SIZE_X + x;
				int worldZ = chunkZ * CHUNK_SIZE_Z + z;
				int height = (int) (getPerlinNoise(worldX, worldZ) * CHUNK_SIZE_Y);

				for (int y = 0; y < height; y++) {
					float BLOCK_SIZE = 1.0f;
					float xPos = worldX * BLOCK_SIZE;
					float yPos = y * BLOCK_SIZE;
					float zPos = worldZ * BLOCK_SIZE;

					Block cube = new Block(1, (int) xPos, (int) yPos, (int) zPos);

					cubes[x * 256 * 16 + y * 16 + z] = cube;
				}
			}
		}

		return Terrain.of(cubes);
	}

	private double getPerlinNoise(int x, int z) {
		RangedValue noiseValue = noise.getNoise2D(x * roughness, z * roughness);
		return noiseValue.getValue();
	}
}
