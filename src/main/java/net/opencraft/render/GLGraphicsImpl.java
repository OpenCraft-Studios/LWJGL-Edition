package net.opencraft.render;

import net.opencraft.render.texture.TextureLoader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import net.opencraft.render.texture.Texture;

import org.lwjgl.opengl.*;

/**
 * <h1>GLGraphicsImpl</h1> This class is used to implement Java Graphics to
 * OpenGL. OpenGL can be messy sometimes, so we decided to implement this class
 * to code much faster.
 *
 * @author Ciro
 * @since 1.0.0
 */
public class GLGraphicsImpl extends Graphics {

    /**
     * The global instance of this class.
     */
    public static final GLGraphicsImpl instance = new GLGraphicsImpl();

    private Font font;

    public GLGraphicsImpl() {
        this.font = new Font("Consolas", Font.PLAIN, 12);
    }

    @Override
    public Graphics create() {
        return this;
    }

    @Override
    public void translate(int x, int y) {
        glTranslatef(x, y, 0);
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public void setColor(Color c) {
        float red = (float) c.getRed() / 255f;
        float green = (float) c.getGreen() / 255f;
        float blue = (float) c.getBlue() / 255f;
        float alpha = (float) c.getAlpha() / 255f;

        glColor4f(red, green, blue, alpha);
    }

    @Override
    public void setPaintMode() {
    }

    @Override
    public void setXORMode(Color c1) {
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return null;
    }

    @Override
    public Rectangle getClipBounds() {
        return null;
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        glViewport(x, y, width, height);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {
        this.setClip(x, y, width, height);
    }

    @Override
    public Shape getClip() {
        return new Rectangle(0, 0, Display.getWidth(), Display.getHeight());
    }

    @Override
    public void setClip(Shape clip) {
        if (clip instanceof Rectangle) {
            Rectangle rect = (Rectangle) clip;
            this.clipRect(rect.x, rect.y, rect.width, rect.height);
        }
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        // TODO OPENGL COPY_AREA
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        glBegin(GL_LINES);
        {
            glVertex2i(x1, y1);
            glVertex2i(x2, y2);
        }
        glEnd();
    }

    @Override
    public void fillRect(int x, int y, int w, int h) {
        glBegin(GL_QUADS);
        {
            glVertex2i(x, y);
            glVertex2i(x, y + h);
            glVertex2i(x + w, y + h);
            glVertex2i(x + w, y);
        }
        glEnd();
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
        setColor(Color.BLACK);
        fillRect(x, y, width, height);
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void drawString(String str, int x, int y) {

    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        String str = new String();
        for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
            str += c;
        }

        drawString(str, x, y);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        BufferedImage bi = new BufferedImage(img.getWidth(observer), img.getHeight(observer), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(img, 0, 0, observer);
        g2d.dispose();
        
        Texture tex = TextureLoader.getTexture(bi);
        tex.load().bind();

        glColor4f(1F, 1F, 1F, 1F);
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(x, y);

            glTexCoord2f(1, 0);
            glVertex2f(x + width, y);

            glTexCoord2f(1, 1);
            glVertex2f(x + width, y + height);

            glTexCoord2f(0, 1);
            glVertex2f(x, y + height);
        }
        glEnd();

        return true;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), observer);
    }

    @Override
    public boolean drawImage(Image img0, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(bgcolor);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2d.drawImage(img0, 0, 0, null);
        g2d.dispose();

        return drawImage(img, 0, 0, width, height, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
            ImageObserver observer) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
            Color bgcolor, ImageObserver observer) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void dispose() {
    }

}
