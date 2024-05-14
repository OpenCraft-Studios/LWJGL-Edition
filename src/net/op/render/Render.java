package net.op.render;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import net.op.entity.Entity;

public class Render {

	public float cameraYaw = 0;
	public float cameraPitch = 0;
	
	private float fov = 60;

	public Render() {
	}

	public void setupSky() {
		glClearColor(0, 0.8f, 0.8f, 1);
	}
	
	public float getFOV() {
		return fov;
	}
	
	public void setFOV(final float fov) {
		this.fov = fov; 
	}

	public void updateProjection() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		updatePerspective();
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public void updatePerspective() {
		float aspectRatio = (float) Display.getWidth() / Display.getHeight();
		float zNear = 0.1f;
		float zFar = 1000.0f;
		GLU.gluPerspective(getFOV(), aspectRatio, zNear, zFar);
	}

	public void drawCrosshair() {
		begin2D();

		// Set the color of the crosshair to white
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Calculate the coordinates of the screen center */
		float centerX = Display.getWidth() / 2.0f;
		float centerY = Display.getHeight() / 2.0f;

		// Set crosshair size
		float crosshairSize = 23.0f;

		// Set crosshair thickness
		float crosshairThickness = 1.5f;

		/* Draw the horizontal line of the crosshair */
		glLineWidth(crosshairThickness); // Set the thickness
		glBegin(GL_LINES);
		{
			glVertex2f(centerX - crosshairSize / 2, centerY); // From the left of the center
			glVertex2f(centerX + crosshairSize / 2, centerY); // To the right of the center
		}
		glEnd();

		/* Draw the vertical line of the crosshair */
		glBegin(GL_LINES);
		{
			glVertex2f(centerX, centerY - crosshairSize / 2); // From top of the center
			glVertex2f(centerX, centerY + crosshairSize / 2); // To down of the center
		}
		glEnd();

		end2D();
	}

	public void moveCameraTo(Entity e) {
		// Apply camera rotations
		glRotatef(cameraPitch, 1.0f, 0.0f, 0.0f);
		glRotatef(cameraYaw, 0.0f, 1.0f, 0.0f);

		// Move to player
		Vector3f cameraPos = e.getCameraPos();

		final float cameraX = -cameraPos.x;
		final float cameraY = -cameraPos.y;
		final float cameraZ = -cameraPos.z;

		glTranslatef(cameraX, cameraY, cameraZ);
	}

	private void begin2D() {
		// Disable light and texture
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);

		/* Configure OpenGL to an orthographic 2D projection */
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();

		// Screen coordinates
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);

		// Change to the model view
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
	}

	private void end2D() {
		/* Restore previous configuration of OpenGL */
		glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);

		/* Enable textures and light */
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
	}

}
