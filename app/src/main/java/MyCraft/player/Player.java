package MyCraft.player;

import MyCraft.gfx.*;
import MyCraft.input.*;
import MyCraft.world.*;
import MyCraft.hud.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;

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

    public static final int HOTBAR_SIZE = 9;
    private Integer[] hotbarItems;
    private int currentHotbarIdx;

    /* Init the player */
    public Player(Window window, Keyboard keyboard, Mouse mouse, World world) {
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.world = world;

        height = 1.8f;
        speed = 15.0f;

        offset = new Vector3f(
            0.001f + world.getChunksSize().x * Chunk.size.x / 2,
            0.001f + world.getChunksSize().y * Chunk.size.y / 2 + height,
            0.001f + world.getChunksSize().z * Chunk.size.z / 2
        );
        position = new Vector3f(offset);

        camera = new Camera(mouse, position);

        currentHotbarIdx = 0;
        hotbarItems = new Integer[9];
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            hotbarItems[i] = Block.NONE;
        }
        hotbarItems[0] = Block.GRASS;
        hotbarItems[1] = Block.DIRT;
        hotbarItems[2] = Block.STONE;
    }

    /* Update the player */
    public void update() {

        // Handle movement
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

        /* Handle the hotbar
         * Keep in mind that the index starts at 0, not 1
         * */
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            if (keyboard.getButton(GLFW_KEY_1 + i).pressed) {
                currentHotbarIdx = i;
                HudManager.meshHotbar();
            }
        }

        // Update the camera
        camera.setPosition(position);
        camera.update();

        // Handle player raycast and block placement/deletion
        Ray.CastData raycast = Ray.cast(world, position, camera.getDirection(), 8.0f);
        if (raycast != null && raycast.hit) {
            if (mouse.getButton(GLFW_MOUSE_BUTTON_LEFT).pressed) {
                world.getBlock(raycast.position).setID(Block.AIR);
                world.getChunk(raycast.position).mesh();
            }
            if (mouse.getButton(GLFW_MOUSE_BUTTON_RIGHT).pressed) {
                int blockX = raycast.position.x + raycast.out.x;
                int blockY = raycast.position.y + raycast.out.y;
                int blockZ = raycast.position.z + raycast.out.z;
                Block block = world.getBlock(blockX, blockY, blockZ);

                if (block != null && block.getID() == Block.AIR) {
                    world.getBlock(blockX, blockY, blockZ).setID(hotbarItems[currentHotbarIdx]);
                    world.getChunk(blockX, blockY, blockZ).mesh();
                }
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public String getPositionString() {
        return String.format("(%.1f, %.1f, %.1f)", position.x, position.y, position.z);
    }

    public String getPositionMinusOffsetString() {
        return String.format(
            "(%.1f, %.1f, %.1f)", 
            position.x - offset.x, 
            position.y - offset.y, 
            position.z - offset.z
        );
    }

    public Integer getHotbarItem(int idx) {
        return hotbarItems[idx];
    }

    public int getCurrentHotbarIdx() {
        return currentHotbarIdx;
    }

}
