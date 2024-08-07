package net.opencraft.util;

import net.opencraft.OpenCraft;
import net.opencraft.entity.Entity;
import static org.joml.Math.cos;
import static org.joml.Math.sin;
import static org.joml.Math.toRadians;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputManager {

    public static final float SENSIVILITY = 5;
    
    private final OpenCraft oc;

    public InputManager(OpenCraft oc) {
        this.oc = oc;
    }

    public void handleInput() {
        Mouse.poll();
        Keyboard.poll();

        handleMouse(oc.deltaTime);
        handleKeyboard(oc.deltaTime);
    }

    private void handleMouse(double deltaTime) {
        while (!Mouse.isGrabbed() && Mouse.next()) {
            if (!(Mouse.getEventButtonState()
                    && Mouse.getEventButton() == 0
                    && Mouse.isInsideWindow())) {
                continue;
            }

            Mouse.setGrabbed(true);
        }

        if (!Mouse.isGrabbed()) {
            return;
        }
        
        int deltaX = Mouse.getDX();
        int deltaY = Mouse.getDY();

        oc.camera.yaw += deltaTime * deltaX * SENSIVILITY;
        oc.camera.pitch -= deltaTime * deltaY * SENSIVILITY;

        if (oc.camera.pitch > 90.0f) {
            oc.camera.pitch = 90.0f;
        } else if (oc.camera.pitch < -90.0f) {
            oc.camera.pitch = -90.0f;
        }

    }

    private void handleKeyboard(double deltaTime) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(false);
        }

        if (!Mouse.isGrabbed()) {
            return;
        }
        
        boolean isCtrlPressed = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        float speed = isCtrlPressed ? Entity.RUNNING_SPEED : Entity.SPEED;
        speed *= (float) deltaTime;

        // Backwards and forwards
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            oc.camera.x += speed * sin(toRadians(oc.camera.yaw));
            oc.camera.z -= speed * cos(toRadians(oc.camera.yaw));
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            oc.camera.x -= speed * sin(toRadians(oc.camera.yaw));
            oc.camera.z += speed * cos(toRadians(oc.camera.yaw));
        }

        // Left and right
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            oc.camera.x += speed * sin(toRadians(oc.camera.yaw + 90));
            oc.camera.z -= speed * cos(toRadians(oc.camera.yaw + 90));
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            oc.camera.x -= speed * sin(toRadians(oc.camera.yaw + 90));
            oc.camera.z += speed * cos(toRadians(oc.camera.yaw + 90));
        }
    }

}
