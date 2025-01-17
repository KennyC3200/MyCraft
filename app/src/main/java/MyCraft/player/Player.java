package MyCraft.player;

import MyCraft.gfx.*;
import MyCraft.input.*;
import MyCraft.world.*;
import MyCraft.hud.*;
import MyCraft.util.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;

import java.util.*;

public class Player {

    private Window window;
    private Keyboard keyboard;
    private Mouse mouse;
    private World world;

    private Vector3f offset;
    private Vector3f position;
    private Vector3f cameraPosition;

    private final float height = 1.8f;
    private final float width = 0.6f;

    private float movementSpeed = 30.0f;
    private float fallingVelocity = 0.0f;

    private float gravityAcceleration = -15.0f;
    private float gravityTerminalVelocity = -30.0f;

    private Camera camera;

    public static final int hotbar_size = 9;
    private Integer[] hotbarItems;
    private int currentHotbarIdx;

    private Vector3i chunkPosition;
    private Vector3i prevChunkPosition;

    // Keep in mind that height = 1.8f
    // Hence, the player AABB needs to be checked against 10 surrounding blocks
    private AABB aabb;

    /* Init the player */
    public Player(Window window, Keyboard keyboard, Mouse mouse, World world) {
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.world = world;

        offset = new Vector3f(
            0.0001f + world.getChunksSize().x * Chunk.size.x / 2,
            0.0001f + world.getChunksSize().y * Chunk.size.y / 2 + 10,
            0.0001f + world.getChunksSize().z * Chunk.size.z / 2
        );
        position = new Vector3f(offset);
        cameraPosition = new Vector3f(position.x, position.y + height, position.z);

        camera = new Camera(mouse, position);

        // Init the hotbar
        currentHotbarIdx = 0;
        hotbarItems = new Integer[9];
        for (int i = 0; i < hotbar_size; i++) {
            hotbarItems[i] = Block.NONE;
        }
        hotbarItems[0] = Block.GRASS;
        hotbarItems[1] = Block.DIRT;
        hotbarItems[2] = Block.STONE;

        // Chunk positions
        chunkPosition = new Vector3i(
            (int) position.x / Chunk.size.x,
            (int) position.y / Chunk.size.y,
            (int) position.z / Chunk.size.z
        );
        prevChunkPosition = new Vector3i(chunkPosition);

        aabb = new AABB(
            new Vector3f(position.x, position.y, position.z),
            new Vector3f(position.x + width, position.y + height, position.z + width)
        );
    }

    /* Update the player */
    public void update() {
        float dt = (float) window.getTimeDelta();

        // Handle movement
        float displacement = movementSpeed * dt;
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

        // Update the camera
        cameraPosition.x = position.x;
        cameraPosition.y = position.y + height;
        cameraPosition.z = position.z;
        camera.setPosition(cameraPosition);
        camera.update();

        /* Handle the hotbar
         * Keep in mind that the index starts at 0, not 1
         * */
        for (int i = 0; i < hotbar_size; i++) {
            if (keyboard.getButton(GLFW_KEY_1 + i).pressed) {
                currentHotbarIdx = i;
                HudManager.meshHotbar();
            }
        }

        // Handle player raycast and block placement/deletion
        Ray.CastData raycast = Ray.cast(world, cameraPosition, camera.getDirection(), 8.0f);
        if (raycast != null && raycast.hit) {
            if (mouse.getButton(GLFW_MOUSE_BUTTON_LEFT).pressed) {
                world.getBlock(raycast.position).setID(Block.AIR);
                world.getChunk(raycast.position).setDirty();
            }
            if (mouse.getButton(GLFW_MOUSE_BUTTON_RIGHT).pressed) {
                int blockX = raycast.position.x + raycast.out.x;
                int blockY = raycast.position.y + raycast.out.y;
                int blockZ = raycast.position.z + raycast.out.z;
                Block block = world.getBlock(blockX, blockY, blockZ);

                if (block != null && block.getID() == Block.AIR) {
                    world.getBlock(blockX, blockY, blockZ).setID(hotbarItems[currentHotbarIdx]);
                    world.getChunk(blockX, blockY, blockZ).setDirty();
                }
            }
        }

        // Need to round negative numbers down
        chunkPosition.x = (int) (position.x / Chunk.size.x) + (position.x < 0 ? -1 : 0);
        chunkPosition.y = (int) (position.y / Chunk.size.y) + (position.y < 0 ? -1 : 0);
        chunkPosition.z = (int) (position.z / Chunk.size.x) + (position.y < 0 ? -1 : 0);

        // Generate new chunks as the player moves around
        int xDiff = chunkPosition.x - prevChunkPosition.x;
        int zDiff = chunkPosition.z - prevChunkPosition.z;
        if (xDiff != 0 || zDiff != 0) {
            if (xDiff > 0) {
                world.createNewChunks(Direction.EAST);
            } else if (xDiff < 0) {
                world.createNewChunks(Direction.WEST);
            } else if (zDiff > 0) {
                world.createNewChunks(Direction.SOUTH);
            } else if (zDiff < 0) {
                world.createNewChunks(Direction.NORTH);
            }

            prevChunkPosition.x = chunkPosition.x;
            prevChunkPosition.y = chunkPosition.y;
            prevChunkPosition.z = chunkPosition.z;
        }

        // TODO: Handle gravity

        // TODO: Handle collisions
        aabb.setMin(position.x, position.y, position.z);
        aabb.setMax(position.x + width, position.y + height, position.z + width);
        for (int i = 0; i < 6; i++) {
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public String getPositionStr() {
        return String.format("(%.1f, %.1f, %.1f)", position.x, position.y, position.z);
    }

    public String getOffsetStr() {
        return String.format("(%.1f, %.1f, %.1f)", offset.x, offset.y, offset.z);
    }

    public String getPositionMinusOffsetStr() {
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
