package MyCraft;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;

import MyCraft.gfx.*;
import MyCraft.gui.*;
import MyCraft.hud.*;
import MyCraft.input.*;
import MyCraft.player.*;
import MyCraft.world.*;
import org.joml.*;

public class MyCraft {

    private Window window;
    private Renderer renderer;

    private Keyboard keyboard;
    private Mouse mouse;

    private World world;

    private Player player;

    public static final int INIT_STATE = 0;
    public static final int START_STATE = 1;
    public static final int PLAY_STATE = 2;
    public static int[] state;

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
        GuiManager.init(window, world, player);

        renderer = new Renderer(window, world, player);
        
        state = new int[1];
        state[0] = INIT_STATE;
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
        keyboard.update();
        mouse.update();

        if (state[0] == INIT_STATE) {
            mouse.setCursorToggled(true);
        }
        if (state[0] == PLAY_STATE) {
            window.update();
            player.update();
        }
        if (state[0] == START_STATE) {
            world.start(new Vector3i(
                GuiManager.chunksSize[0][0],
                GuiManager.chunksSize[1][0],
                GuiManager.chunksSize[2][0]
            ));
            player.start();
            state[0] = PLAY_STATE;
            mouse.setCursorToggled(false);
        }

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
