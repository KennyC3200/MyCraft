package MyCraft;

import MyCraft.gfx.*;
import MyCraft.world.*;
import MyCraft.input.*;

import org.lwjgl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import org.joml.*;

public class MyCraft {

    private Window window;
    private Renderer renderer;

    private Keyboard keyboard;

    private World world;

    /* Game */
    public MyCraft() {
        init();
        loop();
        destroy();
    }

    /* Initialize necessary components */
    public void init() {
        window = new Window("MyCraft", new Vector2i(1280, 720));
        window.init();

        world = new World();
        world.init();

        renderer = new Renderer();
        renderer.init(world);

        keyboard = new Keyboard(window);
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
