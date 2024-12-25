package MyCraft.gfx;

import static org.lwjgl.opengl.GL33C.*;

public class VAO {

    private int handle;

    /* Init the vertex array */
    public VAO() {
        handle = glGenVertexArrays();
    }

    /* Destroy the vertex array */
    public void destroy() {
        glDeleteVertexArrays(handle);
    }

    /* Bind the vertex array */
    public void bind() {
        glBindVertexArray(handle);
    }

    /* Attribute the current vertex array buffer 
     * Basically specify the format of the data in the array buffer */
    public void attribPointer(VBO vbo, int location, int size, int type, int stride, long offset) {
        vbo.bind();
        bind();

        glVertexAttribPointer(location, size, type, false, stride, offset);
        glEnableVertexAttribArray(location);
    }

}
