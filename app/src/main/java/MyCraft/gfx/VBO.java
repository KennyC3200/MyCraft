package MyCraft.gfx;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class VBO {
    
    private int handle;
    private int type;
    private int drawType;

    /* Init the buffer */
    public VBO(int type, int drawType) {
        this.type = type;
        this.drawType = drawType;
        this.handle = glGenBuffers();
    }

    /* Destroy the buffer */
    public void destroy() {
        glDeleteBuffers(handle);
    }

    /* Bind the buffer */
    public void bind() {
        glBindBuffer(type, handle);
    }

    /* Buffer the data for usage */
    public void buffer(FloatBuffer data) {
        bind();
        glBufferData(type, data, drawType);
    }

    /* Buffer the data for usage */
    public void buffer(IntBuffer data) {
        bind();
        glBufferData(type, data, drawType);
    }

}
