package net.opencraft.render.texture;

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
 * <h1>TextureLoader</h1> The TextureLoader class is responsible for loading,
 * binding, and managing textures in OpenGL. This class uses LWJGL 2 for OpenGL
 * bindings and javax.imageio.ImageIO for image loading. It supports loading
 * textures from PNG files.
 */
public class TextureLoader {

    private static final BufferedImage MISSIGNO_IMAGE;

    static {
        MISSIGNO_IMAGE = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        {
            MISSIGNO_IMAGE.setRGB(0, 0, 0xFF00FF); // MAGENTA
            MISSIGNO_IMAGE.setRGB(0, 1, 0x000000); // BLACK
            MISSIGNO_IMAGE.setRGB(1, 0, 0x000000); // BLACK
            MISSIGNO_IMAGE.setRGB(1, 1, 0xFF00FF); // MAGENTA
        }
    }

    private TextureLoader() {
    }

    public static Texture getTexture(BufferedImage bi) {
        return new Texture(bi);
    }

    public static Texture getTexture(final String format, final String path) {
        return getTexture(read(path));
    }

    private static BufferedImage read(String path) {
        BufferedImage img = MISSIGNO_IMAGE;

        try {
            // Try to load the texture
            img = ImageIO.read(TextureLoader.class.getResourceAsStream(path));
        } catch (Exception ignored) {
        }

        return img;
    }

}
