package net.opencraft.render;

import net.opencraft.render.model.CubeModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class CubeRenderer {

    private CubeRenderer() {
    }
    
    public static void render(CubeModel c) {
        // Activar el VAO
        GL30.glBindVertexArray(c.vaoId);
        GL20.glEnableVertexAttribArray(0);

        // Dibujar el cubo usando los indices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, c.eboId);
        GL11.glDrawElements(GL11.GL_TRIANGLES, c.indices.length, GL11.GL_UNSIGNED_INT, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // Desactivar los atributos de los vertices
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
    
}
