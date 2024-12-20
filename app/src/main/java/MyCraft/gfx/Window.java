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
    private Vector2i size;

    /* Constructor to initialize title and dimensions */
    public Window(String title, Vector2i size) {
        this.title = title;
        this.size = size;
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

        handle = glfwCreateWindow(
                size.x, size.y,
                title,
                NULL, NULL
        );

        if (handle == NULL) throw new RuntimeException("Failed to create the GLFW handle");

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.mallocInt(1); // int*
            IntBuffer height = stack.mallocInt(1); // int*

            // Get the handle size passed to glfwCreateWindow
            glfwGetWindowSize(handle, width, height);

            GLFWVidMode vid_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    handle,
                    (vid_mode.width() - width.get(0)) / 2,
                    (vid_mode.height() - height.get(0)) / 2
            );
        } // The stack frame is popped automatically

        glfwMakeContextCurrent(handle);

        // v-sync
        glfwSwapInterval(1);

        glfwShowWindow(handle);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    /* Destroy the window */
    public void destroy() {
        glfwDestroyWindow(handle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public long getHandle() {
        return handle;
    }

}
