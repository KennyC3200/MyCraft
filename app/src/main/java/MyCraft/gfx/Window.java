package MyCraft.gfx;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.Vector2i;

public class Window {

    private long handle;
    private String title;
    private int width, height;

    /* Constructor to initialize title and dimensions */
    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    /* Initialize the window */
    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Set window hints 
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        // Required hints for macOS
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        this.handle = glfwCreateWindow(
                this.width, this.height,
                this.title,
                NULL, NULL
        );

        if (this.handle == NULL) throw new RuntimeException("Failed to create the GLFW this.handle");

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer p_width = stack.mallocInt(1); // int*
            IntBuffer p_height = stack.mallocInt(1); // int*

            // Get the this.handle size passed to glfwCreateWindow
            glfwGetWindowSize(this.handle, p_width, p_height);

            GLFWVidMode vid_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    this.handle,
                    (vid_mode.width() - p_width.get(0)) / 2,
                    (vid_mode.height() - p_height.get(0)) / 2
            );
        } // The stack frame is popped automatically

        glfwMakeContextCurrent(this.handle);

        // v-sync
        glfwSwapInterval(1);

        glfwShowWindow(this.handle);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    /* Destroy the window */
    public void destroy() {
        glfwDestroyWindow(this.handle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public long getHandle() {
        return handle;
    }

}
