package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import java.util.Random;
import static pkgDriver.slSpot.*;
import static java.lang.Math.PI;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
public class slRenderEngine {
    private final int NUM_RGBA = 4;
    private final int NUM_3D_COORDS = 3;
    private final int TRIANGLES_PER_CIRCLE = 40;
    private final float C_RADIUS = 0.05f;
    private final int MAX_CIRCLES = 100;
    private final int UPDATE_INTERVAL = 100;
    private float[] rand_colors = new float[NUM_RGBA];
    Random myRand = new Random();
    private slWindowManager my_wm = new slWindowManager();
    private float[] rand_coords = new float[NUM_3D_COORDS];
    private float[] vertex_one = new float[NUM_3D_COORDS];
    private float[] vertex_two = new float[NUM_3D_COORDS];
    private final float opacity = 1.0f;
    private final float z = 0.0f;
    private float thetaInterval = (float)(Math.PI)/20;
    private void updateRandVertices() {
        float minX = C_RADIUS - 1;
        float maxX = 1 - C_RADIUS;
        float minY = C_RADIUS - 1;
        float maxY = 1 - C_RADIUS;
        float coord_1 = ((myRand.nextFloat() * (maxX - minX)) + minX);
        rand_coords[0] = coord_1;
        float coord_2 = ((myRand.nextFloat() * (maxY - minY)) + minY);
        rand_coords[1] = coord_2;
        rand_coords[2] = z;
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
    private void generateCircleSegmentVertices(float x, float y, float theta) {
        float aspectRatio = (float) WIN_HEIGHT / WIN_WIDTH;
        float xRadius = C_RADIUS * aspectRatio;
        vertex_one[0] = (float) (rand_coords[0] + (xRadius * Math.cos(theta)));
        vertex_one[1] = (float) (rand_coords[1] + (C_RADIUS * Math.sin(theta)));
        vertex_one[2] = z;
        vertex_two[0] = x;
        vertex_two[1] = y;
        vertex_two[2] = z;
    }
    public void render() {
        final float begin_angle = 0.0f, end_angle = (float) (2.0f * PI);
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            for(int circles = 0; circles < MAX_CIRCLES; circles++) {
                glBegin(GL_TRIANGLES);
                float theta = 0.0f;
                updateRandColors();
                updateRandVertices();
                generateCircleSegmentVertices(0.0f, 0.0f, begin_angle);
                theta += thetaInterval;
                for (int triangles = 0; triangles < TRIANGLES_PER_CIRCLE; triangles++) {
                    generateCircleSegmentVertices(vertex_one[0], vertex_one[1], theta);
                    theta += thetaInterval;
                    glColor4f(rand_colors[0], rand_colors[1], rand_colors[2], rand_colors[3]);
                    glVertex3f(rand_coords[0], rand_coords[1], rand_coords[2]);
                    glVertex3f(vertex_one[0], vertex_one[1], vertex_one[2]);
                    glVertex3f(vertex_two[0], vertex_two[1], vertex_two[2]);
                }
                glEnd();
            }
            my_wm.swapBuffers();
            if(UPDATE_INTERVAL != 0) {
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
