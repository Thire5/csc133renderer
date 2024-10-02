package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import static java.lang.Math.PI;
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

    public void render() {
        updateRandVertices();

        final float begin_angle = 0.0f, end_angle = (float) (2.0f * PI);
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLES);
// Each triangle will require color + 3 vertices as below.
// For each circle you need 40 of these for the assignment.
            glColor4f(..., ..., ..., ...);
            glVertex3f(x0, y0, z0);
            glVertex3f(x1, y1, z1);
            glVertex3f(x2, y2, z2);
 ...
....
            glEnd();
            my_wm.swapBuffers();
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)


    } // void render()

    public void initOpenGL(slWindowManager window) {
    }
}
