package MyCraft.gfx;

import MyCraft.world.*;

import org.lwjgl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderer {

    /* Flags for the renderer */
    public static class Flags {
        public boolean wireframe;

        public Flags() {
            wireframe = false;
        }
    }

    private static Flags flags;
    private static World world;

    public void init(World world) {
        this.world = world;
        this.flags = new Flags();

        /* Blend for alpha channel
         * Required for crosshair and hotbar
         * */
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // 3D depth
        glEnable(GL_DEPTH_TEST);
    }

    public void render() {
        if (flags.wireframe) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        /* Render the world */
        world.render();

        /* TODO: Render the UI */
    }

    public Flags getFlags() {
        return flags;
    }

}
