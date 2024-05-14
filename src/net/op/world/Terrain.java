package net.op.world;

import org.joml.Vector3i;

import net.op.block.Block;

public interface Terrain {

	void setBlock(Block block, int x, int y, int z);

	default void setBlock(Block block, Vector3i blockpos) {
		setBlock(block, blockpos.x, blockpos.y, blockpos.z);
	}

	void setBlock(byte blockId, int x, int y, int z);

	default void setBlock(byte blockId, Vector3i blockpos) {
		setBlock(blockId, blockpos.x, blockpos.y, blockpos.z);
	}

	Block getBlock(int x, int y, int z);

	default Block getBlock(Vector3i blockpos) {
		return getBlock(blockpos.x, blockpos.y, blockpos.z);
	}

	byte getBlockId(int x, int y, int z);

	default byte getBlockId(Vector3i blockpos) {
		return getBlockId(blockpos.x, blockpos.y, blockpos.z);
	}

}
