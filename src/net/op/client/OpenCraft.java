package net.op.client;

import static java.lang.Math.*;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.op.SharedConstants;
import net.op.entity.Player;
import net.op.render.Render;
import net.op.render.TerrainRenderer;
import net.op.util.MouseUtils;
import net.op.world.PlainTerrainGenerator;
import net.op.world.World;

public class OpenCraft implements Runnable {

	public static final float SENSIVILITY = 0.1f;
	public static final float MOVEMENT_SPEED = 0.1f;
	
	public static OpenCraft oc = null;

	public Thread thread;
	public Random random;

	public World world;
	public Render render;
	public Player player;
	
	public OpenCraft() {
		this.random = new Random();
		
		this.player = new Player();
		this.render = new Render();

		this.thread = new Thread(this);
		this.thread.setName("main");
		
		/* Generate terrain */
		
		var terrainGen = new PlainTerrainGenerator();
		terrainGen.clip(10, 5, 10);
		
		this.world = new World(10, 5, 10);
		this.world.generate(terrainGen);
		
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
		Display.setResizable(true);                        // Make resizable

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
		
		glShadeModel(GL_FLAT); // Cambiar a flat shading
	    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Perspectiva más correcta
		
		render.setupSky();
		glClearDepth(1.0f); // Valor de profundidad del buffer de profundidad

		/* Enable face culling */
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	private void update() {
		Mouse.poll();
		Keyboard.poll();
		
		player.tick(world);

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

			render.cameraYaw += deltaX * SENSIVILITY;
			render.cameraPitch -= deltaY * SENSIVILITY;

			if (render.cameraPitch > 90.0f) {
				render.cameraPitch = 90.0f;
			} else if (render.cameraPitch < -90.0f) {
				render.cameraPitch = -90.0f;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Mouse.setGrabbed(false);
		
		if (!Mouse.isGrabbed())
			return;

		// W
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			player.pos.x += MOVEMENT_SPEED * sin(toRadians(render.cameraYaw));
			player.pos.z -= MOVEMENT_SPEED * cos(toRadians(render.cameraYaw));
		}

		// A
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			player.pos.x -= MOVEMENT_SPEED * sin(toRadians(render.cameraYaw + 90));
			player.pos.z += MOVEMENT_SPEED * cos(toRadians(render.cameraYaw + 90));
		}

		// S
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			player.pos.x -= MOVEMENT_SPEED * sin(toRadians(render.cameraYaw));
			player.pos.z += MOVEMENT_SPEED * cos(toRadians(render.cameraYaw));
		}

		// D
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			player.pos.x += MOVEMENT_SPEED * sin(toRadians(render.cameraYaw + 90));
			player.pos.z -= MOVEMENT_SPEED * cos(toRadians(render.cameraYaw + 90));
		}

		// Space
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			player.jump();

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		
		render.moveCameraTo(player);

		var terrainRenderer = new TerrainRenderer();
		terrainRenderer.clip(
				world.getWidth(),
				world.getHeight(),
				world.getDepth()
			);
		
		terrainRenderer.render(world);
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