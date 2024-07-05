package net.opencraft.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public final class ImageDecoder {

	private ImageDecoder() {
	}

	public static ByteBuffer decode(BufferedImage image) {
		final int width = image.getWidth();
		final int height = image.getHeight();

		int[] pixels_raw = new int[width * height];
		image.getRGB(0, 0, width, height, pixels_raw, 0, width);

		ByteBuffer pixels = BufferUtils.createByteBuffer(pixels_raw.length * 4);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels_raw[y * width + x];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
				pixels.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
				pixels.put((byte) (pixel & 0xFF)); // BLUE
				pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
			}
		}

		pixels.flip();
		return pixels;
	}

}
