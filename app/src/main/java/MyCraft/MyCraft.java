package MyCraft;

import MyCraft.gfx.*;
import MyCraft.world.*;
import MyCraft.input.*;
import MyCraft.player.*;

import org.lwjgl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import org.joml.*;

public class MyCraft {

    private Window window;
    private Renderer renderer;

    private Keyboard keyboard;
    private Mouse mouse;

    private World world;

    private Camera camera;

    /* Game */
    public MyCraft() {
        init();
        loop();
        destroy();
    }

    /* Initialize necessary components */
    public void init() {
        window = new Window("MyCraft", new Vector2i(1280, 720));

        world = new World();

        renderer = new Renderer(world);

        keyboard = new Keyboard(window);
        mouse = new Mouse(window);

        camera = new Camera(window, mouse, new Vector3f(0, 0, 0));
    }

    /* Main game loop */
    public void loop() {
        while (!glfwWindowShouldClose(window.getHandle())) {
            glfwPollEvents();

            glClearColor(0.580f, 0.800f, 0.976f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            update();
            render();

            glfwSwapBuffers(window.getHandle());
        }
    }

    /* Destroy objects */
    public void destroy() {
        world.destroy();
        window.destroy();
    }

    /* Handle updates */
    public void update() {
        window.update();
        keyboard.update();

        if (keyboard.getButton(GLFW_KEY_W).down) {
        }
        if (keyboard.getButton(GLFW_KEY_S).down) {
        }
        if (keyboard.getButton(GLFW_KEY_A).down) {
        }
        if (keyboard.getButton(GLFW_KEY_D).down) {
        }
    }

    /* Render */
    public void render() {
        renderer.render();
    }

    public static void main(String args[]) {
        new MyCraft();
    }

}
