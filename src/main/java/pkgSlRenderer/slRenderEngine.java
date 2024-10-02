package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import java.util.Random;

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
    private float[] rand_colors;
    Random myRand = new Random();
    private slWindowManager my_wm = new slWindowManager();
    private float[][] rand_coords;
    private final float opacity = 1.0f;
    private final float z = 0.0f;
    private void updateRandVertices() {
        float coord_1 = (myRand.nextFloat() * 2) - 1;
        rand_coords[0][0] = coord_1;
        float coord_2 = (myRand.nextFloat() * 2) - 1;
        rand_coords[0][1] = coord_2;
        rand_coords[0][2] = z;
    }
    private void updateRandColors() {
        float red = myRand.nextFloat();
        float green = myRand.nextFloat();
        float blue = myRand.nextFloat();
        rand_colors[0] = red;
        rand_colors[1] = green;
        rand_colors[2] = blue;
        rand_colors[3] = opacity;
    }
    private void generateCircleSegmentVertices(float[] a, float b, float[] c, float d, float e, int f) {

    }
    public void render() {
        updateRandVertices();

        final float begin_angle = 0.0f, end_angle = (float) (2.0f * PI);
        while (!my_wm.isGlfwWindowClosed()) {
            updateRandVertices();
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLES);
// Each triangle will require color + 3 vertices as below.
// For each circle you need 40 of these for the assignment.
            for (int triangles = 0; triangles < TRIANGLES_PER_CIRCLE; triangles++) {
                updateRandColors();
                glColor4f(rand_colors[0], rand_colors[1], rand_colors[2], rand_colors[3]);
                glVertex3f(rand_coords[0][0], rand_coords[0][1], rand_coords[0][2]);
                glVertex3f(rand_coords[1][0], rand_coords[1][1], rand_coords[1][2]);
                glVertex3f(rand_coords[2][0], rand_coords[2][1], rand_coords[2][2]);
                glEnd();
                my_wm.swapBuffers();
            }
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    } // public void render(...)

    public void initOpenGL(slWindowManager window) {
        my_wm = window;
        my_wm.updateContextToThis();

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 1.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
    }
}
