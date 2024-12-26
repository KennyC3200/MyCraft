package MyCraft.input;

import MyCraft.gfx.*;

import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse extends Input {

    private Vector2d position, positionDelta;
    private boolean cursorToggled;
    private boolean firstMove;

    /* Init the mouse */
    public Mouse(Window window) {
        super(window, GLFW_MOUSE_BUTTON_1, GLFW_MOUSE_BUTTON_LAST);

        this.position = new Vector2d(0, 0);
        this.positionDelta = new Vector2d(0, 0);
        this.cursorToggled = false;
        this.firstMove = true;
    }

    /* Update the mouse */
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

        super.update();

        glfwSetInputMode(
            window.getHandle(), 
            GLFW_CURSOR, 
            cursorToggled ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED
        );
    }

    @Override
    protected int getButtonState(int button) {
        return glfwGetMouseButton(window.getHandle(), button);
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
