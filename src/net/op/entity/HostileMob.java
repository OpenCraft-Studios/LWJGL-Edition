package net.op.entity;

public abstract class HostileMob extends Mob {
	
	@Override
	public int getMood() {
		return Mob.HOSTILE_MOOD;
	}

}
