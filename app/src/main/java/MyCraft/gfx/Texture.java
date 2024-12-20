package MyCraft.gfx;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Texture {

    private int handle;
    private Vector2i size;
    private String fragmentShaderName;

    /* Initialize the texture
     * @param path The path of the texture
     * @param fragmentShaderName The fragment shader name
     * @param texFormat The texture format
     * @param srcFormat The source format
     * */
    public Texture(String path, String fragmentShaderName, int texFormat, int srcFormat) {
        this.fragmentShaderName = fragmentShaderName;
        this.handle = glGenTextures();
        bind();

        // No interpolation
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        // OpenGL has texture coordinates with (0, 0) on bottom left
        stbi_set_flip_vertically_on_load(true);
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer data = stbi_load(path, width, height, channels, 0);

        this.size = new Vector2i(width.get(0), height.get(0));

        // Handle errors
        if (data != null) {
            glTexImage2D(GL_TEXTURE_2D, 0, texFormat, size.x, size.y, 0, srcFormat, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
        } else {
            System.out.printf("Error creating texture %s: %s", fragmentShaderName, path);
        }

        if (data != null) stbi_image_free(data);
    }

    /* Destroy the texture */
    public void destroy() {
        glDeleteTextures(handle);
    }

    /* Bind the texture */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    public String getFragmentShaderName() {
        return fragmentShaderName;
    }

    public Vector2i getSize() {
        return size;
    }

}
