package pkgSlPolygonRenderer;
import org.lwjgl.opengl.GL;
import pkgSlRenderer.CHslRenderEngine;
import pkgSlUtils.CHslWindowManager;
import pkgPingPongArray.CHslPingPongArray;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import java.util.Random;
import static pkgDriver.CHslSpot.*;

public class CHslPolygonRenderer extends CHslRenderEngine {
    private CHslWindowManager my_wm = new CHslWindowManager();
    private final int FACE_MINIMUM = 3;
    private final int FACE_MAXIMUM = 20;
    private final int COORDS_PER_VERTEX = 3;
    private final int X_COORD = 0;
    private final int Y_COORD = 1;
    private final int Z_COORD = 2;
    private final float Z = 0.0f;
    private final float WINDOW_RANGE = 2.0f;
    private final float ASPECT_RATIO = (float) WIN_HEIGHT / WIN_WIDTH;
    private final int DEFAULT_DELAY = 500;
    private final int DEFAULT_ROWS = 30;
    private final int DEFAULT_COLS = 30;
    private float[] center = new float[COORDS_PER_VERTEX];
    private final float opacity = 1.0f;
    private float radius = .05f;
    private float[] vertexOne = new float[COORDS_PER_VERTEX];
    private float[] vertexTwo = new float[COORDS_PER_VERTEX];
    private int randomCount = 100;
    private int frameDelay = 500;
    Random myRand = new Random();
    CHslPingPongArray game_board = new CHslPingPongArray();
    public void setRadius(float radius) {
        this.radius = radius;
    }
    public void setRandomCount(int count) {
        this.randomCount = count;
    }
    private void calculateRadius(int rows, int cols) {
        if (rows >= cols) {
            radius = (float) (WINDOW_RANGE / (rows)) / 2;
        }
        else if (cols > rows) {
            radius = (float) (WINDOW_RANGE / (cols)) / 2;
        }
    }
    private int calculateRows() {
        float rowCountTemp = (WINDOW_RANGE / (radius * 2));
        int rowCount = (int) Math.floor(rowCountTemp);
        return rowCount;
    }
    private int calculateCols() {
        float colCountTemp = (WINDOW_RANGE / (radius * 2));
        int colCount = (int) Math.floor(colCountTemp);
        return colCount;
    }
    private void generateVertices(int rows, int cols, int shapeCount) {
        center[X_COORD] = (-1 + radius);
        center[Y_COORD] = (1 - radius);
        center[Z_COORD] = (Z);
        if (shapeCount > 0) {
            while (shapeCount > cols) {
                center[Y_COORD] -= (WINDOW_RANGE / rows);
                shapeCount -= cols;
            }
            while (shapeCount > 1) {
                center[X_COORD] += (WINDOW_RANGE / cols);
                shapeCount--;
            }
        }
    }
    private void generateRandVertices() {
        float minX = radius - 1;
        float maxX = 1 - radius;
        float minY = radius - 1;
        float maxY = 1 - radius;
        float coord_1 = ((myRand.nextFloat() * (maxX - minX)) + minX);
        center[X_COORD] = coord_1;
        float coord_2 = ((myRand.nextFloat() * (maxY - minY)) + minY);
        center[Y_COORD] = coord_2;
        center[Z_COORD] = Z;
    }
    private void generateShapes(int faces) {
        float theta = (float) (Math.PI / faces);
        float thetaInterval = (float) (2 * Math.PI) / faces;
        float xRadius = radius * ASPECT_RATIO;
        vertexOne[X_COORD] = (float) (center[X_COORD] + (xRadius * Math.cos(theta)));
        vertexOne[Y_COORD] = (float) (center[Y_COORD] + (radius * Math.sin(theta)));
        vertexOne[Z_COORD] = Z;
        theta += thetaInterval;
        for (int triangle = 0; triangle < faces; triangle++) {
            vertexTwo[X_COORD] = (float) (center[X_COORD] + (xRadius * Math.cos(theta)));
            vertexTwo[Y_COORD] = (float) (center[Y_COORD] + (radius * Math.sin(theta)));
            vertexTwo[Z_COORD] = Z;
            theta += thetaInterval;
            glVertex3f(center[X_COORD], center[Y_COORD], center[Z_COORD]);
            glVertex3f(vertexOne[X_COORD], vertexOne[Y_COORD], vertexOne[Z_COORD]);
            glVertex3f(vertexTwo[X_COORD], vertexTwo[Y_COORD], vertexTwo[Z_COORD]);
            vertexOne[X_COORD] = vertexTwo[X_COORD];
            vertexOne[Y_COORD] = vertexTwo[Y_COORD];
        }
    }
    public void render() {
        render(DEFAULT_DELAY, DEFAULT_ROWS, DEFAULT_COLS);
    }
    public void renderGameOfLife(int rows, int cols) {
        calculateRadius(rows, cols);
        boolean keepRunning = true;
        game_board.createArray(rows, cols);
        while (keepRunning && !my_wm.isGlfwWindowClosed()) {
            game_board.gameOfLifeStep();
            game_board.swap();
            int shape = 1;
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLES);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int cell = game_board.getCellLive(row, col);
                    if (cell == 0) {
                        glColor4f(1.0f, 0.0f, 0.0f, opacity);
                    }
                    if (cell == 1) {
                        glColor4f(0.0f, 1.0f, 0.0f, opacity);
                    }
                    generateVertices(rows, cols, shape);
                    generateShapes(4);
                    shape++;
                }
            }
            if (frameDelay != 0) {
                try {
                    Thread.sleep(frameDelay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            glEnd();
            my_wm.swapBuffers();
            glClear(GL_COLOR_BUFFER_BIT);
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    }
    public void render(int frameDelay, int rows, int cols, int faces) {
        int shapes = rows * cols;
        calculateRadius(rows, cols);
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
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
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    }
    public void render(int frameDelay, int rows, int cols) {
        int shapes = rows * cols;
        calculateRadius(rows, cols);
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            //each pass fills window
            for (int faces = FACE_MINIMUM; faces <= FACE_MAXIMUM; faces++) {
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
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    }
    public void render(int frameDelay, float radius) {
        setRadius(radius);
        int cols = calculateCols();
        int rows = calculateRows();
        int shapes = rows * cols;
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            //each pass fills window
            for (int faces = FACE_MINIMUM; faces <= FACE_MAXIMUM; faces++) {
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
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    }
    public void renderRandom(int frameDelay, float radius) {
        setRadius(radius);
        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            //each pass fills window
            for (int faces = FACE_MINIMUM; faces <= FACE_MAXIMUM; faces++) {
                glBegin(GL_TRIANGLES);
                glColor4f(myRand.nextFloat(), myRand.nextFloat(), myRand.nextFloat(), opacity);
                //each pass creates one polygon
                for (int shape = 0; shape <= randomCount; shape++) {
                    generateRandVertices();
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
        } // while (!my_wm.isGlfwWindowClosed())
        my_wm.destroyGlfwWindow();
    }
    public void initOpenGL(CHslWindowManager window) {
        my_wm = window;
        my_wm.updateContextToThis();

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 0.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
    }
}