package MyCraft.input;

import MyCraft.gfx.*;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard extends Input {

    public Keyboard(Window window) {
        super(window, GLFW_KEY_SPACE, GLFW_KEY_LAST);
    }

    @Override
    protected int getButtonState(int button) {
        return glfwGetKey(window.getHandle(), button);
    }

}
