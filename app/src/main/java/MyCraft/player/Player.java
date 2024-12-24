package MyCraft.player;

import MyCraft.util.*;
import MyCraft.gfx.*;
import MyCraft.input.*;
import MyCraft.world.*;

import org.lwjgl.*;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import org.joml.*;

import java.nio.FloatBuffer;

public class Player {

    private Window window;
    private Keyboard keyboard;
    private Mouse mouse;
    private World world;

    private Vector3f offset;
    private Vector3f position;

    private float height;
    private float speed;

    private Camera camera;

    /* Init the player */
    public Player(Window window, Keyboard keyboard, Mouse mouse, World world) {
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.world = world;

        height = 1.8f;
        speed = 15.0f;

        offset = new Vector3f(
            world.getChunksSize().x * Chunk.size.x / 2,
            world.getChunksSize().y * Chunk.size.y / 2 + height,
            world.getChunksSize().z * Chunk.size.z / 2
        );
        position = new Vector3f(offset);

        camera = new Camera(window, mouse, new Vector3f(position));
    }

    /* Update the player */
    public void update() {
        float displacement = speed * (float) window.getTimeDelta();
        if (keyboard.getButton(GLFW_KEY_W).down) {
            position.add(camera.getFront().mul(displacement));
        }
        if (keyboard.getButton(GLFW_KEY_S).down) {
            position.sub(camera.getFront().mul(displacement));
        }
        if (keyboard.getButton(GLFW_KEY_A).down) {
            position.sub(camera.getRight().mul(displacement));
        }
        if (keyboard.getButton(GLFW_KEY_D).down) {
            position.add(camera.getRight().mul(displacement));
        }
        if (keyboard.getButton(GLFW_KEY_SPACE).down) {
            position.y += 0.5 * displacement;
        }
        if (keyboard.getButton(GLFW_KEY_LEFT_SHIFT).down) {
            position.y -= 0.5 * displacement;
        }

        camera.setPosition(position);
        camera.update();
    }

    /* Render the player */
    public void render() {
    }

    public Camera getCamera() {
        return camera;
    }

}
