package net.opencraft.render.model;

import net.opencraft.render.CubeRenderer;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class CubeModel extends Model {

    public static final CubeModel NORMAL = new CubeModel(true);
    public static final CubeModel INVISIBLE = new CubeModel(false);
    
    private final boolean visible;
    
    public CubeModel(final boolean visible) {
        super(new float[]{
            -0.5f, 0.5f, -0.5f, // 0
            -0.5f, -0.5f, -0.5f, // 1
            0.5f, -0.5f, -0.5f, // 2
            0.5f, 0.5f, -0.5f, // 3
            -0.5f, 0.5f, 0.5f, // 4
            -0.5f, -0.5f, 0.5f, // 5
            0.5f, -0.5f, 0.5f, // 6
            0.5f, 0.5f, 0.5f // 7
        }, new int[]{
            0, 1, 2, 2, 3, 0, // Cara frontal
            7, 6, 5, 5, 4, 7, // Cara trasera
            4, 5, 1, 1, 0, 4, // Cara izquierda
            3, 2, 6, 6, 7, 3, // Cara derecha
            4, 0, 3, 3, 7, 4, // Cara superior
            1, 5, 6, 6, 2, 1 // Cara inferior
        }
        );
        
        this.visible = visible;
    }

    public void load() {
        CubeLoader.load(this);
    }
    
    @Override
    public void render() {
        if (!visible)
            return;
        
        CubeRenderer.render(this);
    }

    public void unload() {
        glDeleteBuffers(vboId);
        glDeleteBuffers(eboId);
        glDeleteVertexArrays(vaoId);
    }
}
