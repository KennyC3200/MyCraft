package MyCraft.hud;

import MyCraft.gfx.*;
import MyCraft.player.*;

public class HudManager {

    private static Crosshair crosshair;
    private static Hotbar hotbar;

    /* Init the hud */
    public static void init(Window window, Player player) {
        HudComponent.init(window);

        crosshair = new Crosshair();
        hotbar = new Hotbar(player);
    }

    /* Render the hud */
    public static void render() {
        HudComponent.shader.bind();

        if (MyCraft.MyCraft.state[0] != MyCraft.MyCraft.PLAY_STATE) {
            return;
        }

        if (!crosshair.meshed) {
            crosshair.mesh();
            crosshair.meshed = true;
        }
        crosshair.render();

        if (!hotbar.meshed) {
            hotbar.mesh();
            hotbar.meshed = true;
        }
        hotbar.render();
    }

    /* Mesh the hotbar */
    public static void meshHotbar() {
        hotbar.meshed = false;
    }

}
