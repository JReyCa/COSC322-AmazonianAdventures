package cosc322;

import ygraphs.ai.smart_fox.games.GameModel;

/**
 *
 * @author julian
 */
public class BoardModel extends GameModel {
    public static final String POS_MARKED_BLACK = "black";
    public static final String POS_MARKED_WHITE = "white";
    public static final String POS_MARKED_ARROW = "arrow";
    public static final String POS_AVAILABLE = "available";
    
    private String[][] gameBoard = null;
    
    public int getSize() {
        return gameBoard.length;
    }
    
    public String getTile(int row, int column) {
        return gameBoard[row][column];
    }
    
    public void setTile(int row, int column, String occupant) {
        gameBoard[row][column] = occupant;
    }
    
    // set a white or black queen at the specified tile (used for initialization)
    public void setQueen(int row, int column, boolean isWhite) {
        setTile(row, column, isWhite ? POS_MARKED_WHITE : POS_MARKED_BLACK);
    }
    
    public BoardModel(int size) {
        gameBoard = new String[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = POS_AVAILABLE;
            }
        }
    }
    
    // change the game model to reflect a valid move
    public boolean makeMove(int newQueenRow, int newQueenColumn,
                                int arrowRow, int arrowColumn,
                                int oldQueenRow, int oldQueenColumn) {
        boolean isValid = true;
        
        if (newQueenRow >= gameBoard.length | newQueenColumn >= gameBoard.length) {
            isValid = false;
        }
        else if (!gameBoard[newQueenRow][newQueenColumn].equalsIgnoreCase(POS_AVAILABLE)) {
            isValid = false;
        }
        
        if (isValid) {
            gameBoard[newQueenRow][newQueenColumn] = gameBoard[oldQueenRow][oldQueenColumn];
            gameBoard[oldQueenRow][oldQueenColumn] = POS_AVAILABLE;
            gameBoard[arrowRow][arrowColumn] = POS_MARKED_ARROW;
        }
        
        return isValid;
    }
}
