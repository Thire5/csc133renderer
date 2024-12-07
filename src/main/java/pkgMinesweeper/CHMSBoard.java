package pkgMinesweeper;
import pkgDriver.CHSpot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
public class CHMSBoard {
    private int current_score;
    private Boolean game_active;
    private int ROWS = CHSpot.NUM_POLY_ROWS;
    private int COLS = CHSpot.NUM_POLY_COLS;
    private int NUM_MINES = CHSpot.NUM_MINES;
    private CellData[][] ms_board;
    private final int SCORE_PER_MINE = 5;
    private final int SCORE_PER_GOLD = 40;
    private static class CellData {
        private CHSpot.CELL_STATUS status;
        private CHSpot.CELL_TYPE type;
        private int cell_score;
    }
    public void fill() {
        ms_board = new CellData[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ms_board[row][col] = new CellData();
            }
        }
        int mine = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (mine < NUM_MINES) {
                    ms_board[row][col].type = CHSpot.CELL_TYPE.MINE;
                }
                if (mine >= NUM_MINES) {
                    ms_board[row][col].type = CHSpot.CELL_TYPE.GOLD;
                }
                ms_board[row][col].status = CHSpot.CELL_STATUS.NOT_EXPOSED;
                mine++;
            }
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ms_board[row][col].cell_score = calcScore(row, col);
            }
        }
        List<CellData> cells = new ArrayList<>();
        for(int row = 0; row < ROWS; row++) {
            cells.addAll(Arrays.asList(ms_board[row]).subList(0, COLS));
        }
        Collections.shuffle(cells);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ms_board[row][col] = cells.get(row * COLS + col);
            }
        }
        game_active = true;
    }
    public void gameStep(int row, int col) {
        if (game_active) {
            ms_board[row][col].status = CHSpot.CELL_STATUS.EXPOSED;
            current_score += ms_board[row][col].cell_score;
            if (ms_board[row][col].type == CHSpot.CELL_TYPE.MINE) {
                game_active = false;
                for (int ro = 0; ro < ROWS; ro++) {
                    for (int co = 0; co < COLS; co++) {
                        ms_board[ro][co].status = CHSpot.CELL_STATUS.EXPOSED;
                    }
                }
                System.out.println("Better luck next time!");
            }
        }
    }
    public int calcScore(int row, int col) {
        int score = 0;
        int nextRow = (row + 1) % ROWS;
        int nextCol = (col + 1) % COLS;
        int prevRow = (row - 1 + ROWS) % ROWS;
        int prevCol = (col - 1 + COLS) % COLS;
        if(ms_board[row][col].type == CHSpot.CELL_TYPE.MINE) {
            return score;
        }
        if(ms_board[row][col].type == CHSpot.CELL_TYPE.GOLD) {
            score += SCORE_PER_GOLD;
        }
        if(ms_board[prevRow][prevCol].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[prevRow][col].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[prevRow][nextCol].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[row][prevCol].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[row][nextCol].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[nextRow][prevCol].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[nextRow][col].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[nextRow][nextCol].type == CHSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        return score;
    }
    public void printBoard() {
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(ms_board[row][col].type + " ");
            }
            System.out.println();
        }
    }
    public boolean isGameActive() {
        return game_active;
    }
    public void printCellScores() {
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {
                System.out.printf("%2d ", ms_board[row][col].cell_score);
            }
            System.out.println();
        }
    }
    public CHSpot.CELL_STATUS getCellStatus(int row, int col) {
        return ms_board[row][col].status;
    }
    public CHSpot.CELL_TYPE getCellType(int row, int col) {
        return ms_board[row][col].type;
    }
    public CHSpot.CELL_TYPE changeCellStatus(int row, int col) {
        ms_board[row][col].status = CHSpot.CELL_STATUS.EXPOSED;
        return ms_board[row][col].type;
    }
    public int getCurrentScore() {
        return current_score;
    }
}
