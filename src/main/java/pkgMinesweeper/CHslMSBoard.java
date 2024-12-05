package pkgMinesweeper;
import pkgDriver.CHslSpot;
public class CHslMSBoard {
    private int current_score;
    private Boolean game_active;
    private int ROWS = CHslSpot.NUM_POLY_ROWS;
    private int COLS = CHslSpot.NUM_POLY_COLS;
    private int NUM_MINES = CHslSpot.NUM_MINES;
    private CellData[][] ms_board = new CellData[ROWS][COLS];
    private final int SCORE_PER_MINE = 5;
    private final int SCORE_PER_GOLD = 40;
    private static class CellData {
        private CHslSpot.CELL_STATUS status;
        private CHslSpot.CELL_TYPE type;
        private int cell_score;
    }
    public void fill() {
        int mine = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (mine < NUM_MINES) {
                    ms_board[row][col].type = CHslSpot.CELL_TYPE.MINE;
                }
                if (mine >= NUM_MINES) {
                    ms_board[row][col].type = CHslSpot.CELL_TYPE.GOLD;
                }
                ms_board[row][col].status = CHslSpot.CELL_STATUS.NOT_EXPOSED;
                mine++;
            }
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ms_board[row][col].cell_score = calcScore(row, col);
            }
        }
    }
    public int calcScore(int row, int col) {
        int score = 0;
        int nextRow = (row + 1) % ROWS;
        int nextCol = (col + 1) % COLS;
        int prevRow = (row - 1 + ROWS) % ROWS;
        int prevCol = (col - 1 + COLS) % COLS;
        if(ms_board[row][col].type == CHslSpot.CELL_TYPE.MINE) {
            return score;
        }
        if(ms_board[row][col].type == CHslSpot.CELL_TYPE.GOLD) {
            score += SCORE_PER_GOLD;
        }
        if(ms_board[prevRow][prevCol].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[prevRow][col].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[prevRow][nextCol].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[row][prevCol].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[row][nextCol].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[nextRow][prevCol].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[nextRow][col].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        if(ms_board[nextRow][nextCol].type == CHslSpot.CELL_TYPE.MINE) {
            score += SCORE_PER_MINE;
        }
        return score;
    }
    public void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.printf(ms_board[row][col].type + " ");
            }
            System.out.println();
        }
    }
    public boolean isGameActive() {
        return game_active;
    }
    public void printCellScore() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.printf("%2d ", ms_board[row][col].cell_score);
            }
            System.out.println();
        }
    }
    public CHslSpot.CELL_STATUS getCellStatus(int row, int col) {
        return ms_board[row][col].status;
    }
    public CHslSpot.CELL_TYPE getCellType(int row, int col) {
        return ms_board[row][col].type;
    }
    public CHslSpot.CELL_TYPE changeCellStatus(int row, int col) {
        ms_board[row][col].status = CHslSpot.CELL_STATUS.EXPOSED;
        return ms_board[row][col].type;
    }
    public int getCurrentScore() {
        return current_score;
    }
}
