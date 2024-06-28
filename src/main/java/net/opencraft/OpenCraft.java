package net.opencraft;

import static javax.swing.JOptionPane.*;
import static net.opencraft.SharedConstants.*;

import java.awt.Color;
import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import joptsimple.*;
import net.opencraft.renderer.GLContext;
import net.opencraft.util.Utils;

public final class OpenCraft implements Runnable {

	public static OpenCraft oc;

	public int width;
	public int height;

	public boolean running = false;

	private Thread thread;
	public final File directory;

	/**
	 * Creates an OpenCraft instance with the specified parameters.
	 * 
	 * @param width  the window width
	 * @param height the window height
	 */
	public OpenCraft(File directory, int width, int height) {
		if (directory == null) {
			showMessageDialog(null, "Invalid directory!", "Failed to instance OpenCraft!", ERROR_MESSAGE);
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

		Display.update();
		Display.sync(60);
	}

	public void run() {
		this.init();
		this.running = true;

		GLContext gl = GLContext.instance;
		gl.setClip(0, 0, width, height);
		while (running) {
			gl.render();

			gl.setColor(Color.WHITE);
			gl.drawLine(0, 0, 854, 480);

			tick();
		}

		this.stop();
	}

	/**
	 * Initializes OpenGL and Inputs.
	 * @throws LWJGLException if something failed to start
	 * @throws UnsatisfiedLinkError if natives weren't linked
	 * @throws Exception if something else failed
	 */
	private void initGL() throws LWJGLException, UnsatisfiedLinkError, Exception {
		// Check if full screen is activated
		if (Utils.isFullscreen(width, height))
			Display.setFullscreen(true);

		/* Display */
		Display.setDisplayMode(new DisplayMode(this.width, this.height)); // Sets the width and height of the display
		Display.setTitle("OpenCraft ".concat(VERSION_STRING));            // Sets the window title
		Display.create();                                                 // Creates the window

		/* Inputs */
		Keyboard.create(); // Create Keyboard
		Mouse.create();    // Create mouse

		// If keyboard is not created, throw an exception
		if (!Keyboard.isCreated())
			throw new LWJGLException(String.format(INIT_ERROR_MESSAGE, "the keyboard"));

		// Same with mouse
		if (!Mouse.isCreated())
			throw new LWJGLException(String.format(INIT_ERROR_MESSAGE, "the mouse"));
	}
	
	/**
	 * Tries to stop the game saving all resources
	 * and stopping the current thread.
	 * 
	 * How this method works:
	 * <ul>
	 *   <li>Sets running variable to false</li>
	 *   <li>Destroys inputs and display</li>
	 *   <li>Stops the thread with {@link Thread#stop()} or with {@link Thread#interrupt()}</li>
	 *   <li>Exits the JVM</li>
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
		 * If the thread didn't stop and failed,
		 * exit the JVM
		 */
		System.exit(FORCE_THREAD_EXIT_CODE);
	}

	private void init() {
		try {
			// Try to initialize OpenGL stuff
			this.initGL();
		} catch (LWJGLException e) {
			e.printStackTrace();
			showMessageDialog(null, e, "Failed to start OpenCraft!", ERROR_MESSAGE);

			if (e.getMessage().contains(INIT_ERROR_MESSAGE.substring(3)))
				System.exit(INIT_ERROR_CODE);

			System.exit(GENERIC_ERROR_CODE);
		} catch (UnsatisfiedLinkError ule) {
			showMessageDialog(null, "OpenCraft requires OpenGL natives!", "Failed to start OpenCraft!", ERROR_MESSAGE);
			System.exit(NO_NATIVES_LINKED_CODE);
		} catch (Exception ex) {
			ex.printStackTrace();
			showMessageDialog(null, ex, "Failed to start OpenCraft!", ERROR_MESSAGE);

			System.exit(GENERIC_ERROR_CODE);
		}

		this.running = true;
	}

	public static void main(String[] args) {
		OptionParser parser = new OptionParser();

		Utils.GUI.setOSLookAndFeel();
		checkNatives();

		OptionSpec<?> gameDirArgument = parser.accepts("gameDir").withRequiredArg();
		File gameDir = null;

		OptionSet optionSet = parser.parse(args);
		if (optionSet.has(gameDirArgument))
			gameDir = Utils.parseDirectory(optionSet.valueOf(gameDirArgument));
		else
			gameDir = Utils.requestDirectory();

		// Call main thread "BootstrapThread"
		Thread.currentThread().setName("BootstrapThread");

		// Create an OpenCraft instance
		OpenCraft.oc = new OpenCraft(gameDir, 854, 480);

		// The thread the game is allocated on
		System.out.println("Allocating game on: " + oc.thread.getName());

		// Start the game
		oc.thread.start();

		// Wait until the game finishes
		try {
			oc.thread.join();
		} catch (Exception ignored) {
		}

		// Exit
		System.exit(0);
	}

	private static void checkNatives() {
		if (!Utils.Natives.nativesLinked()) {
			final int option = showConfirmDialog(null,
					"The game detected there are no natives linked.\nDownload natives?", "No natives linked",
					YES_NO_OPTION, QUESTION_MESSAGE);
			if (option == YES_OPTION) {
				File nativesDir = new File(".");

				System.out.println("No natives linked: Downloading...");
				boolean successful = Utils.Natives.downloadNatives(nativesDir);
				showMessageDialog(null, "The game has finished downloading the natives!");
				if (!successful)
					System.err.println("Failed to download natives!");

			} else
				System.out.println("No natives linked: Ignored");
		}
	}

}
