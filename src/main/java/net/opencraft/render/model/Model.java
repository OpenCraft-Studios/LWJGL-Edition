package net.opencraft.render.model;

import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Model {

    public int vaoId;
    public int vboId;
    public int eboId;
    
    private CubeLoader loader;
    
    public float[] vertices;
    public int[] indices;
    
    public Model(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }
    
    public void setLoader(CubeLoader l) {
        this.loader = l;
    }
    
    public void load() {}
    
    public void render() {}
    
    public void render(float x, float y, float z) {
        glTranslatef(x, y, z);
        render();
    }
    
    public void render(Vector3f blockpos) {
        render(blockpos.x, blockpos.y, blockpos.z);
    }

}
