package net.opencraft.util;

import java.awt.Color;

public final class FloatColor {

    private final Color c;
    
    public final float r;
    public final float g;
    public final float b;
    public final float a;
    
    public FloatColor(Color c) {
        this.c = c;
        
        this.r = c.getRed() / 255.0f;
        this.g = c.getGreen() / 255.0f;
        this.b = c.getBlue() / 255.0f;
        this.a = c.getAlpha() / 255.0f;
    }
    
    public static FloatColor of(int argb) {
        return new FloatColor(new Color(argb));
    }
    
    public static FloatColor of(Color c) {
        return new FloatColor(c);
    }
    
    public Color getColor() {
        return c;
    }
    
    public boolean isOpaque() {
        return a == 1;
    }
    
}
