import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class CubeGame {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private static final float MOVE_SPEED = 0.1f;

	private float cameraX = 0.0f;
	private float cameraY = 0.0f;
	private float cameraZ = 0.0f;
	private float cameraYaw = 0.0f;
	private float cameraPitch = 0.0f;

	private boolean mouseLocked = false;

	public CubeGame() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Cube Game");
			setWindowIcon("C:/Users/Ciro/Desktop/block.png");
			Display.create();

			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		init();

		while (!Display.isCloseRequested()) {
			Mouse.poll();
			while (Mouse.next()) {
				// Verificar si se hizo clic dentro de la ventana del juego
				if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) { // Botón izquierdo del ratón
					if (Mouse.getX() >= 0 && Mouse.getX() <= Display.getWidth() && Mouse.getY() >= 0
							&& Mouse.getY() <= Display.getHeight()) {
						// Capturar el ratón
						Mouse.setGrabbed(true);
						mouseLocked = false;
					}
				}
			}

			update();
			render();
			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	private void init() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		float fov = 60.0f; // Nuevo ángulo de apertura (en grados)
		float aspectRatio = (float) Display.getWidth() / Display.getHeight();
		float zNear = 0.1f;
		float zFar = 1000.0f;
		GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		/* Enable 3d */
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		/* Light options */
		glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_NORMALIZE);

		/* Set sky color */
		glClearColor(0, 0.5f, 0.5f, 1);

		/* Enable 2d textures */
		glEnable(GL_TEXTURE_2D);

		/* Enable face culling */
		glEnable(GL_CULL_FACE);
		glCullFace(GL_RIGHT);
	}

	private void drawCrosshair() {
		// Desactivar iluminación y texturas para dibujar la cruz
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);

		// Establecer el color de la cruz a blanco
		glColor3f(1.0f, 1.0f, 1.0f);

		// Obtener las dimensiones de la ventana
		int windowWidth = Display.getWidth();
		int windowHeight = Display.getHeight();

		// Calcular las coordenadas del centro de la pantalla
		float centerX = windowWidth / 2.0f;
		float centerY = windowHeight / 2.0f;

		// Establecer el tamaño de la cruz
		float crosshairSize = 23.0f; // Ajustar este valor para cambiar el tamaño de la cruz

		// Establecer el grosor de la cruz
		float crosshairThickness = 1.5f; // Ajustar este valor para cambiar el grosor de la cruz

		// Configurar OpenGL para el modo de proyección ortográfica 2D
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, windowWidth, windowHeight, 0, -1, 1); // Coordenadas de pantalla

		// Cambiar al modo de modelo de vista
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();

		// Dibujar la línea horizontal de la cruz
		glLineWidth(crosshairThickness); // Establecer el grosor de la línea
		glBegin(GL_LINES);
		glVertex2f(centerX - crosshairSize / 2, centerY); // Desde el punto a la izquierda del centro
		glVertex2f(centerX + crosshairSize / 2, centerY); // Hasta el punto a la derecha del centro
		glEnd();

		// Dibujar la línea vertical de la cruz
		glBegin(GL_LINES);
		glVertex2f(centerX, centerY - crosshairSize / 2); // Desde el punto arriba del centro
		glVertex2f(centerX, centerY + crosshairSize / 2); // Hasta el punto abajo del centro
		glEnd();

		// Restaurar la configuración de OpenGL
		glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);

		// Reestablecer el estado de OpenGL
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
	}

	private void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
			mouseLocked = true;
		}

		if (!mouseLocked) {
			int deltaX = Mouse.getDX();
			int deltaY = Mouse.getDY();

			// Sensibilidad ajustable para el movimiento del ratón
			float sensitivity = 0.1f;

			cameraYaw += deltaX * sensitivity;
			cameraPitch -= deltaY * sensitivity;

			// Limitar el ángulo de rotación de cameraPitch entre -90 y 90 grados
			if (cameraPitch > 90.0f) {
				cameraPitch = 90.0f;
			} else if (cameraPitch < -90.0f) {
				cameraPitch = -90.0f;
			}
		}

		// Movimiento hacia adelante (W)
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			cameraX += MOVE_SPEED * Math.sin(Math.toRadians(cameraYaw));
			cameraZ -= MOVE_SPEED * Math.cos(Math.toRadians(cameraYaw));
		}

		// Movimiento hacia la izquierda (A)
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			cameraX -= MOVE_SPEED * Math.sin(Math.toRadians(cameraYaw + 90));
			cameraZ += MOVE_SPEED * Math.cos(Math.toRadians(cameraYaw + 90));
		}

		// Movimiento hacia atrás (S)
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			cameraX -= MOVE_SPEED * Math.sin(Math.toRadians(cameraYaw));
			cameraZ += MOVE_SPEED * Math.cos(Math.toRadians(cameraYaw));
		}

		// Movimiento hacia la derecha (D)
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			cameraX += MOVE_SPEED * Math.sin(Math.toRadians(cameraYaw + 90));
			cameraZ -= MOVE_SPEED * Math.cos(Math.toRadians(cameraYaw + 90));
		}

		// Movimiento hacia arriba (Espacio)
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			cameraY += MOVE_SPEED;
		}

		// Movimiento hacia abajo (Shift)
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			cameraY -= MOVE_SPEED;
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();
		glRotatef(cameraPitch, 1.0f, 0.0f, 0.0f);
		glRotatef(cameraYaw, 0.0f, 1.0f, 0.0f);

		glTranslatef(-cameraX, -cameraY, -cameraZ);

		renderCube(0.0f, 0.0f, 0.0f);
		renderCube(2.0f, 2.0f, -5.0f);
		renderCube(-2.0f, -2.0f, -5.0f);
		drawCrosshair();
	}

	private void renderCube(float x, float y, float z) {
		glTranslatef(x, y, z);
		
		final float BLOCK_SIZE = 1.0f;
		float halfBlockSize = BLOCK_SIZE / 2.0f;
		float xMin = x - halfBlockSize;
		float xMax = x + halfBlockSize;
		float yMin = y - halfBlockSize;
		float yMax = y + halfBlockSize;
		float zMin = z - halfBlockSize;
		float zMax = z + halfBlockSize;

		float[] vertices = {
			    // Cara frontal
			    xMin, yMin, zMax,  // Vértice 0
			    xMax, yMin, zMax,  // Vértice 1
			    xMax, yMax, zMax,  // Vértice 2
			    xMin, yMax, zMax,  // Vértice 3
			    
			    // Cara trasera
			    xMax, yMin, zMin,  // Vértice 4
			    xMin, yMin, zMin,  // Vértice 5
			    xMin, yMax, zMin,  // Vértice 6
			    xMax, yMax, zMin,  // Vértice 7
			    
			    // Cara superior
			    xMin, yMax, zMax,  // Vértice 8
			    xMax, yMax, zMax,  // Vértice 9
			    xMax, yMax, zMin,  // Vértice 10
			    xMin, yMax, zMin,  // Vértice 11
			    
			    // Cara inferior
			    xMin, yMin, zMin,  // Vértice 12
			    xMax, yMin, zMin,  // Vértice 13
			    xMax, yMin, zMax,  // Vértice 14
			    xMin, yMin, zMax,  // Vértice 15
			    
			    // Cara derecha
			    xMax, yMin, zMax,  // Vértice 16
			    xMax, yMin, zMin,  // Vértice 17
			    xMax, yMax, zMin,  // Vértice 18
			    xMax, yMax, zMax,  // Vértice 19
			    
			    // Cara izquierda
			    xMin, yMin, zMin,  // Vértice 20
			    xMin, yMin, zMax,  // Vértice 21
			    xMin, yMax, zMax,  // Vértice 22
			    xMin, yMax, zMin   // Vértice 23
			};


		float[] colors = { 1.0f, 0.0f, 0.0f, // Color del vértice 0 (rojo)
				1.0f, 0.0f, 0.0f, // Color del vértice 1 (rojo)
				1.0f, 0.0f, 0.0f, // Color del vértice 2 (rojo)
				1.0f, 0.0f, 0.0f, // Color del vértice 3 (rojo)
				// Otros colores...
		};

		int[] indices = { 0, 1, 2, // Triángulo 1
				0, 2, 3, // Triángulo 2
				// Otros índices...
		};

		glBegin(GL_TRIANGLES);

		for (int i = 0; i < indices.length; i++) {
			int index = indices[i] * 3; // Cada índice apunta a tres valores en los arrays de vértices, normales y
										// colores
			int vertexIndex = index;
			int normalIndex = i / 6 * 3; // Cada cara tiene dos triángulos
			int colorIndex = normalIndex; // Asignamos el mismo color para cada vértice de la cara

			//glNormal3f(NORMALS[normalIndex], NORMALS[normalIndex + 1], NORMALS[normalIndex + 2]);
			glColor3f(colors[colorIndex], colors[colorIndex + 1], colors[colorIndex + 2]);
			glVertex3f(vertices[vertexIndex], vertices[vertexIndex + 1], vertices[vertexIndex + 2]);
		}

		glEnd();

	}

	private void setWindowIcon(String filename) {
		try {
			// Cargar el archivo de imagen
			InputStream inputStream = new FileInputStream(filename);
			BufferedImage image = ImageIO.read(inputStream);

			// Obtener las dimensiones de la imagen
			int width = image.getWidth();
			int height = image.getHeight();

			// Obtener los píxeles de la imagen como un array de bytes
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);

			// Crear un ByteBuffer para almacenar los datos de los píxeles
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4); // Cada píxel tiene 4 bytes (RGBA)

			// Copiar los píxeles en el ByteBuffer
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = pixels[y * width + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF)); // Componente rojo
					buffer.put((byte) ((pixel >> 8) & 0xFF)); // Componente verde
					buffer.put((byte) (pixel & 0xFF)); // Componente azul
					buffer.put((byte) ((pixel >> 24) & 0xFF)); // Componente alfa
				}
			}

			// Volcar el ByteBuffer para que esté listo para ser leído
			buffer.flip();

			// Establecer el icono de la ventana
			Display.setIcon(new ByteBuffer[] { buffer });

			// Cerrar el flujo de entrada
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new CubeGame();
	}
}
