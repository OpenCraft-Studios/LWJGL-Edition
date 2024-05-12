package net.op.util;

import java.awt.Point;
import java.awt.Rectangle;

import org.joml.Vector2f;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseUtils {

	private MouseUtils() {
	}
	
	public static boolean inRange(Rectangle rect) {
		Point mousePos = new Point(Mouse.getX(), Mouse.getY());
		return rect.contains(mousePos);
	}

	public static boolean inRange(float x, float y, float w, float h) {
		return inRange(new Rectangle((int) x, (int) y, (int) w, (int) h));
	}

	public static boolean inRange(Vector2f pos, float w, float h) {
		return inRange(pos.x, pos.y, w, h);
	}
	
	public static boolean inScreen() {
		int screenWidth, screenHeight;
		screenWidth = Display.getWidth();
		screenHeight = Display.getHeight();
		
		Rectangle screen = new Rectangle(0, 0, screenWidth, screenHeight);
		return inRange(screen);
	}

}
