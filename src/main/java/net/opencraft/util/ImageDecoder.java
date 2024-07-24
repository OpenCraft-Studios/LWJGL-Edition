package net.opencraft.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
        IntBuffer pixels_int = pixels.asIntBuffer();
        pixels_int.put(pixels_raw);
        pixels_int.flip();

        return pixels;
    }

}
