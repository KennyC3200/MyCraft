package MyCraft.hud;

import MyCraft.gfx.*;

import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class HudComponent {

    protected static Window window;
    protected static Shader shader;
    protected static VAO vao;
    protected static VBO vbo;
    protected static VBO ibo;

    /* Inherited members */
    protected FloatBuffer vertices;
    protected IntBuffer indices;
    protected boolean toggled;

    /* Init the hud component */
    public static void init(Window window) {
        HudComponent.window = window;

        shader = new Shader("./src/main/resources/shaders/hud.vs", "./src/main/resources/shaders/hud.fs");
        vao = new VAO();
        vbo = new VBO(GL_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
        ibo = new VBO(GL_ELEMENT_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
    }

    abstract void mesh();
    abstract void render();

}
