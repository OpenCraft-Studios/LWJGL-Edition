package net.opencraft.render.gui;

import static net.opencraft.OpenCraft.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import net.opencraft.render.GLGraphicsImpl;
import net.opencraft.render.texture.TextureLoader;

public final class GuiLogo {

    private BufferedImage texture;

    private int x = 0;
    private int y = 0;

    private int width = 0;
    private int height = 0;

    public GuiLogo() {
        this.texture = TextureLoader.getTexture("PNG", "/logo.png").toJavaImage();
        autosize();
    }

    public final void autosize() {
        setSize(texture.getWidth(), texture.getHeight());
    }

    public void render() {
        GLGraphicsImpl g = GLGraphicsImpl.instance;
        g.drawImage(texture, x, y, width, height, null);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Point p) {
        this.setLocation(p.x, p.y);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(Dimension dim) {
        this.setSize(dim.width, dim.height);
    }

    public void setBounds(int x, int y, int w, int h) {
        setLocation(x, y);
        setSize(w, h);
    }

    public void setBounds(Rectangle rect) {
        this.setBounds(rect.x, rect.y, rect.width, rect.height);
    }

    public void autoBounds() {
        double wsf = 650.0 / 854.0;
        double ar = 88.0 / 548.0;

        int width = (int) (wsf * oc.width);
        int height = (int) (width * ar);

        int x = (oc.width - width) / 2;
        int y = (oc.height - height) / 2;

        this.setBounds(x, y, width, height);
    }

}
