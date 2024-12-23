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
    private Camera camera;

    /* Init the player */
    public Player(Window window, Keyboard keyboard, Mouse mouse, World world) {
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.world = world;

        camera = new Camera(window, mouse, new Vector3f(0, 0, 0));
    }

    /* Update the player */
    public void update() {
        camera.update();

        if (keyboard.getButton(GLFW_KEY_W).down) {
        }
        if (keyboard.getButton(GLFW_KEY_S).down) {
        }
        if (keyboard.getButton(GLFW_KEY_A).down) {
        }
        if (keyboard.getButton(GLFW_KEY_D).down) {
        }
    }

    /* Render the player */
    public void render() {
    }

    public Camera getCamera() {
        return camera;
    }

}
