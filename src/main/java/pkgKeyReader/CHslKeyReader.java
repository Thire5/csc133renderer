package pkgKeyReader;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class CHslKeyReader {
    public static void keyCallback(long my_win, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        }
        else if(action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }
    public static void resetKeyPressEvent(int keyCode) {
        if(my_instance != null && keyCode < get().keyPressed.length) {
            my_instance.keyPressed[keyCode] = false;
        }
    }
    public static boolean isKeyPressed(int keyCode) {
        if(keyCode < get().keyPressed.length) {
            return get().keyPressed[keyCode];
        }
        else {
            return false;
        }
    }
}
