package org.josl.d3util;

public class Vertex3f {

	public float x = 0;
	public float y = 0;
	public float z = 0;

	public Vertex3f() {
	}

	public Vertex3f(float x, float y, float z) {
		set(x, y, z);
	}
	
	public static Vertex3f newTemp(float x, float y, float z) {
		return new Vertex3f().set(x, y, z);
	}
	
	/* Setters */

	public Vertex3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public Vertex3f set(Vertex3f pos) {
		return set(pos.x, pos.y, pos.z);
	}

	public Vertex3f set(float[] v) {
		this.set(v[0], v[1], v[2]);
		return this;
	}

	public Vertex3f setX(float x) {
		this.x = x;
		return this;
	}

	public Vertex3f setY(float y) {
		this.y = y;
		return this;
	}

	public Vertex3f setZ(float z) {
		this.z = z;
		return this;
	}

	/* Getters */
	public float[] get() {
		return new float[] { x, y, z };
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	/* Control methods */

	public Vertex3f reset() {
		return set(0, 0, 0);
	}
	
	public Vertex3f clone() {
		return new Vertex3f(x, y, z);
	}
	
	/* Operations */
	
	public Vertex3f add(Vertex3f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		
		return this;
	}
	
	public Vertex3f substract(Vertex3f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public Vertex3f multiply(Vertex3f other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}
	
	public Vertex3f sub(Vertex3f other) {
		return this.substract(other);
	}
	
	public Vertex3f mul(Vertex3f other) {
		return this.multiply(other);
	}

}
