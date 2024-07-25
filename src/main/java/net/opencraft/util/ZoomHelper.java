package net.opencraft.util;

import static net.opencraft.OpenCraft.oc;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ZoomHelper {

    public static float intensity = 45f;
    public static boolean didZoom = false;
    private static boolean mouseWRelative = true;
    
    public static void setIntensity(float intensity) {
        ZoomHelper.intensity = Math.clamp(intensity, 10f, 69f);
    }
    
    public static float getIntensity() {
        return intensity;
    }
    
    public static void setMouseWheelRelative(boolean flag) {
        ZoomHelper.mouseWRelative = flag;
    }
    
    public static void update() {
        if (mouseWRelative) {
            float mwheel = Mouse.getDWheel() / 100.0f;
            setIntensity(intensity - mwheel);
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            if (!didZoom) {
                oc.renderer.setFOV(intensity);
                didZoom = true;
            }
        } else {
            if (didZoom) {
                oc.renderer.setFOV(70f);
                didZoom = false;
            }
        }
    }
    
    
    
}
