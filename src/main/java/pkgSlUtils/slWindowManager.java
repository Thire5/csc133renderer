package pkgSlUtils;

import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class slWindowManager {
    private static long my_win;
    private static void slWindowManagerCreate(int win_width, int win_height) {
        if(my_win == 0) {
            my_win = glfwCreateWindow(win_width, win_height, "csc 133", NULL, NULL)
        }
    }
    public static slWindowManager get() {
        return new slWindowManager();
    }
    public void makeContextCurrent() {
        glfwMakeContextCurrent(my_win);
    }



    public void initGLFWWindow(int winWidth, int winHeight, String title) {

    }
}  // public class Main
