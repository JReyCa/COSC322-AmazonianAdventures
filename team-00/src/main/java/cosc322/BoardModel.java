package cosc322;

import java.util.ArrayList;
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
    
    private String[][] gameBoard = null;    // the 2D array of positions, with strings representing what's on them
    public ArrayList<int[]> queenPositions = new ArrayList<int[]>(8); // all the positions of the queens
    
    public int getSize() {
        return gameBoard.length;
    }
    
    public String getTile(int row, int column) {
        return gameBoard[row][column];
    }
    
    public String getTile(int[] position) {
        return gameBoard[position[0]][position[1]];
    }
    
    public BoardModel(int size) {
        gameBoard = new String[size][size];
        initialize(size);
    }
    
    public BoardModel() {
        gameBoard = new String[10][10];
        initialize(10);
        setAmazonsQueens();
    }
    
    // set up the board for game start
    private void initialize(int size) {
        
        // mark each tile as initially unoccupied
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = POS_AVAILABLE;
            }
        }
    }
    
    // set the initial positions of the queens for the amazons game
    private void setAmazonsQueens() {
        setQueen(new int[] {0, 3}, true);
        setQueen(new int[] {0, 6}, true);
        setQueen(new int[] {3, 0}, true);
        setQueen(new int[] {3, 9}, true);
        setQueen(new int[] {6, 0}, false);
        setQueen(new int[] {6, 9}, false);
        setQueen(new int[] {9, 3}, false);
        setQueen(new int[] {9, 6}, false);  
    }
    
    // change the game model to reflect a valid move
    public void makeMove(int[] oldQueenPosition, int[] newQueenPosition, int[] arrowPosition) {
        moveQueen(oldQueenPosition, newQueenPosition);
        setTile(arrowPosition, POS_MARKED_ARROW);
    }
    
    public void makeMove(Move move) {
        makeMove(move.getOldQueenPosition(), move.getNewQueenPosition(), move.getArrowPosition());
    }
    
    // make sure that this move is valid! (especially for dumb bots)
    // WARNING: the code below is exceedingly boring
    public String validateMove(int[] oldQueenPosition, int[] newQueenPosition, int[] arrowPosition) {
        String message = VALID;
        
        // don't select positions that are out of bounds
        if (isOutOfBounds(oldQueenPosition, this)) {
            message = SELECTED_OUT_OF_BOUNDS;
        }
        // don't try to move empty positions
        else if (getTile(oldQueenPosition).equalsIgnoreCase(POS_AVAILABLE)) {
            message = SELECTED_EMPTY;
        }
        // don't try to move arrows
        else if (getTile(oldQueenPosition).equalsIgnoreCase(POS_MARKED_ARROW)) {
            message = SELECTED_ARROW;
        }
        // don't try to move a queen or fire an arrow out of bounds
        else if (isOutOfBounds(newQueenPosition, this) || isOutOfBounds(arrowPosition, this)) {
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
        else if (!pathIsClear(oldQueenPosition, newQueenPosition, this) ||
                 !pathIsClear(newQueenPosition, arrowPosition, this)) {
            message = TARGET_OCCUPIED;
        }
        
        return message;
    }
    
    public String validateMove(Move move) {
        return validateMove(move.getOldQueenPosition(), move.getNewQueenPosition(), move.getArrowPosition());
    }
    
    // check whether a position is out of bounds
    private static boolean isOutOfBounds(int[] position, BoardModel model) {
        return position[0] < 0 || position[0] >= model.gameBoard.length ||
               position[1] < 0 || position[1] >= model.gameBoard.length;
    }
    
    // check whether all positions between two positions are unoccupied
    private static boolean pathIsClear(int[] position1, int[] position2, BoardModel model) {
        
        // determine the direction between the tiles
        int rowChange = 0;
        int columnChange = 0;
        
        if (position1[0] > position2[0])
            rowChange = -1;
        else if (position1[0] < position2[0])
            rowChange = 1;
        
        if (position1[1] > position2[1])
            columnChange = -1;
        else if (position1[1] < position2[1]) {
            columnChange = 1;
        }
        
        // iterate through the positions between the two (inclusive of the second position)
        int r = position1[0], c = position1[1];
        while (r != position2[0]  || c != position2[1] ) {
            r += rowChange;
            c += columnChange;
            
            if (!model.gameBoard[r][c].equalsIgnoreCase(POS_AVAILABLE)) {
                return false;
            }
        }
        
        return true;
    }
    
    // check whether two positions are the same
    private static boolean isSamePosition(int[] position1, int[] position2) {
        return position1[0] == position2[0] && position1[1] == position2[1];
    }
    
    // check whether two positions are diagonal, relative to each other
    private static boolean isDiagonal(int[] position1, int[] position2) {
        return Math.abs(position1[0] - position2[0]) - Math.abs(position1[1] - position2[1]) == 0;
    }
    
    // check whether two positions are orthagonal, relative to each other
    private static boolean isOrthagonal(int[] position1, int[] position2) {
        return position1[0] == position2[0] && position1[1] != position2[1] ||
               position1[0] != position2[0] && position1[1] == position2[1];
    }
    
    public void setTile(int[] position, String occupant) {
        gameBoard[position[0]][position[1]] = occupant;
    }
    
    // set a white or black queen at the specified position
    private void setQueen(int[] position, boolean isWhite) {
        setTile(position, isWhite ? POS_MARKED_WHITE : POS_MARKED_BLACK);
        queenPositions.add(position);
    }
    
    // remove the queen at the specified position
    private void removeQueen(int[] position) {
        setTile(position, POS_AVAILABLE);
        queenPositions.removeIf(pos -> pos.equals(position));
    }
    
    public void moveQueen(int[] position1, int[] position2) {
        setQueen(position2, getTile(position1).equalsIgnoreCase(POS_MARKED_WHITE));
        removeQueen(position1);
    }
}
