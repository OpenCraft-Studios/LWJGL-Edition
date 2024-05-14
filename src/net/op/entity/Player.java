package net.op.entity;

import org.joml.Vector3f;
import org.joml.Vector3i;

import net.op.block.Block;
import net.op.render.model.PlayerModel;
import net.op.world.World;

public class Player extends Entity {

	private boolean inGround = true;
	private PlayerModel model;
	
	public Player() {
		super();
		
		this.model = PlayerModel.NORMAL;
		this.pos.set(0, 2, 0);
	}

	@Override
	public void tick(World world) {
		if (pos.y < -15)
			this.pos.set(0, 2, 0);
		
		Block block = world.getBlock(
					new Vector3i(
						(int) pos.x,
						(int) pos.y,
						(int) pos.x
					).sub(0, 1, 0)
				);

		if (block == null)
			inGround = false;
		else if (!block.getModel().isVisible())
			inGround = false;
		else
			inGround = true;
		
		if (!inGround) {
			this.pos.sub(0, 0.07f, 0);
		}
		
	}
	
	public void jump() {
		if (!inGround)
			return;
		
		
		
		this.pos.add(0, 1.2f, 0);
	}

	@Override
	public Vector3f getCameraPos() {
		return pos;
	}
	
	public PlayerModel getModel() {
		return model;
	}

	public void render() {
		this.model.render();
	}
	
}
