package net.op.entity;

import org.joml.Vector3f;

import net.op.render.AABB;
import net.op.world.World;

public abstract class Entity {

	public final Vector3f pos = new Vector3f(0, 0, 0);
	private float health;

	public float getHealth() {
		return health;
	}

	public int getMood() {
		return Mob.NEUTRAL_MOOD;
	}

	public AABB getAABB() {
		AABB aabb = new AABB(pos.x, pos.y, pos.z, pos.x + 1.0f, pos.y + 1.0f, pos.z + 1.0f);

		return aabb;
	}
	
	public abstract void tick(World world);
	
	public Vector3f getCameraPos() {
		return pos;
	}

}
