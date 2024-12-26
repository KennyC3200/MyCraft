package MyCraft.gfx;

import MyCraft.world.*;
import MyCraft.player.*;
import MyCraft.gui.*;
import MyCraft.hud.*;

import static org.lwjgl.opengl.GL33C.*;

public class Renderer {

    /* Flags for the renderer */
    public static class Flags {
        public boolean wireframe;

        public Flags() {
            wireframe = false;
        }
    }

    private Flags flags;

    private World world;
    private Player player;

    /* Initialize the renderer */
    public Renderer(World world, Player player) {
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

    public void render() {
        if (flags.wireframe) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        /* Render the world */
        world.render();
        player.render();

        /* Render the gui*/
        HudManager.render();
        GuiManager.render();
    }

    public Flags getFlags() {
        return flags;
    }

}
