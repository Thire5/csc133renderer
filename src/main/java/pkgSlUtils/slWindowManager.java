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
        slWindowManagerCreate();
        slWindowManager window = new slWindowManager();
        return window;
    }
    public void makeContextCurrent() {
        glfwMakeContextCurrent(my_win);
    }

    void render(int win_width, int win_height) {
            if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }
            long my_win = glfwCreateWindow(win_width, win_height, "CSC 133", NULL, NULL);
            if (my_win == NULL) {
                throw new RuntimeException("Failed to create the GLFW window");
            }  //  if (window == NULL)
            glfwMakeContextCurrent(my_win);

            GL.createCapabilities();
            float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 1.0f, CC_ALPHA = 1.0f;
            glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);

            while (!glfwWindowShouldClose(my_win)) {
                glfwPollEvents();
                glClear(GL_COLOR_BUFFER_BIT);

                glfwSwapBuffers(my_win);
            }

            glfwDestroyWindow(my_win);
    } // void render()

    public void initGLFWWindow(int winWidth, int winHeight, String title) {

    }
}  // public class Main
