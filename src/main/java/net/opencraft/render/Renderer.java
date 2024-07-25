package net.opencraft.render;

import java.awt.GraphicsEnvironment;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Renderer {

    private FloatBuffer fogColor0 = BufferUtils.createFloatBuffer(4);
    private FloatBuffer fogColor1 = BufferUtils.createFloatBuffer(4);

    private final int monitorRefreshRate;
    
    public Camera camera;
    public float fov;

    public Renderer() {
        monitorRefreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    }

    public void enableTexturization() {
        glEnable(GL_TEXTURE_2D);
    }

    public void enable3D() {
        glEnable(GL_DEPTH_TEST);
        glShadeModel(GL_SMOOTH);
        glDepthFunc(GL_LEQUAL);
    }

    public void enableFaceCulling() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    public void enableAlpha() {
        glEnable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glAlphaFunc(GL_GREATER, 0);
    }

    public void updateProjection() {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        setupPerspective(fov);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void setupPerspective(float fov) {
        gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), 0.1F, 100F);
    }

    public void setupFog() {
        int col1 = 920330;
        float fr = 0.5F;
        float fg = 0.8F;
        float fb = 1.0F;
        this.fogColor0.put(new float[]{fr, fg, fb, 1.0F});
        this.fogColor0.flip();
        this.fogColor1.put(new float[]{(col1 >> 16 & 0xFF) / 255.0F, (col1 >> 8 & 0xFF) / 255.0F, (col1 & 0xFF) / 255.0F, 1.0F});
        this.fogColor1.flip();

        glClearColor(fr, fg, fb, 0);
        glClearDepth(1f);
    }

    public Camera createCamera() {
        if (camera == null) {
            camera = new Camera();
        }

        return camera;
    }
    
    public void setFOV(float fov) {
        this.fov = fov;
        updateProjection();
    }

    public void vsync() {
        Display.update();
        Display.sync(monitorRefreshRate);
    }

}
