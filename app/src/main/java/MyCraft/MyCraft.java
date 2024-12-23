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

    private Player player;

    /* Game */
    public MyCraft() {
        init();
        loop();
        destroy();
    }

    /* Initialize necessary components */
    public void init() {
        window = new Window("MyCraft", new Vector2i(1280, 720));

        keyboard = new Keyboard(window);
        mouse = new Mouse(window);

        world = new World();
        player = new Player(window, keyboard, mouse, world);

        renderer = new Renderer(world, player);
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
        mouse.update();
        player.update();
    }

    /* Render */
    public void render() {
        renderer.render();
    }

    public static void main(String args[]) {
        new MyCraft();
    }

}
