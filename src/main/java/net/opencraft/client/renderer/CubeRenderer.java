package net.opencraft.client.renderer;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Color;

import org.josl.d3util.Vertex3f;

import net.opencraft.OpenCraft;
import net.opencraft.world.Chunk;

/**
 * <h1>CubeRenderer class</h1>
 * This class renders cubes
 */
public class CubeRenderer {

	/**
	 * Renders a chunk with the given arguments
	 * 
	 * @param chunk the chunk to render
	 * @param v3f the position of the terrain
	 * @param size the size of the blocks
	 * @param color the color of the blocks
	 */
	public static void renderTerrain(Chunk chunk, Vertex3f v3f, float size, int color) {
		float x, y, z;
		x = v3f.x;
		y = v3f.y;
		z = v3f.z;

		float r, g, b;
		r = (float) new Color(color).getRed() / 255.0f;
		g = (float) new Color(color).getGreen() / 255.0f;
		b = (float) new Color(color).getBlue() / 255.0f;

		float halfSize = size / 2;

		Camera camera = OpenCraft.getCamera();

		float camX = camera.getPos().getX();
		float camY = camera.getPos().getY();
		float camZ = camera.getPos().getZ();

		float cameraYaw = camera.getYaw();
		float cameraPitch = camera.getPitch();

		glPushMatrix();
		glTranslatef(x, y, z);
		glColor3f(r, g, b);

		GlStateManager.enableCull();

		// Front face
		if (isVisible(x, y, z + halfSize, camX, camY, camZ, cameraYaw, cameraPitch)) {
			glBegin(GL_QUADS);
			{
				glVertex3f(-halfSize, -halfSize, halfSize);
				glVertex3f(halfSize, -halfSize, halfSize);
				glVertex3f(halfSize, halfSize, halfSize);
				glVertex3f(-halfSize, halfSize, halfSize);
			}
			glEnd();
		}

		GlStateManager.disableCull();

		GlStateManager.enableCull();

		// Back face
		if (isVisible(x, y, z - halfSize, camX, camY, camZ, cameraYaw, cameraPitch)) {
			glBegin(GL_QUADS);
			{
				glVertex3f(halfSize, -halfSize, -halfSize);
				glVertex3f(-halfSize, -halfSize, -halfSize);
				glVertex3f(-halfSize, halfSize, -halfSize);
				glVertex3f(halfSize, halfSize, -halfSize);
			}
			glEnd();
		}

		GlStateManager.disableCull();

		GlStateManager.enableCull();

		// Top face
		if (isVisible(x, y + halfSize, z, camX, camY, camZ, cameraYaw, cameraPitch)) {
			glBegin(GL_QUADS);
			{
				glVertex3f(-halfSize, halfSize, -halfSize);
				glVertex3f(-halfSize, halfSize, halfSize);
				glVertex3f(halfSize, halfSize, halfSize);
				glVertex3f(halfSize, halfSize, -halfSize);
			}
			glEnd();
		}

		GlStateManager.disableCull();

		GlStateManager.enableCull();

		// Bottom face
		if (isVisible(x, y - halfSize, z, camX, camY, camZ, cameraYaw, cameraPitch)) {
			glBegin(GL_QUADS);
			{
				glVertex3f(-halfSize, -halfSize, -halfSize);
				glVertex3f(halfSize, -halfSize, -halfSize);
				glVertex3f(halfSize, -halfSize, halfSize);
				glVertex3f(-halfSize, -halfSize, halfSize);
			}
			glEnd();
		}

		GlStateManager.disableCull();

		GlStateManager.enableCull();

		// Right face
		if (isVisible(x + halfSize, y, z, camX, camY, camZ, cameraYaw, cameraPitch)) {
			glBegin(GL_QUADS);
			{
				glVertex3f(halfSize, -halfSize, -halfSize);
				glVertex3f(halfSize, halfSize, -halfSize);
				glVertex3f(halfSize, halfSize, halfSize);
				glVertex3f(halfSize, -halfSize, halfSize);
			}
			glEnd();
		}

		GlStateManager.disableCull();

		GlStateManager.enableCull();

		// Left face
		if (isVisible(x - halfSize, y, z, camX, camY, camZ, cameraYaw, cameraPitch)) {
			glBegin(GL_QUADS);
			{
				glVertex3f(-halfSize, -halfSize, -halfSize);
				glVertex3f(-halfSize, -halfSize, halfSize);
				glVertex3f(-halfSize, halfSize, halfSize);
				glVertex3f(-halfSize, halfSize, -halfSize);
			}
			glEnd();
		}

		GlStateManager.disableCull();

		glPopMatrix();

	}

	/**
	 * Renders a chunk with the given arguments
	 * 
	 * @param chunk the chunk to render
	 * @param v3f the position of the terrain
	 * @param size the size of the blocks
	 * @param color the color of the blocks
	 */
	public static void renderTerrain(Chunk chunk, Vertex3f v3f, float size, Color color) {
		renderTerrain(chunk, v3f, size, color.getRGB());
	}
	
	/**
	 * Checks if a face is visible to the camera
	 * 
	 * @param faceX the x face of the cube
	 * @param faceY the y face of the cube
	 * @param faceZ the z face of the cube
	 * @param cameraX the camera x position
	 * @param cameraY the camera y position
	 * @param cameraZ the camera z position
	 * @param cameraYaw camera's yaw
	 * @param cameraPitch camera's pitch
	 * 
	 * @apiNote Not completed yet!
	 */
	private static boolean isVisible(float faceX, float faceY, float faceZ, float cameraX, float cameraY, float cameraZ,
			float cameraYaw, float cameraPitch) {

		// CiroZDP: Good luck doing that!!
		return true; // going to do the back face cull technique directly
	}

	/**
	 * Checks if a face is visible to the camera
	 * 
	 * @param faceV the faces of the cube
	 * @param cameraV the camera position
	 * @param cameraYaw camera's yaw
	 * @param cameraPitch camera's pitch
	 * 
	 * @apiNote Not completed yet!
	 */
	@SuppressWarnings("unused")
	private static boolean isVisible(Vertex3f faceV, Vertex3f cameraV, float yaw, float pitch) {
		return isVisible(faceV.x, faceV.y, faceV.z, cameraV.x, cameraV.y, cameraV.z, yaw, pitch);
	}
}
