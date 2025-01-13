package MyCraft.hud;

import MyCraft.gfx.*;

import static org.lwjgl.opengl.GL33C.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class HudComponent {

    protected static Window window;
    static Shader shader;
    protected static VAO vao;
    protected static VBO vbo;
    protected static VBO ibo;

    protected FloatBuffer[] vertices;
    protected IntBuffer[] indices;

    public boolean meshed;
    public boolean toggled;

    abstract void mesh();
    abstract void render();

    /* Init the hud component */
    static void init(Window window) {
        HudComponent.window = window;

        shader = new Shader("./src/main/resources/shaders/hud.vert", "./src/main/resources/shaders/hud.frag");
        vao = new VAO();
        vbo = new VBO(GL_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
        ibo = new VBO(GL_ELEMENT_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
    }

}
