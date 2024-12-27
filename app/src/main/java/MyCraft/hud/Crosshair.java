package MyCraft.hud;

import MyCraft.gfx.*;

import static org.lwjgl.opengl.GL33C.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Crosshair extends HudComponent {

    private Texture texture;

    /* Init the crosshair */
    public Crosshair() {
        texture = new Texture("./src/main/resources/images/crosshair.png", "tex", GL_RGBA, GL_RGBA);
        toggled = true;

        vertices = new FloatBuffer[1];
        indices = new IntBuffer[1];
    }

    /* Mesh the crosshair texture */
    @Override
    public void mesh() {
        float x = window.getSize().y / 1000.0f * 0.01f;
        float y = window.getSize().x / 1000.0f * 0.01f;

        float[] verticesArray = {
             x,  y,  1.0f, 1.0f,
             x, -y,  1.0f, 0.0f,
            -x, -y,  0.0f, 0.0f,
            -x,  y,  0.0f, 1.0f,
        };

        int[] indicesArray = {
            0, 1, 3,
            1, 2, 3,
        };

        vertices[0] = BufferUtils.createFloatBuffer(verticesArray.length);
        vertices[0].put(verticesArray);
        vertices[0].flip();

        indices[0] = BufferUtils.createIntBuffer(indicesArray.length);
        indices[0].put(indicesArray);
        indices[0].flip();
    }

    /* Render the crosshair texture */
    @Override
    public void render() {
        if (!toggled) {
            return;
        }

        shader.uniformTexture2D(texture, 0);

        ibo.buffer(indices[0]);
        vbo.buffer(vertices[0]);

        vao.attribPointer(vbo, 0, 2, GL_FLOAT, 4 * Float.BYTES, 0 * Float.BYTES);
        vao.attribPointer(vbo, 1, 2, GL_FLOAT, 4 * Float.BYTES, 2 * Float.BYTES);

        vao.bind();
        ibo.bind();
      
        glDrawElements(GL_TRIANGLES, indices[0].remaining(), GL_UNSIGNED_INT, 0);
    }

}
