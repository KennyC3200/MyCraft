package MyCraft.player;

import MyCraft.gfx.*;
import MyCraft.input.*;
import MyCraft.world.*;

import org.joml.Vector3f;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Camera {

    private Vector3f position;
    private Vector3f direction;

    /* Camera vectors that define the vector space */
    private Vector3f up, right, front;

    /* View and projection 4x4 matrices */
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

        // Update the view and projection matrices
        try (MemoryStack stack = MemoryStack.stackPush()) {
            view = new Matrix4f()
                .lookAt(position, new Vector3f(position).add(direction), up)
                .get(stack.mallocFloat(16));
            ChunkMesh.shader.uniformMatrix4f("view", view); 

            projection = new Matrix4f()
                .perspective(
                    fov,
                    (float) window.getSize().x / (float) window.getSize().y, 
                    zNear, zFar
                ).get(stack.mallocFloat(16));
            ChunkMesh.shader.uniformMatrix4f("projection", projection);
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

}
