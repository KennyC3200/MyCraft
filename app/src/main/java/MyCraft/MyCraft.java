package MyCraft;

import MyCraft.gfx.*;
import MyCraft.world.*;
import MyCraft.input.*;
import MyCraft.player.*;
import MyCraft.gui.*;
import MyCraft.hud.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33C.*;

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
    }

    /* Initialize necessary components */
    public void init() {
        window = new Window("MyCraft", new Vector2i(1440, 900));

        keyboard = new Keyboard(window);
        mouse = new Mouse(window);

        world = new World();
        player = new Player(window, keyboard, mouse, world);

        HudManager.init(window, player);
        GuiManager.init(window, player);

        renderer = new Renderer(window, world, player);
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

    /* Handle updates */
    public void update() {
        window.update();
        keyboard.update();
        mouse.update();
        player.update();

        // Toggle/untoggle wireframe
        if (keyboard.getButton(GLFW_KEY_T).pressed) {
            renderer.getFlags().wireframe = !renderer.getFlags().wireframe;
        }

        // Toggle/untoggle gui
        if (keyboard.getButton(GLFW_KEY_ESCAPE).pressed) {
            mouse.setCursorToggled(!mouse.getCursorToggled());
            player.getCamera().setToggled(!mouse.getCursorToggled());
        }

        // Quit the game
        if (keyboard.getButton(GLFW_KEY_Q).pressed) {
            System.exit(0);
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
