package net.op.client;

import static java.lang.Math.*;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
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
		Display.setDisplayMode(new DisplayMode(854, 480)); // Set display size
		Display.setTitle(SharedConstants.DISPLAY_NAME);    // Set display title
		setDisplayIcons();                                 // Display icons
		Display.create();                                  // Create it
		Display.setResizable(false);                       // Make no it resizable

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

		glShadeModel(GL_FLAT); // Change to flat shading
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Nicely perspective

		render.setupSky();
		glClearDepth(1.0f); // Set value of the buffer depth

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
		terrainRenderer.clip(world.getWidth(), world.getHeight(), world.getDepth());

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

	private ByteBuffer getIcon(String resource) {
		ByteBuffer bb = null;
		
		try {
			InputStream inputStream = getClass().getResourceAsStream(resource);
			BufferedImage image = ImageIO.read(inputStream);

			int width = image.getWidth();
			int height = image.getHeight();

			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);

			bb = BufferUtils.createByteBuffer(width * height * 4);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = pixels[y * width + x];
					bb.put((byte) ((pixel >> 16) & 0xFF));
					bb.put((byte) ((pixel >> 8) & 0xFF));
					bb.put((byte) (pixel & 0xFF));
					bb.put((byte) ((pixel >> 24) & 0xFF));
				}
			}

			bb.flip();
			inputStream.close();
		} catch (Exception ignored) {
		}
		
		return bb;
	}

	private void setDisplayIcons() {
		Stream<String> resources = Stream.of("/icons/icon_16x16.png", "/icons/icon_24x24.png", "/icons/icon_32x32.png",
				"/icons/icon_48x48.png", "/icons/icon_256x256.png");

		List<ByteBuffer> bbfs = resources.map(res -> getIcon(res)).filter(icon -> icon != null)
				.collect(Collectors.toList());

		Display.setIcon(bbfs.toArray(new ByteBuffer[0]));
	}

	public static void main(String[] args) {
		oc = new OpenCraft();
		oc.thread.start();
	}

}