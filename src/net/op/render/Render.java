package net.op.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

public class Render {

	public Render(Camera camera) {
	}

	public void setupSky() {
		glClearColor(0, 0.8f, 0.8f, 1);
	}
	
	public void updateProjection() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		{
			float aspectRatio = (float) Display.getWidth() / Display.getHeight();
			float zNear = 0.1f;
			float zFar = 1000.0f;
			GLU.gluPerspective(Camera.FOV, aspectRatio, zNear, zFar);
		}
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public void drawCrosshair() {
		// Disable light and texture
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);

		// Set the color of the crosshair to white
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Calculate the coordinates of the screen center */
		float centerX = Display.getWidth() / 2.0f;
		float centerY = Display.getHeight() / 2.0f;

		// Set crosshair size
		float crosshairSize = 23.0f;

		// Set crosshair thickness
		float crosshairThickness = 1.5f;

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
