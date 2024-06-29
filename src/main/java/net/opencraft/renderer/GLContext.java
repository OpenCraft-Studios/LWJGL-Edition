package net.opencraft.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import org.lwjgl.opengl.Display;

/**
 * <h1>GLContext</h1> This class is used to implement Java Graphics to OpenGL.
 * OpenGL can be messy sometimes, so we decided to implement this class to code
 * much faster.
 * 
 * @author Ciro
 * @since 1.0.0
 */
public class GLContext extends Graphics {

	/**
	 * The global instance of this class.
	 */
	public static final GLContext instance = new GLContext();

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
		return new Font("Consolas", Font.PLAIN, 12);
	}

	@Override
	public void setFont(Font font) {
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

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glOrtho(x, width, height, y, -1, 1);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
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
	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

}
