package net.opencraft.render.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import net.opencraft.util.ImageDecoder;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public final class Texture {

    private final BufferedImage img;
    private int texId;

    Texture(BufferedImage img) {
        this.img = img;
        this.texId = -1; // The texture isn't loaded yet!
    }

    public Texture load() {
        if (isLoaded()) {
            return this;
        }

        final int width = img.getWidth();
        final int height = img.getHeight();

        ByteBuffer pixels = ImageDecoder.decode(img);
        this.texId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        gluBuild2DMipmaps(GL_TEXTURE_2D, GL_RGBA, width, height, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        return this;
    }
    
    public int bind() {
        if (!isLoaded())
            throw new IllegalStateException("Texture has not been loaded.");
        
        glBindTexture(GL_TEXTURE_2D, texId); // Link the texture
        return texId;
    }

    public Texture unload() {
        // If the texture doesn't exists we don't need to unlink it
        if (!isLoaded()) {
            return this;
        }

        glBindTexture(GL_TEXTURE_2D, 0); // Unlink the texture
        return this;
    }

    public void cleanup() {
        // If the texture doesn't exists we don't need to do a cleanup
        if (!isLoaded()) {
            return;
        }

        glDeleteTextures(texId); // Delete the texture
        texId = -1;
    }
    
    public boolean isLoaded() {
        return texId != -1;
    }
    
    public int getId() {
        return texId;
    }
    
    public BufferedImage toJavaImage() {
        return img;
    }
}
