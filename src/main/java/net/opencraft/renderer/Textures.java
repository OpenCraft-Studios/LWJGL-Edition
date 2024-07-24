package net.opencraft.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.ByteBuffer;
import java.util.*;

import javax.imageio.ImageIO;

import net.opencraft.util.ImageDecoder;

/**
 * <h1>Texture</h1> The Texture class is responsible for loading, binding, and
 * managing textures in OpenGL. This class uses LWJGL 2 for OpenGL bindings and
 * javax.imageio.ImageIO for image loading. It supports loading textures from
 * PNG files.
 */
public final class Textures {

    private static Map<Image, Integer> idMap = new HashMap<>();

    public static int loadTexture(String resourceName) {
        return loadTexture(read(resourceName));
    }

    public static int loadTexture(final Image img, final ImageObserver observer) {
        Objects.requireNonNull(img, "specified image must not be null!");

        if (img instanceof BufferedImage) {
            return loadTexture((BufferedImage) img);
        }

        BufferedImage bi = new BufferedImage(img.getWidth(observer), img.getHeight(observer),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(img, 0, 0, observer);
        g2d.dispose();

        return loadTexture(bi);
    }

    public static int loadTexture(BufferedImage image) {
        Objects.requireNonNull(image, "specified image must not be null!");
        if (idMap.containsKey(image)) {
            return idMap.get(image);
        }

        final int width = image.getWidth();
        final int height = image.getHeight();

        ByteBuffer pixels = ImageDecoder.decode(image);

        final int textureID = glGenTextures();
        idMap.put(image, textureID);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        gluBuild2DMipmaps(GL_TEXTURE_2D, GL_RGBA, width, height, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        return textureID;
    }

    public static BufferedImage read(String resourceName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(Textures.class.getResourceAsStream(resourceName));
        } catch (Exception ignored) {
            img = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
            img.setRGB(0, 0, Color.MAGENTA.getRGB());
            img.setRGB(0, 1, Color.BLACK.getRGB());
            img.setRGB(1, 0, Color.BLACK.getRGB());
            img.setRGB(1, 1, Color.MAGENTA.getRGB());
        }

        return img;
    }
}
