package net.opencraft.entity;

import java.text.MessageFormat;
import java.util.UUID;

import org.joml.Vector3f;

public abstract class Entity {

    public float x;
    public float y;
    public float z;

    public float xd;
    public float yd;
    public float zd;

    protected boolean removed;
    protected UUID uuid;
    public float health;

    public final Vector3f pos() {
        return new Vector3f(x, y, z);
    }

    public final Vector3f dest() {
        return new Vector3f(xd, yd, zd);
    }

    public abstract void tick();

    public void damage(float strength) {
        this.health -= strength;
        if (health <= 0) {
            this.removed = true;
        }
    }

    public abstract void remove();

    public void remove(String cause) {
        this.remove();
        debug("Entity {0} removed: {1}", getUUID().toString(), cause);
    }

    protected static void debug(String pattern, Object... args) {
        System.out.println("[DEBUG]: " + MessageFormat.format(pattern, args));
    }

    public UUID getUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }

        return uuid;
    }

}
