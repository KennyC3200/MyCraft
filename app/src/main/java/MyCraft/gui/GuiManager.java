package MyCraft.gui;

import MyCraft.gfx.*;

import org.lwjgl.glfw.GLFW;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class GuiManager {

    protected static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");
    private static String glslVersion = IS_APPLE ? "#version 150" : "#version 130";

    private Window window;
    private ImGuiImplGlfw imGuiGlfw;
    private ImGuiImplGl3 imGuiGl3;

    /* Create the imgi context */
    public GuiManager(Window window) {
        this.window = window;

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null); // No imgui.ini file

        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGlfw.init(window.getHandle(), true);
        imGuiGl3 = new ImGuiImplGl3();
        imGuiGl3.init(glslVersion);
    }

    /* Destroy the imgui context */
    public void destroy() {
        ImGui.destroyContext();
    }

    /* Render the gui */
    public void render() {
        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();

        ImGui.newFrame();
        ImGui.text("Hello world!");
        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

}
