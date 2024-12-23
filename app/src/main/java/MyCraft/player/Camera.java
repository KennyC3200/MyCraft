package MyCraft.player;

import MyCraft.gfx.*;
import MyCraft.input.*;
import MyCraft.world.*;

import org.joml.*;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Camera {

    private Vector3f position;
    private Vector3f direction;

    /* Camera vectors that define the vector space */
    private Vector3f up, right, front;

    private FloatBuffer view, projection;

    private float yaw, pitch, roll;
    private float fov;
    private float zNear, zFar;
    private float sensitivity;

    private boolean toggled;

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
        yaw = -90.0f;
        pitch = 0.0f;
        roll = 0.0f;

        fov = 60.0f;
        zNear = 0.1f;
        zFar = 1000.0f;
        sensitivity = 0.1f;

        toggled = true;
    }

    /* Update the camera */
    public void update() {
        // Update the view and projection matrices
        try (MemoryStack stack = MemoryStack.stackPush()) {
            view = new Matrix4f()
                .translate(new Vector3f(0.0f, 0.0f, -3.0f))
                .get(stack.mallocFloat(16));
            ChunkMesh.shader.uniformMatrix4f("view", view); 

            projection = new Matrix4f()
                .perspective(
                    (float) java.lang.Math.toRadians(45.0f), 
                    (float) window.getSize().x / (float) window.getSize().y, 
                    0.1f, 
                    100.0f
                ).get(stack.mallocFloat(16));
            ChunkMesh.shader.uniformMatrix4f("projection", projection);
        }
    }

}
