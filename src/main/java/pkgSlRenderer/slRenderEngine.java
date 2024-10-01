package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class slRenderEngine {
    private final int NUM_RGBA = 4;
    private final int NUM_3D_COORDS = 3;
    private final int TRIANGLES_PER_CIRCLE = 40;
    private final float C_RADIUS = 0.05f;
    private final int MAX_CIRCLES = 100;
    private final int UPDATE_INTERVAL = 30;

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

    public void initOpenGL(slWindowManager window) {
    }
}
