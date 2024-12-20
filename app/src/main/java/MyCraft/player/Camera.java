package MyCraft.player;

import MyCraft.gfx.*;
import MyCraft.input.*;

import org.joml.*;

public class Camera {

    private Vector3f position;
    private Vector3f direction;

    /* Camera vectors that define the vector space */
    private Vector3f up, right, front;

    private Matrix4f view, projection;

    private float yaw, pitch, roll;
    private float fov;
    private float zNear, zFar;
    private float sensitivity;

    private boolean toggled;

    private Window window;
    private Mouse mouse;

    public Camera(Window window, Mouse mouse, Vector3f position) {
        this.window = window;
        this.mouse = mouse;
        this.position = position;

        direction = new Vector3f(0.0f, 0.0f, -1.0f);
        up = new Vector3f(0.0f, 1.0f, 0.0f);

        // Point towards -Z axis
        yaw = -90.0f;
        pitch = 0.0f;
        roll = 0.0f;

        fov = 60.0f;
        zNear = 0.1f;
        zFar = 1000.0f;
        sensitivity = 0.1f;

        toggled = true;
    }

    public void update() {
    }

}
