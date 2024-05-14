package net.op.render.model;

import static org.lwjgl.opengl.GL11.*;

public class PlayerModel implements Model {

	private static final float[] VERTICES = {
		    // Posiciones         // Normales
		    // Cara frontal
		    -0.5f,  1.0f,  0.5f,   0.0f,  1.0f,  0.0f,  // Arriba-izquierda
		     0.5f,  1.0f,  0.5f,   0.0f,  1.0f,  0.0f,  // Arriba-derecha
		     0.5f,  0.0f,  0.5f,   0.0f,  1.0f,  0.0f,  // Abajo-derecha
		    -0.5f,  0.0f,  0.5f,   0.0f,  1.0f,  0.0f,  // Abajo-izquierda
		    // Cara trasera
		    -0.5f,  1.0f, -0.5f,   0.0f,  0.0f, -1.0f,  // Arriba-izquierda
		     0.5f,  1.0f, -0.5f,   0.0f,  0.0f, -1.0f,  // Arriba-derecha
		     0.5f,  0.0f, -0.5f,   0.0f,  0.0f, -1.0f,  // Abajo-derecha
		    -0.5f,  0.0f, -0.5f,   0.0f,  0.0f, -1.0f,  // Abajo-izquierda
		    // Cara superior
		    -0.5f,  1.0f,  0.5f,   0.0f,  1.0f,  0.0f,  // Arriba-izquierda
		     0.5f,  1.0f,  0.5f,   0.0f,  1.0f,  0.0f,  // Arriba-derecha
		     0.5f,  1.0f, -0.5f,   0.0f,  1.0f,  0.0f,  // Abajo-derecha
		    -0.5f,  1.0f, -0.5f,   0.0f,  1.0f,  0.0f,  // Abajo-izquierda
		    // Cara inferior
		    -0.5f,  0.0f,  0.5f,   0.0f, -1.0f,  0.0f,  // Arriba-izquierda
		     0.5f,  0.0f,  0.5f,   0.0f, -1.0f,  0.0f,  // Arriba-derecha
		     0.5f,  0.0f, -0.5f,   0.0f, -1.0f,  0.0f,  // Abajo-derecha
		    -0.5f,  0.0f, -0.5f,   0.0f, -1.0f,  0.0f,  // Abajo-izquierda
		    // Cara izquierda
		    -0.5f,  1.0f,  0.5f,  -1.0f,  0.0f,  0.0f,  // Arriba-izquierda
		    -0.5f,  1.0f, -0.5f,  -1.0f,  0.0f,  0.0f,  // Arriba-derecha
		    -0.5f,  0.0f, -0.5f,  -1.0f,  0.0f,  0.0f,  // Abajo-derecha
		    -0.5f,  0.0f,  0.5f,  -1.0f,  0.0f,  0.0f,  // Abajo-izquierda
		    // Cara derecha
		     0.5f,  1.0f,  0.5f,   1.0f,  0.0f,  0.0f,  // Arriba-izquierda
		     0.5f,  1.0f, -0.5f,   1.0f,  0.0f,  0.0f,  // Arriba-derecha
		     0.5f,  0.0f, -0.5f,   1.0f,  0.0f,  0.0f,  // Abajo-derecha
		     0.5f,  0.0f,  0.5f,   1.0f,  0.0f,  0.0f   // Abajo-izquierda
		};

		private static final int[] INDICES = {
		    // Frente
		    0, 1, 2,
		    2, 3, 0,
		    // Atrás
		    4, 5, 6,
		    6, 7, 4,
		    // Superior
		    8, 9, 10,
		    10, 11, 8,
		    // Inferior
		    12, 13, 14,
		    14, 15, 12,
		    // Izquierda
		    16, 17, 18,
		    18, 19, 16,
		    // Derecha
		    20, 21, 22,
		    22, 23, 20
		};

		private static final float[] NORMALS = {
		    // Normales de las caras
		    // Frente
		    0.0f, 0.0f, 1.0f,
		    0.0f, 0.0f, 1.0f,
		    // Atrás
		    0.0f, 0.0f, -1.0f,
		    0.0f, 0.0f, -1.0f,
		    // Superior
		    0.0f, 1.0f, 0.0f,
		    0.0f, 1.0f, 0.0f,
		    // Inferior
		    0.0f, -1.0f, 0.0f,
		    0.0f, -1.0f, 0.0f,
		    // Izquierda
		    -1.0f, 0.0f, 0.0f,
		    -1.0f, 0.0f, 0.0f,
		    // Derecha
		    1.0f, 0.0f, 0.0f,
		    1.0f, 0.0f, 0.0f
		};

	public static final PlayerModel NORMAL = new PlayerModel(0xFFA800);
	
	private final int color;
	
	PlayerModel(final int color) {
		this.color = color;
	}
	
	@Override
	public void render() {
		glBegin(GL_TRIANGLES);

		glColor4f(
				(float) ((color >> 4) & 0xFF) / 255.0f,
				(float) ((color >> 2) & 0xFF) / 255.0f,
				(float) (color & 0xFF) / 255.0f,
				1.0f
		);
		
		for (int i = 0; i < INDICES.length; i += 3) {
			// Obtener los índices de los vértices del triángulo
			int index1 = INDICES[i];
			int index2 = INDICES[i + 1];
			int index3 = INDICES[i + 2];

			// Obtener las coordenadas de los vértices del triángulo
			float x1 = VERTICES[index1 * 6];
			float y1 = VERTICES[index1 * 6 + 1];
			float z1 = VERTICES[index1 * 6 + 2];

			float x2 = VERTICES[index2 * 6];
			float y2 = VERTICES[index2 * 6 + 1];
			float z2 = VERTICES[index2 * 6 + 2];

			float x3 = VERTICES[index3 * 6];
			float y3 = VERTICES[index3 * 6 + 1];
			float z3 = VERTICES[index3 * 6 + 2];

			// Obtener las normales de los vértices del triángulo
			float nx1 = NORMALS[index1 * 6];
			float ny1 = NORMALS[index1 * 6 + 1];
			float nz1 = NORMALS[index1 * 6 + 2];

			float nx2 = NORMALS[index2 * 6];
			float ny2 = NORMALS[index2 * 6 + 1];
			float nz2 = NORMALS[index2 * 6 + 2];

			float nx3 = NORMALS[index3 * 6];
			float ny3 = NORMALS[index3 * 6 + 1];
			float nz3 = NORMALS[index3 * 6 + 2];

			// Dibujar el triángulo con sus normales
			glNormal3f(nx1, ny1, nz1);
			glVertex3f(x1, y1, z1);

			glNormal3f(nx2, ny2, nz2);
			glVertex3f(x2, y2, z2);

			glNormal3f(nx3, ny3, nz3);
			glVertex3f(x3, y3, z3);
		}
		glEnd();

	}

}
