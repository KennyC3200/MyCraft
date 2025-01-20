package MyCraft.gui;

import MyCraft.gfx.*;
import MyCraft.world.*;
import MyCraft.player.*;
import MyCraft.util.*;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import java.util.*;

public class GuiManager {

    protected static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");
    private static String glslVersion = IS_APPLE ? "#version 150" : "#version 130";

    private static Window window;
    private static World world;
    private static Player player;

    private static ImGuiImplGlfw imGuiGlfw;
    private static ImGuiImplGl3 imGuiGl3;

    public static int[][] chunksSize;

    /* Create the imgi context */
    public static void init(Window window, World world, Player player) {
        GuiManager.window = window;
        GuiManager.world = world;
        GuiManager.player = player;

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename("GUI.txt"); // Enable imgui.ini file

        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGlfw.init(window.getHandle(), true);
        imGuiGl3 = new ImGuiImplGl3();
        imGuiGl3.init(glslVersion);

        chunksSize = new int[3][1];
        chunksSize[0][0] = 10;
        chunksSize[1][0] = 10;
        chunksSize[2][0] = 10;
    }

    /* Render the gui */
    public static void render() {
        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();

        // Create new imgui frame
        ImGui.newFrame();

        if (MyCraft.MyCraft.state[0] == MyCraft.MyCraft.INIT_STATE) {
            ImGui.begin("World Initialization");
            ImGui.textColored(255, 0, 0, 255, "INITIALIZE NUMBER OF CHUNKS");
            ImGui.text("Don't choose too many chunks if ur computer is slow.");
            ImGui.text("This will be reflected in chunk generation.");
            ImGui.sliderInt("# of chunks (x)", chunksSize[0], 10, 24);
            ImGui.sliderInt("# of chunks (y)", chunksSize[1], 10, 24);
            ImGui.sliderInt("# of chunks (z)", chunksSize[2], 10, 24);
            ImGui.textColored(255, 0, 0, 255, "TUTORIAL");
            ImGui.text("Use WASD to move around.");
            ImGui.text("Use the mouse to look around.");
            ImGui.text("Press ESCAPE to toggle/untoggle the cursor.");
            ImGui.text("Press T to toggle/untoggle the wireframe.");
            ImGui.text("There are some magical areas where block placing breaks.");
            ImGui.text("See the README.md for bugs and things to be implemented.");
            if (ImGui.button("Start world")) {
                MyCraft.MyCraft.state[0] = MyCraft.MyCraft.START_STATE;
            }
            ImGui.end();
        }

        if (MyCraft.MyCraft.state[0] == MyCraft.MyCraft.PLAY_STATE) {
            ImGui.begin("Overview");
            ImGui.text("FPS: " + Integer.toString((int) window.getFPS()));
            ImGui.text(String.format("POSITION: " + player.getPositionStr()));
            ArrayList<BlockCounter> blocksPlaced = new ArrayList<>(player.blocksPlaced.values());
            blocksPlaced.sort(new BlockCounter());
            ImGui.text("Most blocks placed: " + blocksPlaced.get(0));
            ArrayList<BlockCounter> blocksBroken = new ArrayList<>(player.blocksBroken.values());
            blocksBroken.sort(new BlockCounter());
            ImGui.text("Most blocks broken: " + blocksBroken.get(0));
            ImGui.end();
        }

        ImGui.endFrame();
        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

}
