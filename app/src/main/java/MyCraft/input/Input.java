package MyCraft.input;

import MyCraft.gfx.*;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Input {

    protected Window window;
    protected Button[] buttons;
    protected int buttonFirst, buttonLast;

    protected Input(Window window, int buttonFirst, int buttonLast) {
        this.window = window;
        this.buttonFirst = buttonFirst;
        this.buttonLast = buttonLast;
        this.buttons = new Button[buttonLast];
        for (int i = 0; i < buttonLast; i++) {
            buttons[i] = new Button();
        }
    }

    /* Get the button state */
    protected abstract int getButtonState(int button);

    protected void update() {
        for (int i = buttonFirst; i < buttonLast; i++) {
            int state = getButtonState(i);
            if (state == GLFW_PRESS) {
                buttons[i].down = true;
            }
            if (state == GLFW_RELEASE) {
                buttons[i].down = false;
            }

            buttons[i].pressed = buttons[i].down && !buttons[i].last;
            buttons[i].last = buttons[i].down;
        }
    }

    public Button getButton(int button) {
        return buttons[button];
    }

}
