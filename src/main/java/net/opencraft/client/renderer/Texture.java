package net.opencraft.client.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {

	private int id;
	private int width;
	private int height;

	public Texture(BufferedImage bi) {
		width = bi.getWidth();
		height = bi.getHeight();

		int[] pixels_raw = new int[width * height * 4];
		pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer pixels = getPixels(pixels_raw);

		id = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
	}

	private ByteBuffer getPixels(int[] pixels_raw) {
		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixel = pixels_raw[i * width + j];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
				pixels.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
				pixels.put((byte) (pixel & 0xFF)); // BLUE
				pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
			}
		}
		
		pixels.flip();
		return pixels;
	}
	
	public static Texture of(Image image) {
		BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return new Texture(bi);
	}

	public static Texture read(InputStream in) {
		Texture tex = null;
		try {
			tex = new Texture(ImageIO.read(in));
		} catch (Exception ignored) {
		}

		return tex;
	}
	
	public static Texture read(File file) {
		try {
			return Texture.read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public static Texture read(String filename) {
		return Texture.read(new File(filename));
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
