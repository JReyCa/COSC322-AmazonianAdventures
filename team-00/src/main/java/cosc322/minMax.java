/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;
import static cosc322.BoardModel.POS_MARKED_ARROW;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import ygraphs.ai.smart_fox.games.GameModel;

/**
 *
 * @author EmilyEarl
 */
public class minMax {
    
    // This class will determine based on the minimax algorithm which 
    // positions belong to which player (black or white). 
    static String[][] ownedBy = new String[10][10];
    
    
    public static void main(String[] args){
       int[][] test =  queenMoves(new int[] {0,3}, new BoardModel());
       for (int i = 0; i < test.length; i++){
           for (int j = 0; j < test.length; j++){
               System.out.print(" " + test[i][j]);
           }
           System.out.println("");
       }
      calculateOwnership(new BoardModel());
      for (int i = 0; i < ownedBy.length; i++){
           for (int j = 0; j < ownedBy.length; j++){
               System.out.print(" " + ownedBy[i][j]);
           }
           System.out.println("");
       }
    }
    
    public static 
    
    public static void applyMinMax(int[] currentQueen, int d, BoardModel model){
        // need to apply the min-max algorithm to determine the best move
        // The entity to maximize and minimize respectively per turn is assumed to be the number
        // of tiles "owned" on the board. 
        // How do we do this? 
        // We need to look at all possible moves 1 step away that will maximize the number of spaces 
        // that the assigned player has on the board. We need to check each 1 step move for each 
        // same coloured queen. 
        // We need to make a graph that considers: the current positions of the queens ,the number of 
        // tiles that the same coloured queens will own if it moves to any other viable location.
        // maybe make a different version - one for each of the 4 queens? 
        
        // alpha is the number to maximize, this is equal to the number of spaces on the board 
        // OUR team owns. 
        // beta is the number to minimize, this is equal to the number of spaces on the board 
        // that the OTHER team owns. 
        
        
       // need to instantiate some form of an arraylist or something to hold all of the possible
       // moves and successor moves. TODO: 
       
        int alpha = -10000000;
        int beta = 10000000; 
        int score = -1000000; 
        int[][] moves = queenMoves(currentQueen, model);
        for (int i = 0; i < moves.length; i++){ 
            for (int j = 0; j < moves[0].length; j++){ 
                // based on the make move method, we need to test for every single arrow shot..
                // step 1. test every single queen (NEED TO CALL STILL) 
                // step 2. test every possible 1 step move for every queen (DONE) 
                // step 3. with that queen moved, test every possible arrow (DONE) 
                // step 4. add all move pairs into a graph as a node
                //      each move pair is a combination of the movement of a queen and the 
                //      placement of an arrow. We need to complete all of these steps FIRST 
                //      and THEN add all options to a graph as nodes. 
                
                // When we make a new node in the tree we need to add a visualization of 
                // the board if that move were to take place, as well as the location of the
                // queen we moved and the location of the arrow we placed 
                // we need to distinguish how far down in the thing we are, I suppose we can use
                // d? 
                if (moves[i][j] == 1){
                    // need to move queen independent of making an arrow shot 
                    model.moveQueen(currentQueen, new int[] {i,j});
                    // step 3. test every possible arrow 
                    // arrows can move the exact same as queens, so we can use the queen moves
                    // check on the queen that has just been relocated. 
                    int[][] arrowMoves = queenMoves(new int[] {i,j}, model);
                    for (int x = 0; x < arrowMoves.length; x++){
                        for (int y = 0; j < arrowMoves[0].length; y++){
                            if (arrowMoves[x][y] == 1){
                                model.setTile(new int[] {x,y}, POS_MARKED_ARROW);
                                // now, we need to calculate who owns which part of the board
                                // considering this move was played. 
                               calculateOwnership(model); 
                               int numWhite = 0; 
                               int numBlack = 0; 
                               for (int u = 0; u < ownedBy.length; u++){
                                   for (int v = 0; v < ownedBy.length; v++){
                                       if (ownedBy[u][v].equals("white")){
                                           numWhite++; 
                                       }
                                       if (ownedBy[u][v].equals("black")){
                                           numBlack++; 
                                       }
                                   }
                               }
                              
                            }
                        }
                    }
                }
            }
        }
        
        
        
        
        
    }
    
    
    class allPossibleMoves{
        
    }
    class possibleMove{
        int depth; 
        int[] oldQueenLoc; 
        int[] newQueenLoc; 
        int[] arrowLoc;
        
    possibleMove(int depth, int[] oldQueenLoc, int[] newQueenLoc, int[] arrowLoc){
        this.depth = depth; 
        this.oldQueenLoc = oldQueenLoc;
        this.newQueenLoc = newQueenLoc; 
        this.arrowLoc = arrowLoc;
        
    }
    }
    public static void calculateOwnership(BoardModel model){
        
        // if number < current Numer and greater than zero, replace. 
        // if number == current Number and greater than zero and colours same - do nothing
        // if number == current number and greater than zero and colours different - set blank. 
        for (int r = 0; r < 10; r++){
            for (int c = 0; c < 10; c++){
                if (model.getTile(r,c) == BoardModel.POS_AVAILABLE){
                    int smallestMoves = 100; 
                    String closestQueen = ""; 
                    for (int i = 0; i < 8; i ++){
                        int[][] currentQueenArray = queenMoves(model.queenPositions.get(i), model);
                        if (currentQueenArray[r][c] < smallestMoves && currentQueenArray[r][c] > 0){
                            smallestMoves = currentQueenArray[r][c]; 
                            closestQueen = model.getTile(model.queenPositions.get(i)); 
                        }
                        else if (currentQueenArray[r][c] == smallestMoves && currentQueenArray[r][c] > 0 && !closestQueen.equals(model.getTile(model.queenPositions.get(i)))){
                            closestQueen = "noone";
                        }
                    }
                    
                    ownedBy[r][c] = closestQueen;
                }
            }
        }
}
    

    // Queen moves will return an integer array saying how many moves it takes for that queen
    // to get to each space on the board for all numbers greater than 0. A zero on the board
    // suggests that that location is either where the queen is, or that location is blocked by 
    // either another queen or an arrow. 
    
    public static int[][] queenMoves(int[] currentQueen, BoardModel model){
         
        int[][] moveLevel = new int[10][10]; 
        boolean[][] visited = new boolean[10][10];
        Queue <int[]> nodesToCheck = new LinkedList<>();
        //initialize the queen array to show the location of the queen and the 
        // number of moves it takes to get to the queen. 
        int[] initQueen = new int[3];
        initQueen[0] = currentQueen[0];
        initQueen[1] = currentQueen[1]; 
        initQueen[2] = 0; 
        moveLevel[initQueen[0]][initQueen[1]] = initQueen[2]; 
        nodesToCheck.add(initQueen);
        while (!nodesToCheck.isEmpty()){
            int[] neighbourNode = nodesToCheck.poll(); 
        // search nodes above until positions are no longer available; 
        for (int i = neighbourNode[0] + 1; i < 10; i++){
            if (!visited[i][neighbourNode[1]]){
                if (model.getTile(i,neighbourNode[1]) == BoardModel.POS_AVAILABLE){
                  visited[i][neighbourNode[1]] = true; 
                  int[] initCurrentSpot = new int[3];
                  initCurrentSpot[0] = i; 
                  initCurrentSpot[1] = neighbourNode[1]; 
                  initCurrentSpot[2] = neighbourNode[2] + 1; 
                  moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                
                  nodesToCheck.add(initCurrentSpot);
                 
            }
            else {
                break; 
                    
                }
            }
        }
        // search nodes below until positions are no longer available
        for (int i = neighbourNode[0] - 1; i >= 0; i--){
            if (!visited[i][neighbourNode[1]]){
                if (model.getTile(i,neighbourNode[1])== BoardModel.POS_AVAILABLE){
                   visited[i][neighbourNode[1]] = true; 
                   int[] initCurrentSpot = new int[3];
                   initCurrentSpot[0] = i; 
                   initCurrentSpot[1] = neighbourNode[1]; 
                   initCurrentSpot[2] = neighbourNode[2] + 1; 
                    moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                   nodesToCheck.add(initCurrentSpot);
                }
                
                  else { 
                    break; 
                    }
            }
           
        }
        // search nodes to the right until positions are no longer available
        for (int i = neighbourNode[1] + 1; i < 10; i++){
            if(!visited[neighbourNode[0]][i]){
                if (model.getTile(neighbourNode[0],i) == BoardModel.POS_AVAILABLE){
                    visited[neighbourNode[0]][i] = true; 
                    int[] initCurrentSpot = new int[3]; 
                    initCurrentSpot[0] = neighbourNode[0]; 
                    initCurrentSpot[1] = i; 
                    initCurrentSpot[2] = neighbourNode[2] + 1; 
                     moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                    nodesToCheck.add(initCurrentSpot); 
                }
                else {
                    break;
                }
        }
        }
        
        // search nodes to the left until position are no longer available; 
        for (int i = neighbourNode[1] -1; i >= 0; i--){
            if (!visited[neighbourNode[0]][i]){
                if (model.getTile(neighbourNode[0],i) == BoardModel.POS_AVAILABLE){
                    visited[neighbourNode[0]][i] = true; 
                    int[] initCurrentSpot = new int[3]; 
                    initCurrentSpot[0] = neighbourNode[0]; 
                    initCurrentSpot[1] = i; 
                    initCurrentSpot[2] = neighbourNode[2] + 1; 
                     moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                    nodesToCheck.add(initCurrentSpot);
                }
                else{
                    break; 
                }
            }
        }
        // search nodes diagonally up and right until positions are no longer available;
        // up == decreasing in row, right == increasing in column 
        for (int i = 1; neighbourNode[0] - i >=0 && neighbourNode[1] + i < 10; i++){
            if (!visited[neighbourNode[0] - i][neighbourNode[1] + i]){
                if (model.getTile(neighbourNode[0]- i, neighbourNode[1] + i) == BoardModel.POS_AVAILABLE){
                    visited[neighbourNode[0] - i ][neighbourNode[1] + i] = true; 
                    int[] initCurrentSpot = new int[3]; 
                    initCurrentSpot[0] = neighbourNode[0] - i; 
                    initCurrentSpot[1] = neighbourNode[1] + i; 
                    initCurrentSpot[2] = neighbourNode[2] + 1; 
                     moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                    nodesToCheck.add(initCurrentSpot); 
                }
                else{
                    break;
                }
            }
        }
        
        //search nodes diagonally down and left until positions are no longer available 
        for (int i = 1; neighbourNode[0] + i < 10 && neighbourNode[1] -i >=0; i++){
            if (!visited[neighbourNode[0] + i][neighbourNode[1] - i]){
                if (model.getTile(neighbourNode[0] + i, neighbourNode[1] - i) == BoardModel.POS_AVAILABLE){
                    visited[neighbourNode[0]+i][neighbourNode[1] - i] = true;
                    int[] initCurrentSpot = new int[3];
                    initCurrentSpot[0] = neighbourNode[0] + i; 
                    initCurrentSpot[1] = neighbourNode[1] - i; 
                    initCurrentSpot[2] = neighbourNode[2] +1; 
                    moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                    nodesToCheck.add(initCurrentSpot);
                }
                else {
                    break; 
                }
            }
        }
        
        // search nodes diagonally up and left until positions are no longer available 
        for (int i = 1; neighbourNode[0] - i >= 0 && neighbourNode[1] - i >= 0; i++){
            if (!visited[neighbourNode[0] - i][neighbourNode[1] - i]){
                if (model.getTile(neighbourNode[0] - i, neighbourNode[1] - i) == BoardModel.POS_AVAILABLE){
                    visited[neighbourNode[0]-i][neighbourNode[1] - i] = true;
                    int[] initCurrentSpot = new int[3];
                    initCurrentSpot[0] = neighbourNode[0] - i; 
                    initCurrentSpot[1] = neighbourNode[1] - i; 
                    initCurrentSpot[2] = neighbourNode[2] +1; 
                    moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                    nodesToCheck.add(initCurrentSpot);
                }
                else {
                    break; 
                }
            }
        }
        
        // search nodes diagonally down and right until positions are no longer available
        for (int i = 1; neighbourNode[0] +i < 10 && neighbourNode[1] + i < 10; i++){
            if (!visited[neighbourNode[0] + i][neighbourNode[1] + i]){
                if (model.getTile(neighbourNode[0] + i, neighbourNode[1] + i) == BoardModel.POS_AVAILABLE){
                    visited[neighbourNode[0]+i][neighbourNode[1] + i] = true;
                    int[] initCurrentSpot = new int[3];
                    initCurrentSpot[0] = neighbourNode[0] + i; 
                    initCurrentSpot[1] = neighbourNode[1] + i; 
                    initCurrentSpot[2] = neighbourNode[2] +1; 
                    moveLevel[initCurrentSpot[0]][initCurrentSpot[1]] = initCurrentSpot[2];
                    nodesToCheck.add(initCurrentSpot);
                }
                else {
                    break; 
                }
            }
        }
        
        }
        return moveLevel; 
    }

}


