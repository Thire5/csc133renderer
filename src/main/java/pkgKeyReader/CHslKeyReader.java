package pkgKeyReader;

import pkgSlUtils.CHslWindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class CHslKeyReader {
    public static boolean[] keyPressed =  new boolean[400];
    public boolean[] isKeyPressed() {
        return keyPressed;
    }
    static CHslKeyReader my_instance = new CHslKeyReader();
    public static void resetKeyPressEvent(int keyCode) {
        if(my_instance != null && keyCode < keyPressed.length) {
            my_instance.isKeyPressed()[keyCode] = false;
        }
    }
    public static boolean isKeyPressed(int keyCode) {
        if(keyCode < keyPressed.length) {
            return keyPressed[keyCode];
        }
        else {
            return false;
        }
    }
}
