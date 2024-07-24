package net.opencraft;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import static javax.swing.JOptionPane.*;
import static net.opencraft.SharedConstants.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import net.opencraft.renderer.model.CubeModel;
import net.opencraft.renderer.Camera;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.opencraft.util.Utils;
import net.opencraft.renderer.Renderer;
import net.opencraft.util.InputManager;

public final class OpenCraft implements Runnable {

    public static OpenCraft oc;

    public int width;
    public int height;
    
    private boolean didZoom = false;
    private int monitorRefreshRate = 60;
    
    public boolean running = false;

    public final Thread thread;
    public final File directory;

    public Renderer renderer;
    private InputManager im;
    public Camera camera;
    public double deltaTime = 0.016d;
    
    /**
     * Creates an OpenCraft instance with the specified parameters.
     *
     * @param directory the working directory of the game
     * @param width the window width
     * @param height the window height
     */
    public OpenCraft(File directory, int width, int height) {
        if (directory == null) {
            showMessageDialog(null, INVALID_DIR_MESSAGE, "Failed to instance OpenCraft!", ERROR_MESSAGE);
            System.exit(INVALID_DIR_CODE);
        }

        // Set window properties
        this.width = width;
        this.height = height;

        // Create a new renderer
        this.renderer = new Renderer();
        this.monitorRefreshRate = renderer.getMonitorRefreshRate();
        this.camera = renderer.createCamera();

        // Create a input controller
        im = new InputManager(this);

        // Main thread
        this.thread = new Thread(this);
        this.thread.setName("GameThread");

        this.directory = directory;
    }

    /**
     * Creates an OpenCraft instance with the selected display mode
     *
     * @param directory the working directory of the game
     * @param dm the size of the window
     */
    public OpenCraft(File directory, DisplayMode dm) {
        this(directory, dm.getWidth(), dm.getHeight());
    }

    /**
     * Creates an OpenCraft instance with full screen enabled
     *
     * @param directory the working directory of the game
     */
    public OpenCraft(File directory) {
        this(directory, Display.getDesktopDisplayMode());
    }

    private void tick() {
        this.running = running && !Display.isCloseRequested();
        if (Display.wasResized()) {
            this.width = Display.getWidth();
            this.height = Display.getHeight();
        }
        
        im.handleInput();
        camera.applyMovements();

    }
    
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        checkZoom();
        
        CubeModel cube = new CubeModel(true);
        cube.load();
        {       
            cube.render(0, 0, 0);
        }    
        cube.unload();
    }

    public void run() {
        this.init();
        
        Instant beginTime;
        while (running) {
            // Calculate deltaTime
            beginTime = Instant.now();
            {
                tick();
                render();
                Display.update();
                vsync();
            }
            this.deltaTime = Duration.between(beginTime, Instant.now()).toMillis() / 1000.0d;
        }

        this.stop();
    }
    
    private void vsync() {
        Display.sync(monitorRefreshRate);
    }
    
    private void checkZoom() {
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            if (!didZoom) {
                renderer.updateProjection(45f);
                didZoom = true;
            }
        } else {
            if (didZoom) {
                renderer.updateProjection(70f);
                didZoom = false;
            }
        }
    }

    /**
     * Initializes OpenGL and Inputs.
     *
     * @throws LWJGLException if something failed to start
     * @throws UnsatisfiedLinkError if natives weren't linked
     * @throws Exception if something else failed
     */
    private void initGL() throws LWJGLException, UnsatisfiedLinkError, Exception {
        // Check if full screen is activated
        Display.setFullscreen(Utils.isFullscreen(width, height));

        /* Display */
        Display.setDisplayMode(new DisplayMode(width, height)); // Sets the width and height of the display
        Display.setTitle("OpenCraft ".concat(VERSION_STRING));  // Sets the window title
        Display.setResizable(true);                             // Set resizable
        Utils.setDisplayIcons();                                // Set the icons of the display
        Display.create();                                       // Creates the window

        /* Inputs */
        Keyboard.create(); // Create keyboard
        Mouse.create();    // Create mouse

        // Allow texturization
        renderer.enableTexturization();

        // Enable 3d and optimization tricks
        renderer.enable3D();
        renderer.enableFaceCulling();
        renderer.updateProjection(70F);

        // Enable alpha
        renderer.enableAlpha();
        
        // Setup fog and sky
        renderer.setupFog();
    }

    /**
     * Tries to stop the game saving all resources and stopping the current
     * thread.
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
    @SuppressWarnings("removal")
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
    }

    private void init() {
        try {
            // Try to initialize OpenGL stuff
            this.initGL();
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
