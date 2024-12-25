package MyCraft.input;

import MyCraft.gfx.*;

import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private Window window;

    private Vector2d position, positionDelta;
    private Button[] keys;
    private boolean cursorToggled;
    private boolean firstMove;

    /* Init the mouse */
    public Mouse(Window window) {
        this.window = window;
        this.position = new Vector2d(0, 0);
        this.positionDelta = new Vector2d(0, 0);
        this.keys = new Button[GLFW_MOUSE_BUTTON_LAST];
        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            this.keys[i] = new Button();
        }

        this.cursorToggled = false;
        this.firstMove = true;
    }

    /* Update the mosue */
    public void update() {
        double[] x = new double[1];
        double[] y = new double[1];
        glfwGetCursorPos(window.getHandle(), x, y);

        if (firstMove) {
            position.x = x[0];
            position.y = y[0];
            firstMove = false;
        }

        positionDelta.x = x[0] - position.x;
        positionDelta.y = position.y - y[0];

        position.x = x[0];
        position.y = y[0];

        for (int i = GLFW_MOUSE_BUTTON_1; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            int state = glfwGetMouseButton(window.getHandle(), i);
            if (state == GLFW_PRESS) {
                keys[i].down = true;
            }
            if (state == GLFW_RELEASE) {
                keys[i].down = false;
            }

            keys[i].pressed = keys[i].down && !keys[i].last;
            keys[i].last = keys[i].down;
        }

        glfwSetInputMode(
            window.getHandle(), 
            GLFW_CURSOR, 
            cursorToggled ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED
        );
    }

    public Vector2f getPositionDelta() {
        return new Vector2f(positionDelta);
    }

    public boolean getCursorToggled() {
        return cursorToggled;
    }

    public void setCursorToggled(boolean bool) {
        cursorToggled = bool;
    }

}
