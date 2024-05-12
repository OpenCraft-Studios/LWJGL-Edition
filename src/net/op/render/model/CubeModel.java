package net.op.render.model;

import static org.lwjgl.opengl.GL11.*;

public class CubeModel implements Model {

	private static final float BLOCK_SIZE = 0.8f;
	private static final float MIN = -0.5f * BLOCK_SIZE;
	private static final float MAX = 0.5f * BLOCK_SIZE;
	
	private static final float[] VERTICES = {
		    // Cara frontal
		    MIN, MIN, MAX,  // Vértice 0
		    MAX, MIN, MAX,  // Vértice 1
		    MAX, MAX, MAX,  // Vértice 2
		    MIN, MAX, MAX,  // Vértice 3
		    
		    // Cara trasera
		    MAX, MIN, MIN,  // Vértice 4
		    MIN, MIN, MIN,  // Vértice 5
		    MIN, MAX, MIN,  // Vértice 6
		    MAX, MAX, MIN,  // Vértice 7
		    
		    // Cara superior
		    MIN, MAX, MAX,  // Vértice 8
		    MAX, MAX, MAX,  // Vértice 9
		    MAX, MAX, MIN,  // Vértice 10
		    MIN, MAX, MIN,  // Vértice 11
		    
		    // Cara inferior
		    MIN, MIN, MIN,  // Vértice 12
		    MAX, MIN, MIN,  // Vértice 13
		    MAX, MIN, MAX,  // Vértice 14
		    MIN, MIN, MAX,  // Vértice 15
		    
		    // Cara derecha
		    MAX, MIN, MAX,  // Vértice 16
		    MAX, MIN, MIN,  // Vértice 17
		    MAX, MAX, MIN,  // Vértice 18
		    MAX, MAX, MAX,  // Vértice 19
		    
		    // Cara izquierda
		    MIN, MIN, MIN,  // Vértice 20
		    MIN, MIN, MAX,  // Vértice 21
		    MIN, MAX, MAX,  // Vértice 22
		    MIN, MAX, MIN   // Vértice 23
		};
	
	private static final float[] NORMALS = {
		    // Cara frontal
		    0.0f, 0.0f, 1.0f,  // Normal de la cara frontal
		    0.0f, 0.0f, 1.0f,  // Normal de la cara frontal
		    0.0f, 0.0f, 1.0f,  // Normal de la cara frontal
		    0.0f, 0.0f, 1.0f,  // Normal de la cara frontal
		    
		    // Cara trasera
		    0.0f, 0.0f, -1.0f,  // Normal de la cara trasera
		    0.0f, 0.0f, -1.0f,  // Normal de la cara trasera
		    0.0f, 0.0f, -1.0f,  // Normal de la cara trasera
		    0.0f, 0.0f, -1.0f,  // Normal de la cara trasera
		    
		    // Cara superior
		    0.0f, 1.0f, 0.0f,  // Normal de la cara superior
		    0.0f, 1.0f, 0.0f,  // Normal de la cara superior
		    0.0f, 1.0f, 0.0f,  // Normal de la cara superior
		    0.0f, 1.0f, 0.0f,  // Normal de la cara superior
		    
		    // Cara inferior
		    0.0f, -1.0f, 0.0f,  // Normal de la cara inferior
		    0.0f, -1.0f, 0.0f,  // Normal de la cara inferior
		    0.0f, -1.0f, 0.0f,  // Normal de la cara inferior
		    0.0f, -1.0f, 0.0f,  // Normal de la cara inferior
		    
		    // Cara derecha
		    1.0f, 0.0f, 0.0f,  // Normal de la cara derecha
		    1.0f, 0.0f, 0.0f,  // Normal de la cara derecha
		    1.0f, 0.0f, 0.0f,  // Normal de la cara derecha
		    1.0f, 0.0f, 0.0f,  // Normal de la cara derecha
		    
		    // Cara izquierda
		    -1.0f, 0.0f, 0.0f,  // Normal de la cara izquierda
		    -1.0f, 0.0f, 0.0f,  // Normal de la cara izquierda
		    -1.0f, 0.0f, 0.0f,  // Normal de la cara izquierda
		    -1.0f, 0.0f, 0.0f   // Normal de la cara izquierda
		};

	private static final int[] INDICES = {
			// Cara frontal
			0, 1, 2, // Triángulo 1
			0, 2, 3, // Triángulo 2

			// Cara posterior
			4, 5, 6, // Triángulo 1
			4, 6, 7, // Triángulo 2

			// Cara superior
			8, 9, 10, // Triángulo 1
			8, 10, 11, // Triángulo 2

			// Cara inferior
			12, 13, 14, // Triángulo 1
			12, 14, 15, // Triángulo 2

			// Cara lateral izquierda
			16, 17, 18, // Triángulo 1
			16, 18, 19, // Triángulo 2

			// Cara lateral derecha
			20, 21, 22, // Triángulo 1
			20, 22, 23 // Triángulo 2
	};

	private static final float[] COLORS = {
			// Cara frontal (rojo)
			1.0f, 0.0f, 0.0f, 1.0f, // V0
			1.0f, 0.0f, 0.0f, 1.0f, // V1
			1.0f, 0.0f, 0.0f, 1.0f, // V2
			1.0f, 0.0f, 0.0f, 1.0f, // V3

			// Cara posterior (verde)
			0.0f, 1.0f, 0.0f, 1.0f, // V4
			0.0f, 1.0f, 0.0f, 1.0f, // V5
			0.0f, 1.0f, 0.0f, 1.0f, // V6
			0.0f, 1.0f, 0.0f, 1.0f, // V7

			// Cara superior (azul)
			0.0f, 0.0f, 1.0f, 1.0f, // V8
			0.0f, 0.0f, 1.0f, 1.0f, // V9
			0.0f, 0.0f, 1.0f, 1.0f, // V10
			0.0f, 0.0f, 1.0f, 1.0f, // V11

			// Cara inferior (amarillo)
			1.0f, 1.0f, 0.0f, 1.0f, // V12
			1.0f, 1.0f, 0.0f, 1.0f, // V13
			1.0f, 1.0f, 0.0f, 1.0f, // V14
			1.0f, 1.0f, 0.0f, 1.0f, // V15

			// Cara lateral izquierda (magenta)
			1.0f, 0.0f, 1.0f, 1.0f, // V16
			1.0f, 0.0f, 1.0f, 1.0f, // V17
			1.0f, 0.0f, 1.0f, 1.0f, // V18
			1.0f, 0.0f, 1.0f, 1.0f, // V19

			// Cara lateral derecha (cyan)
			0.0f, 1.0f, 1.0f, 1.0f, // V20
			0.0f, 1.0f, 1.0f, 1.0f, // V21
			0.0f, 1.0f, 1.0f, 1.0f, // V22
			0.0f, 1.0f, 1.0f, 1.0f // V23
	};
	
	public static final CubeModel COLORIZED = new CubeModel(true, true);
	public static final CubeModel UNCOLORED = new CubeModel(true, false);
	public static final CubeModel INVISIBLE = new CubeModel(false, false);
	
	private final boolean coloring;
	private final boolean visible;
	
	private CubeModel(final boolean visible, final boolean coloring) {
		this.visible = visible;
		this.coloring = coloring;
	}
	
	public void render() {
		if (!visible)
			return;
		
		glBegin(GL_TRIANGLES);
		for (int i = 0; i < INDICES.length; i++) {
			int index = INDICES[i] * 3;
			
			int vertexIndex = index;
			int normalIndex = i / 6 * 3; // i / 2

			if (coloring) {
				int colorIndex = normalIndex;
				glColor3f(COLORS[colorIndex], COLORS[colorIndex + 1], COLORS[colorIndex + 2]);
			}

			glNormal3f(NORMALS[normalIndex], NORMALS[normalIndex + 1], NORMALS[normalIndex + 2]);
			glVertex3f(VERTICES[vertexIndex], VERTICES[vertexIndex + 1], VERTICES[vertexIndex + 2]);
		}
		glEnd();
	}

}
