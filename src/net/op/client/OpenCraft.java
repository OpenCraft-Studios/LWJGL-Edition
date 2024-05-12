package net.op.client;

import static java.lang.Math.*;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.op.SharedConstants;
import net.op.entity.Player;
import net.op.render.Camera;
import net.op.render.Render;
import net.op.render.TerrainRenderer;
import net.op.util.Blocks;
import net.op.util.MouseUtils;
import net.op.world.terrain.PlainTerrainGenerator;
import net.op.world.terrain.Terrain;

public class OpenCraft implements Runnable {

	public static OpenCraft oc = null;

	public Thread thread;

	public Render render;
	public Camera camera;
	public Player player;
	public Terrain terrain;

	public OpenCraft() {
		this.camera = new Camera();
		this.render = new Render(camera);

		this.thread = new Thread(this);
		this.thread.setName("main");
		
		var terrainGen = new PlainTerrainGenerator(10, 5, 10);
		this.terrain = terrainGen.generate();
	}

	@Override
	public void run() {
		safeInit();

		while (!Display.isCloseRequested()) {
			update();
			render();

			Display.update();
			Display.sync(60);
		}

		this.stop();
	}

	private void init() throws LWJGLException {
		/* Create display */
		Display.setDisplayMode(new DisplayMode(854, 480)); // Set size
		Display.setTitle(SharedConstants.DISPLAY_NAME);    // Set display title
		Display.create();                                  // Create it
		Display.setResizable(false);                       // Make not resizable

		/* Init input */
		Mouse.create();
		Keyboard.create();

		render.updateProjection();

		/* Configure mouse */
		Mouse.setGrabbed(false); // Ungrab mouse

		/* OpenGL */
		glEnable(GL_DEPTH_TEST); // Enable 3D
		glDepthFunc(GL_LEQUAL);

		/* Light options */
		glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_NORMALIZE);

		// Set sky color
		render.setupSky();

		// Enable 2d textures
		glEnable(GL_TEXTURE_2D);

		/* Enable face culling */
		glEnable(GL_CULL_FACE);
		glCullFace(GL_RIGHT);
	}

	private void update() {
		Mouse.poll();
		Keyboard.poll();

		while (!Mouse.isGrabbed() && Mouse.next()) {
			if (!Mouse.getEventButtonState()
					| Mouse.getEventButton() != 0
					| !MouseUtils.inScreen())
				continue;

			Mouse.setGrabbed(true);
		}

		if (Mouse.isGrabbed()) {
			int deltaX = Mouse.getDX();
			int deltaY = Mouse.getDY();

			camera.yaw += deltaX * Camera.SENSIVILITY;
			camera.pitch -= deltaY * Camera.SENSIVILITY;

			if (camera.pitch > 90.0f) {
				camera.pitch = 90.0f;
			} else if (camera.pitch < -90.0f) {
				camera.pitch = -90.0f;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Mouse.setGrabbed(false);
		
		if (!Mouse.isGrabbed())
			return;

		// W
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.x += Camera.MOVEMENT_SPEED * sin(toRadians(camera.yaw));
			camera.z -= Camera.MOVEMENT_SPEED * cos(toRadians(camera.yaw));
		}

		// A
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.x -= Camera.MOVEMENT_SPEED * sin(toRadians(camera.yaw + 90));
			camera.z += Camera.MOVEMENT_SPEED * cos(toRadians(camera.yaw + 90));
		}

		// S
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.x -= Camera.MOVEMENT_SPEED * sin(toRadians(camera.yaw));
			camera.z += Camera.MOVEMENT_SPEED * cos(toRadians(camera.yaw));
		}

		// D
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.x += Camera.MOVEMENT_SPEED * sin(toRadians(camera.yaw + 90));
			camera.z -= Camera.MOVEMENT_SPEED * cos(toRadians(camera.yaw + 90));
		}

		// Space
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			camera.y += Camera.MOVEMENT_SPEED;

		// LShift or RShift
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			camera.y -= Camera.MOVEMENT_SPEED;

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();

		// Apply camera rotations
		glRotatef(camera.pitch, 1.0f, 0.0f, 0.0f);
		glRotatef(camera.yaw, 0.0f, 1.0f, 0.0f);

		//System.out.println(-camera.x + " " + -camera.y + " " + -camera.z);
		// Apply camera translations
		glTranslatef(-camera.x, -camera.y, -camera.z);

		TerrainRenderer trender = new TerrainRenderer();
		trender.renderTerrain(terrain);
		
		render.drawCrosshair();
	}

	@SuppressWarnings("deprecation")
	private void stop() {
		Display.destroy();

		try {
			this.thread.stop();
		} catch (Exception e1) {
			try {
				this.thread.interrupt();
			} catch (Exception e2) {
				e1.printStackTrace();
				e2.printStackTrace();
			}
		}
	}

	private void safeInit() {
		try {
			init();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		oc = new OpenCraft();
		oc.thread.start();
	}

}
