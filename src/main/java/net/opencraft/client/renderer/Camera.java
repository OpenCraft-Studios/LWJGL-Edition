package net.opencraft.client.renderer;

import static java.lang.Math.*;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.josl.d3util.Vertex3f;

/**
 * <h1>Camera class</h1>
 * The camera class contains all the necessary information
 * of a camera.
 */
public class Camera {

	/**
	 * The camera's x position
	 */
	private float x;
	
	/**
	 * The camera's y position
	 */
	private float y;
	
	/**
	 * The camera's z position
	 */
	private float z;
	
	/* I don't know what are these */
	private float yaw;
	private float pitch;
	
	/**
	 * Instances a new camera class.
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 */
	public Camera(float x, float y, float z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	/**
	 * Instances a new camera class.
	 * @param pos the camera position
	 */
	public Camera(Vertex3f pos, float yaw, float pitch) {
		this(pos.x, pos.y, pos.z, yaw, pitch);
	}

	/**
	 * @return the camera position
	 */
	public Vertex3f getPos() {
		return new Vertex3f(x, y, z);
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getFrustumNear() {
		return 0.1f;
	}

	public float getFrustumFar() {
		return 100.0f;
	}

	public Matrix4fc getProjectionMatrix() {
		float fov = 85.0f;
		float aspectRatio = 16.0f / 9.0f;
		float near = 0.1f;
		float far = 100.0f;
		float f = (float) (1.0 / tan(toRadians(fov) / 2.0));
		return new Matrix4f().setPerspective(f, aspectRatio, near, far);
	}

	public Matrix4fc getViewMatrix() {
		return new Matrix4f().setLookAt(x, y, z,
				(float) (x + cos(toRadians(yaw)) * cos(toRadians(pitch))),
				(float) (y + sin(toRadians(pitch))),
				(float) (z + sin(toRadians(yaw)) * cos(toRadians(pitch))), 0, 1, 0);
	}
}
