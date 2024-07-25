package net.opencraft.render.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public final class CubeLoader {
    
    private CubeLoader() {
    }
    
    private static int createVAO() {
        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        
        return vaoId;
    }
    
    private static int createVBO() {
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        
        return vboId;
    }
    
    private static int createEBO() {
        int eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        
        return eboId;
    }
    
    private static FloatBuffer createDirectFloatBuffer(float[] content) {
        FloatBuffer bff = BufferUtils.createFloatBuffer(content.length);
        bff.put(content).flip();
        
        return bff;
    }
    
    private static IntBuffer createDirectIntBuffer(int[] content) {
        IntBuffer bff = BufferUtils.createIntBuffer(content.length);
        bff.put(content).flip();
        
        return bff;
    }
    
    private static void loadBufferToVBO(FloatBuffer buffer) {
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    private static void loadBufferToEBO(IntBuffer buffer) {
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }
    
    private static void disableVAO() {
        glBindVertexArray(0);
    }
    
    public static void load(Model m) {
        m.vaoId = createVAO(); // Create VAO
        m.vboId = createVBO(); // Create VBO for the vertices
        
        FloatBuffer verticesBuffer = createDirectFloatBuffer(m.vertices);
        loadBufferToVBO(verticesBuffer);

        // Create a EBO for the indices
        m.eboId = createEBO();
        
        IntBuffer indicesBuffer = createDirectIntBuffer(m.indices);
        loadBufferToEBO(indicesBuffer); // Load indices data to EBO
        
        disableVAO(); // Disable the VAO
    }
    
}
