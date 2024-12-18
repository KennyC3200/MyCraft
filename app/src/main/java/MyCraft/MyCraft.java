package MyCraft;

import MyCraft.gfx.*;
import MyCraft.world.*;

import org.lwjgl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class MyCraft {

    private Window window;
    private Renderer renderer;

    private World world;

    /* Game */
    public MyCraft() {
        init();
        loop();
        destroy();
    }

    /* Initialize necessary components */
    public void init() {
        window = new Window("MyCraft", 1280, 720);
        window.init();

        world = new World();
        world.init();

        renderer = new Renderer();
        renderer.init(world);
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

    /* Clean up objects */
    public void destroy() {
        window.destroy();
    }

    /* Handle updates */
    public void update() {
    }

    /* Render */
    public void render() {
        renderer.render();
    }

    public static void main(String args[]) {
        new MyCraft();
    }

}
