package net.op.world.terrain;

import java.util.Arrays;

import org.joml.Vector3i;

import net.op.render.Block;
import net.op.util.Blocks;

public class Terrain {

	byte[] blocks;
	private final int width;
	private final int height;
	private final int depth;
	
	public Terrain(int width, int height, int depth) {
		this.blocks = new byte[width * height * depth];
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		// Fill the terrain with air
		Arrays.fill(this.blocks, Blocks.AIR.id);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public byte[] getBlocks() {
		return blocks;
	}
	
	public Block getBlock(int x, int y, int z) {
        return Blocks.find(blocks[getBlockIndex(x, y, z)]);
    }
	
	public Block getBlock(Vector3i blockpos) {
		return getBlock(blockpos.x, blockpos.y, blockpos.z);
	}
	
	public void setBlock(Block block, int x, int y, int z) {
		int index = getBlockIndex(x, y, z);
		this.blocks[index] = block.id;
	}
	
	public void setBlock(Block block, Vector3i blockpos) {
		this.setBlock(block, blockpos.x, blockpos.y, blockpos.z);
	}
	
	private int getBlockIndex(int x, int y, int z) {
		return x + y * width + z * width * height;
	}
	
}
