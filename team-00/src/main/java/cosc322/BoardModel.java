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
    
    public int getRowCount() {
        return gameBoard.length;
    }
    
    public int getColumnCount() {
        return gameBoard[0].length;
    }
    
    public BoardModel(int rows, int columns) {
        gameBoard = new String[rows][columns];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gameBoard[i][j] = POS_AVAILABLE;
            }
        }
    }
}
