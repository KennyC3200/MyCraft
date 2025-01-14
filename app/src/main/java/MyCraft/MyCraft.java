package MyCraft;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL33C.*;

import MyCraft.gfx.*;
import MyCraft.gui.*;
import MyCraft.hud.*;
import MyCraft.input.*;
import MyCraft.player.*;
import MyCraft.util.*;
import MyCraft.world.*;
import java.util.*;
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

    //     ArrayList<Integer> vertices = new ArrayList<>();
    //     ArrayList<Integer> indices = new ArrayList<>();
    //     for (int i = 0; i < 6; i++) {
    //         BlockMesh.get(Block.GRASS).meshFaceNew(
    //             i,
    //             new Vector3i(15, 15, 15),
    //             vertices,
    //             indices
    //         );
    //     }

    //     for (int i = 0; i < 6; i++) {
    //         for (int j = 0; j < 4; j++) {
    //             int t = vertices.get(4 * i + j);
    //             System.out.print((t & 31) + " ");
    //             System.out.print((t >> 5 & 31) + " ");
    //             System.out.print(((t >> 15 & 31) / 16.0f) + " ");
    //             System.out.print(((t >> 20 & 31) / 16.0f) + " ");
    //             System.out.print(BlockMesh.get(Block.GRASS).getFace(i).uvCoordinates[j * 2] + " ");
    //             System.out.print(BlockMesh.get(Block.GRASS).getFace(i).uvCoordinates[j * 2 + 1] + " ");
    //             System.out.println();
    //         }
    //         System.out.println();
    //     }
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
