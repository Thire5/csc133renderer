package pkgPingPongArray;

import java.util.Random;

public class CHslPingPongArray {
    private final int defaultRandMin = 0;
    private final int defaultRandMax = 1;
    private int rows;
    private int cols;
    private int[][] live;
    private int[][] next;
    Random rand = new Random();
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