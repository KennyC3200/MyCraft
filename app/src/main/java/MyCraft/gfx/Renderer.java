package MyCraft.gfx;

import MyCraft.world.*;
import MyCraft.player.*;
import MyCraft.gui.*;
import MyCraft.hud.*;

import static org.lwjgl.opengl.GL33C.*;
import org.lwjgl.system.MemoryStack;

import org.joml.Vector3f;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public class Renderer {

    /* Flags for the renderer */
    public static class Flags {
        public boolean wireframe;

        public Flags() {
            wireframe = false;
        }
    }

    private Flags flags;

    private Window window;
    private World world;
    private Player player;

    /* Initialize the renderer */
    public Renderer(Window window, World world, Player player) {
        this.window = window;
        this.world = world;
        this.player = player;
        this.flags = new Flags();

        /* Blend for alpha channel
         * Required for crosshair and hotbar
         * */
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // 3D depth
        glEnable(GL_DEPTH_TEST);
    }

    /* Render the game */
    public void render() {
        renderWorld();
        renderUI();
    }

    /* Render the world */
    private void renderWorld() {
        if (flags.wireframe) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        // Bind the shader and uniform texture
        ChunkMesh.shader.bind();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            Camera camera = player.getCamera();
            FloatBuffer view = new Matrix4f()
                .lookAt(
                    camera.getPosition(), 
                    new Vector3f(camera.getPosition()).add(camera.getDirection()), camera.getUp())
                .get(stack.mallocFloat(16));

            FloatBuffer projection = new Matrix4f()
                .perspective(
                        camera.getFOV(),
                        (float) window.getSize().x / (float) window.getSize().y, 
                        camera.getZNear(), camera.getZFar())
                .get(stack.mallocFloat(16));
            ChunkMesh.shader.uniformMatrix4f("view", view); 
            ChunkMesh.shader.uniformMatrix4f("projection", projection);
        }

        ChunkMesh.shader.uniformTexture2D(BlockMesh.getAtlas(), 0);
        world.render();
    }

    /* Render the ui */
    private void renderUI() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        HudManager.render();
        GuiManager.render();
    }

    public Flags getFlags() {
        return flags;
    }

}
