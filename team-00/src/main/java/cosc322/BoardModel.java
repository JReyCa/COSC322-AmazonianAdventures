package cosc322;

import ygraphs.ai.smart_fox.games.GameModel;

/**
 *
 * @author julian
 */
public class BoardModel extends GameModel {
    // these strings denote what can occupy a position on the board
    public static final String POS_MARKED_BLACK = "black";
    public static final String POS_MARKED_WHITE = "white";
    public static final String POS_MARKED_ARROW = "arrow";
    public static final String POS_AVAILABLE = "available";
    
    // these strings are possible error messages
    public static final String VALID = "Valid";
    public static final String SELECTED_OUT_OF_BOUNDS = "Tried to move a queen from a tile that's out of bounds!";
    public static final String SELECTED_ARROW = "Tried to move an arrow!";
    public static final String SELECTED_EMPTY = "Tried to move a queen from an empty tile!";
    public static final String TARGET_OUT_OF_BOUNDS = "Tried to positon on a tile that's out of bounds!";
    public static final String TARGET_OCCUPIED = "Tried to position on or through a tile that's occupied!";
    public static final String IRREGULAR_ARROW = "Tried firing an arrow not in a straight line!";
    public static final String IRREGULAR_QUEEN_MOVE = "Tried moving a queen not in a straight line!";
    
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
        initialize(size);
    }
    
    // set up the board for game start
    private void initialize(int size) {
        
        // mark each tile as initially unoccupied
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = POS_AVAILABLE;
            }
        }
        
        // set the initial positions of the queens
        setQueen(0, 3, true);
        setQueen(0, 6, true);
        setQueen(2, 0, true);
        setQueen(2, 9, true);
        setQueen(7, 0, false);
        setQueen(7, 9, false);
        setQueen(9, 3, false);
        setQueen(9, 6, false);
    }
    
    // change the game model to reflect a valid move
    // WARNING: the code below is exceedingly boring
    public String makeMove(int[] oldQueenPosition,
                           int[] newQueenPosition,
                           int[] arrowPosition) {
        String message = VALID;
        
        // don't select positions that are out of bounds
        if (isOutOfBounds(oldQueenPosition)) {
            message = SELECTED_OUT_OF_BOUNDS;
        }
        // don't try to move empty positions
        else if (gameBoard[oldQueenPosition[0]][oldQueenPosition[1]].equalsIgnoreCase(POS_AVAILABLE)) {
            message = SELECTED_EMPTY;
        }
        // don't try to move arrows
        else if (gameBoard[oldQueenPosition[0]][oldQueenPosition[1]].equalsIgnoreCase(POS_MARKED_ARROW)) {
            message = SELECTED_ARROW;
        }
        // don't try to move a queen or fire an arrow out of bounds
        else if (isOutOfBounds(newQueenPosition) || isOutOfBounds(arrowPosition)) {
            message = TARGET_OUT_OF_BOUNDS;
        }
        // don't move a queen non-orthagonally and non-diagonally
        else if (isSamePosition(oldQueenPosition, newQueenPosition) || 
                 (!isDiagonal(oldQueenPosition, newQueenPosition) && !isOrthagonal(oldQueenPosition, newQueenPosition))) {
            message = IRREGULAR_QUEEN_MOVE;
        }
        // don't try to fire an arrow non-orthagonally and non-diagonally
        else if (isSamePosition(newQueenPosition, arrowPosition) || 
                 (!isDiagonal(newQueenPosition, arrowPosition) && !isOrthagonal(newQueenPosition, arrowPosition))) {
            message = IRREGULAR_ARROW;
        }
        // don't try to move a queen to or fire an arrow at or through an occupied tile
        else if (!pathIsClear(oldQueenPosition, newQueenPosition) ||
                 !pathIsClear(newQueenPosition, arrowPosition)) {
            message = TARGET_OCCUPIED;
        }
        
        // if by some miracle the move is valid, make it so
        if (message.equalsIgnoreCase(VALID)) {
            gameBoard[newQueenPosition[0]][newQueenPosition[1]] = gameBoard[oldQueenPosition[0]][oldQueenPosition[1]];
            gameBoard[oldQueenPosition[0]][oldQueenPosition[1]] = POS_AVAILABLE;
            gameBoard[arrowPosition[0]][arrowPosition[1]] = POS_MARKED_ARROW;
        }
        
        return message;
    }
    
    // check whether a position is out of bounds
    private boolean isOutOfBounds(int[] position) {
        return position[0] < 0 || position[0] >= gameBoard.length ||
               position[1] < 0 || position[1] >= gameBoard.length;
    }
    
    // check whether the straight path between two positions is clear
    private boolean pathIsClear(int[] position1, int[] position2) {
        
        // determine the direction between the tiles
        int rowChange = 0;
        int columnChange = 0;
        
        if (position1[0] > position2[0])
            rowChange = -1;
        else if (position1[0] < position2[0])
            rowChange = 1;
        
        if (position1[1] > position2[0])
            columnChange = -1;
        else if (position1[1] < position2[1]) {
            columnChange = 1;
        }
        
        // iterate through the tiles between the two (inclusive of the second)
        int r = position1[0], c = position1[1];
        while (r != position2[0] + rowChange || c != position2[1] + columnChange) {
            r += rowChange;
            c += columnChange;
            
            if (!gameBoard[r][c].equalsIgnoreCase(POS_AVAILABLE)) {
                return false;
            }
        }
        
        return true;
    }
    
    // check whether two positions are the same
    private boolean isSamePosition(int[] position1, int[] position2) {
        return position1[0] == position2[0] && position1[1] == position2[1];
    }
    
    // check whether two positions are diagonal, relative to each other
    private boolean isDiagonal(int[] position1, int[] position2) {
        return Math.abs(position1[0] - position2[0]) - Math.abs(position1[1] - position2[1]) == 0;
    }
    
    // check whether two positions are orthagonal, relative to each other
    private boolean isOrthagonal(int[] position1, int[] position2) {
        return position1[0] == position2[0] && position1[1] != position2[1] ||
               position1[0] != position2[0] && position1[1] == position2[1];
    }
}
