package pkgSlPolygonRenderer;

import org.lwjgl.opengl.GL;
import pkgSlRenderer.slRenderEngine;
import pkgSlUtils.slWindowManager;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import java.util.Random;
import static pkgDriver.slSpot.*;
import static java.lang.Math.PI;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class slPolygonRenderer extends slRenderEngine {
    private slWindowManager my_wm = new slWindowManager();
    private final int faceMinimum = 3;
    private final int faceMaximum = 20;
    private final int coordsPerVertex = 3;
    private final int xCoord = 0;
    private final int yCoord = 1;
    private final int zCoord = 2;
    private final float z = 0.0f;
    private final float windowRange = 2.0f;
    private float[] center = new float[coordsPerVertex];
    private final float opacity = 1.0f;
    private float radius = .05f;
    private float[] vertexOne = new float[coordsPerVertex];
    private float[] vertexTwo = new float[coordsPerVertex];
    Random myRand = new Random();

    private void generateVertices(int rows, int cols, int shapeCount) {
        if (rows >= cols) {
            radius = (float) (windowRange / (rows));
        } else if (cols > rows) {
            radius = (float) (windowRange / (cols * 2));
        }
        center[xCoord] = (-1 + radius);
        center[yCoord] = (1 - radius);
        center[zCoord] = (z);
        if (shapeCount > 0) {
            while (shapeCount > cols) {
                center[yCoord] -= (radius * 2);
                shapeCount -= cols;
            }
            while (shapeCount > 1) {
                center[xCoord] += (radius * 2);
                shapeCount--;
            }
        }
    }
    private void generateShapes(int faces) {
        float theta = 0.0f;
        float thetaInterval = (float) (2 * Math.PI) / faces;
        float aspectRatio = (float) WIN_HEIGHT / WIN_WIDTH;
        float xRadius = radius * aspectRatio;
        vertexOne[xCoord] = (float) (center[xCoord] + (xRadius * Math.cos(theta)));
        vertexOne[yCoord] = (float) (center[yCoord] + (radius * Math.sin(theta)));
        vertexOne[zCoord] = z;
        theta += thetaInterval;
        for (int triangle = 0; triangle < faces; triangle++) {
            vertexTwo[xCoord] = (float) (center[xCoord] + (xRadius * Math.cos(theta)));
            vertexTwo[yCoord] = (float) (center[yCoord] + (radius * Math.sin(theta)));
            vertexTwo[zCoord] = z;
            theta += thetaInterval;
            glVertex3f(center[xCoord], center[yCoord], center[zCoord]);
            glVertex3f(vertexOne[xCoord], vertexOne[yCoord], vertexOne[zCoord]);
            glVertex3f(vertexTwo[xCoord], vertexTwo[yCoord], vertexTwo[zCoord]);
            vertexOne[xCoord] = vertexTwo[xCoord];
            vertexOne[yCoord] = vertexTwo[yCoord];
        }
    }
    public void render(int frameDelay, int rows, int cols) {
        int shapes = rows * cols;
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            //each pass fills window
            for (int faces = faceMinimum; faces <= faceMaximum; faces++) {
                glBegin(GL_TRIANGLES);
                glColor4f(myRand.nextFloat(), myRand.nextFloat(), myRand.nextFloat(), opacity);
                //each pass creates one polygon
                for (int shape = 1; shape <= shapes; shape++) {
                    generateVertices(rows, cols, shape);
                    generateShapes(faces);
                }
                glEnd();
                if (frameDelay != 0) {
                    try {
                        Thread.sleep(frameDelay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                my_wm.swapBuffers();
                glClear(GL_COLOR_BUFFER_BIT);
            }
            //my_wm.swapBuffers();

        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    }
    public void initOpenGL(slWindowManager window) {
        my_wm = window;
        my_wm.updateContextToThis();

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 0.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
    }
}