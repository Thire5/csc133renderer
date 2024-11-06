package pkgPingPongArray;

import java.util.Random;
import pkgSlPolygonRenderer.CHslPolygonRenderer;

public class CHslPingPongArray {
    private final int defaultRandMin = 0;
    private final int defaultRandMax = 1;
    private int rows;
    private int cols;
    private int[][] live;
    private int[][] next;
    private int frameDelay = 500;
    private final int faces = 4;
    Random rand = new Random();
    CHslPolygonRenderer renderer = new CHslPolygonRenderer();
    public void createArray(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        live = new int[rows][cols];
        next = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                live[row][col] = rand.nextInt(defaultRandMin, defaultRandMax + 1);
            }
        }
    }
    private void gameOfLifeStep() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                checkSurvive(row, col);
            }
        }
    }
    public void gameOfLife(int rows, int cols) {
        createArray(rows, cols);
        boolean keepRunning = true;
        while (keepRunning) {
            renderer.renderGameOfLife(live, rows, cols, faces);
            gameOfLifeStep();
            swap();
            if (frameDelay != 0) {
                try {
                    Thread.sleep(frameDelay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void createArray(int rows, int cols, int value) {
        this.rows = rows;
        this.cols = cols;
        live = new int[rows][cols];
        next = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                live[row][col] = value;
            }
        }
    }
    public void createArray(int rows, int cols, int min, int max) {
        this.rows = rows;
        this.cols = cols;
        live = new int[rows][cols];
        next = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                live[row][col] = rand.nextInt(min, max + 1);
            }
        }
    }
    public void reset() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                live[row][col] = rand.nextInt(defaultRandMin, defaultRandMax + 1);
            }
        }
    }
    public void reset(int min, int max) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                live[row][col] = rand.nextInt(min, max + 1);
            }
        }
    }
    public void printLive() {
        for (int row = 0; row < live[0].length; row++) {
            for (int col = 0; col < live[0].length; col++) {
                System.out.print(live[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public void setCellLive(int row, int col, int value) {
        live[row][col] = value;
    }
    private void setCellNext(int row, int col, int value) {
        next[row][col] = value;
    }
    public int getCellLive(int row, int col) {
        return live[row][col];
    }
    public void swap() {
        int[][] temp = live;
        live = next;
        next = temp;
    }
    public void createNearestArray() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                next[row][col] = countNearest(row, col);
            }
        }
    }
    public void createNextNearestArray() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                next[row][col] = countNextNearest(row, col);
            }
        }
    }
    private void checkSurvive(int row, int col) {
        int neighbors = countNextNearest(row, col);
        if (live[row][col] == 0) {
            if (neighbors == 3) {
                next[row][col] = 1;
            }
        }
        if (live[row][col] == 1) {
            if (neighbors == 3 || neighbors == 2) {
                next[row][col] = 1;
            }
            if (neighbors < 2 || neighbors > 3) {
                next[row][col] = 0;
            }
        }
        else
            System.out.println("invalid cell identity");
    }
    public int countNearest(int row, int col) {
        int count = 0;
        int nextRow = (row + 1) % rows;
        int nextCol = (col + 1) % cols;
        int prevRow = (row - 1 + rows) % rows;
        int prevCol = (col - 1 + cols) % cols;
        count += live[prevRow][col];
        count += live[row][prevCol];
        count += live[nextRow][col];
        count += live[row][nextCol];
        return count;
    }
    public int countNextNearest(int row, int col) {
        int count = 0;
        int nextRow = (row + 1) % rows;
        int nextCol = (col + 1) % cols;
        int prevRow = (row - 1 + rows) % rows;
        int prevCol = (col - 1 + cols) % cols;
        count += countNearest(row, col);
        count += live[prevRow][prevCol];
        count += live[prevRow][nextCol];
        count += live[nextRow][nextCol];
        count += live[nextRow][prevCol];
        return count;
    }
}