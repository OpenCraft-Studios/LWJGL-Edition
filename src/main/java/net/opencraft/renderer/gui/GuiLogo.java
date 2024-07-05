package net.opencraft.renderer.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import net.opencraft.renderer.GLGraphicsImpl;
import net.opencraft.renderer.Textures;

public class GuiLogo {

	private BufferedImage texture;

	private int x = 0;
	private int y = 0;

	private int width = 0;
	private int height = 0;

	public GuiLogo() {
		this.texture = Textures.read("/logo.png");
		autosize();
	}

	public void autosize() {
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

}