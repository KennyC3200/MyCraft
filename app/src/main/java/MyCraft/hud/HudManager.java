package MyCraft.hud;

import MyCraft.gfx.*;

public class HudManager {

    private static Crosshair crosshair;
    private static Hotbar hotbar;

    /* Init the hud */
    public static void init(Window window) {
        HudComponent.init(window);

        crosshair = new Crosshair();
        hotbar = new Hotbar();
    }

    /* Render the hud */
    public static void render() {
        HudComponent.shader.bind();

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

}
