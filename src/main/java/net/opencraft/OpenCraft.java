package net.opencraft;

import static javax.swing.JOptionPane.*;
import static net.opencraft.SharedConstants.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.opencraft.renderer.GLGraphicsImpl;
import net.opencraft.renderer.gui.GuiLogo;
import net.opencraft.util.Utils;

public final class OpenCraft implements Runnable {

	public static OpenCraft oc;

	public int width;
	public int height;

	public boolean running = false;

	public final Thread thread;
	public final File directory;
	private GuiLogo logo = new GuiLogo();

	/**
	 * Creates an OpenCraft instance with the specified parameters.
	 * 
	 * @param width  the window width
	 * @param height the window height
	 */
	public OpenCraft(File directory, int width, int height) {
		if (directory == null) {
			showMessageDialog(null, INVALID_DIR_MESSAGE, "Failed to instance OpenCraft!", ERROR_MESSAGE);
			System.exit(INVALID_DIR_CODE);
		}

		this.width = width;
		this.height = height;

		// Main thread
		this.thread = new Thread(this);
		this.thread.setName("oc-thread");

		this.directory = directory;
	}

	/**
	 * Creates an OpenCraft instance with the selected display mode
	 */
	public OpenCraft(File directory, DisplayMode dm) {
		this(directory, dm.getWidth(), dm.getHeight());
	}

	/**
	 * Creates an OpenCraft instance with full screen enabled
	 */
	public OpenCraft(File directory) {
		this(directory, Display.getDesktopDisplayMode());
	}

	private void tick() {
		this.running = running && !Display.isCloseRequested();

		if (Display.wasResized()) {
			this.width = Display.getWidth();
			this.height = Display.getHeight();
			GLGraphicsImpl.instance.setClip(0, 0, width, height);
		}

		Display.update();
		Display.sync(60);
	}

	public void run() {
		this.init();
		this.running = true;

		glClearColor(1F, 0, 0, 1F);
		while (running) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			logo.autoBounds();
			logo.render();
			
			tick();
		}

		this.stop();
	}

	/**
	 * Initializes OpenGL and Inputs.
	 * 
	 * @throws LWJGLException       if something failed to start
	 * @throws UnsatisfiedLinkError if natives weren't linked
	 * @throws Exception            if something else failed
	 */
	private void initGL() throws LWJGLException, UnsatisfiedLinkError, Exception {
		// Check if full screen is activated
		Display.setFullscreen(Utils.isFullscreen(width, height));

		/* Display */
		Display.setDisplayMode(new DisplayMode(854, 480));     // Sets the width and height of the display
		Display.setTitle("OpenCraft ".concat(VERSION_STRING)); // Sets the window title
		Display.setResizable(true);                            // Set resizable
		Display.create();                                      // Creates the window

		/* Inputs */
		Keyboard.create(); // Create Keyboard
		Mouse.create();    // Create mouse
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// If keyboard is not created, throw an exception
		if (!Keyboard.isCreated())
			throw new LWJGLException(String.format(INIT_ERROR_MESSAGE, "the keyboard"));

		// Same with mouse
		if (!Mouse.isCreated())
			throw new LWJGLException(String.format(INIT_ERROR_MESSAGE, "the mouse"));
	}

	/**
	 * Tries to stop the game saving all resources and stopping the current thread.
	 * 
	 * How this method works:
	 * <ul>
	 * <li>Sets running variable to false</li>
	 * <li>Destroys inputs and display</li>
	 * <li>Stops the thread with {@link Thread#stop()} or with
	 * {@link Thread#interrupt()}</li>
	 * <li>Exits the JVM</li>
	 * </ul>
	 */
	@SuppressWarnings("deprecation")
	private void stop() {
		// This program is no longer running
		this.running = false;

		// Destroy inputs
		Mouse.destroy();
		Keyboard.destroy();

		// Destroy display
		Display.destroy();

		// Try to stop the thread
		try {
			// Traditional way
			thread.stop();
		} catch (Exception e1) {
			try {
				// If that didn't work, try the new way
				thread.interrupt();
			} catch (Exception e2) {
				// If somehow it didn't work, throw an exception
				e1.printStackTrace();
				e2.printStackTrace();
			}
		}

		/*
		 * If the thread didn't stop and failed, exit the JVM
		 */
		System.exit(FORCE_THREAD_EXIT_CODE);
	}

	private void init() {
		try {
			// Try to initialize OpenGL stuff
			this.initGL();
		} catch (LWJGLException e) {
			/*
			 * This exception is thrown when a OpenGL component fails to initialize.
			 */
			e.printStackTrace(); // Show stack trace on console
			showMessageDialog(null, e, "Failed to start OpenCraft!", ERROR_MESSAGE);

			if (e.getMessage().contains(INIT_ERROR_MESSAGE.substring(3)))
				System.exit(INIT_ERROR_CODE);

			System.exit(GENERIC_ERROR_CODE);

		} catch (UnsatisfiedLinkError ule) {
			/*
			 * This error occurs when OpenCraft has got no linked natives (DLL files).
			 */

			// Show a error message
			showMessageDialog(null, "OpenCraft requires OpenGL natives!", "Failed to start OpenCraft!", ERROR_MESSAGE);

			// Exit the JVM with the error code specified
			System.exit(NO_NATIVES_LINKED_CODE);

		} catch (Exception ex) {
			ex.printStackTrace();
			showMessageDialog(null, ex, "Failed to start OpenCraft!", ERROR_MESSAGE);

			System.exit(GENERIC_ERROR_CODE);
		}

		this.running = true;
	}

}
