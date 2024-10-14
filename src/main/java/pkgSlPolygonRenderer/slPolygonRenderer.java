package pkgSlPolygonRenderer;

import pkgSlRenderer.slRenderEngine;

public class slPolygonRenderer extends slRenderEngine {
    private final int faceMinimum = 3;
    private final int faceMaximum = 20;
    private final int coordsPerVertex = 3;
    private final int xCoord = 0;
    private final int yCoord = 1;
    private final int zCoord = 2;
    private final float z = 0.0f;
    private float[] center = new float[coordsPerVertex];
    private void generateVertices(int rows, int cols, int shapeCount) {
        float radius = 0;
        if(rows >= cols) {
            radius = (float) (2.0 / (rows * 2));
        }
        else if(cols >= rows) {
            radius = (float) (2.0 / (cols * 2));
        }
        center[xCoord] = (-1 + radius);
        center[yCoord] = (1 - radius);
        center[zCoord] = (z);
        if(shapeCount > 0) {
            while(shapeCount > cols) {
                center[yCoord] -= (radius * 2);
                shapeCount -= cols;
            }
            while(shapeCount > 0) {
                center[xCoord] += (radius * 2);
                shapeCount--;
            }
        }
    }
    private void generateShapes(int faces) {

    }
    public void render(int frameDelay, int rows, int cols) {
        int shapes = rows * cols;
        for(int faces = faceMinimum; faces <= faceMaximum; faces++) {
            for (int shape = 0; shape < shapes; shape++) {
                generateVertices(rows, cols, shape);
                generateShapes(faces);
            }
        }
    }
}
