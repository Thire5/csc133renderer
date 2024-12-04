package pkgMinesweeper;
import pkgDriver.CHslSpot;
public class CHslMSBoard {
    private int current_score;
    private Boolean game_active;
    private int ROWS = CHslSpot.NUM_POLY_ROWS;
    private int COLS = CHslSpot.NUM_POLY_COLS;
    private int NUM_MINES = CHslSpot.NUM_MINES;
    private CellData[][] ms_board = new CellData[ROWS][COLS];
    private static class CellData {
        private CHslSpot.CELL_STATUS status;
        private CHslSpot.CELL_TYPE type;
        private int cell_score;
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
