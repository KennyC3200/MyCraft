package MyCraft.input;

import MyCraft.gfx.*;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    private Window window;
    private Button[] keys;

    public Keyboard(Window window) {
        this.window = window;

        this.keys = new Button[GLFW_KEY_LAST];
        for (int i = 0; i < GLFW_KEY_LAST; i++) {
            keys[i] = new Button();
        }
    }

    public void update() {
        for (int i = GLFW_KEY_SPACE; i < GLFW_KEY_LAST; i++) {
            int state = glfwGetKey(window.getHandle(), i);
            if (state == GLFW_PRESS) {
                keys[i].down = true;
            }
            if (state == GLFW_RELEASE) {
                keys[i].down = false;
            }

            keys[i].pressed = keys[i].down && !keys[i].last;
            keys[i].last = keys[i].down;
        }
    }

    public Button getButton(int key) {
        return keys[key];
    }

}
