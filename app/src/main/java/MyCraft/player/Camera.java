package MyCraft.player;

import MyCraft.gfx.*;
import MyCraft.input.*;
import MyCraft.util.*;

import org.joml.Vector3f;

public class Camera {

    Vector3f position;
    Vector3f direction;

    /* Camera vectors that define the vector space */
    Vector3f up, right, front;

    float yaw, pitch, roll;
    float fov;
    float zNear, zFar;
    float sensitivity;

    boolean toggled;

    private Window window;
    private Mouse mouse;

    /* Init the camera */
    public Camera(Window window, Mouse mouse, Vector3f position) {
        this.window = window;
        this.mouse = mouse;
        this.position = position;

        direction = new Vector3f(0.0f, 0.0f, -1.0f);
        up = new Vector3f(0.0f, 1.0f, 0.0f);

        // Point towards -Z axis
        yaw = (float) Math.toRadians(-90.0f);
        pitch = 0.0f;
        roll = 0.0f;

        fov = (float) Math.toRadians(60.0f);
        zNear = 0.1f;
        zFar = 1000.0f;
        sensitivity = 0.1f;

        toggled = true;
    }

    /* Update the camera */
    public void update() {
        if (toggled) {
            yaw += Math.toRadians(sensitivity * mouse.getPositionDelta().x);
            pitch += Math.toRadians(sensitivity * mouse.getPositionDelta().y);
            pitch = MathUtil.clamp(pitch, (float) Math.toRadians(-89.0f), (float) Math.toRadians(89.0f));

            direction = new Vector3f(
                (float) Math.cos(yaw) * (float) Math.cos(pitch),
                (float) Math.sin(pitch),
                (float) Math.sin(yaw) * (float) Math.cos(pitch)
            ).normalize();
            right = new Vector3f(direction).cross(up).normalize();
            // This is the cross product of the up vector and right vector
            // Gives the direction that the player will move in the x and z components
            front = new Vector3f(up).cross(right).normalize();
        }
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getFront() {
        return new Vector3f(front);
    }

    public Vector3f getRight() {
        return new Vector3f(right);
    }

    public void setToggled(boolean bool) {
        toggled = bool;
    }

}
