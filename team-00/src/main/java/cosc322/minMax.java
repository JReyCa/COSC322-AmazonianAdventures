/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;
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
    
    public static String closestQueen(int row, int col, BoardModel model){
        // returns black or white to represent who owns that piece  
        for (int i = 0; i < 8; i++){
            int[][] currentQueen = queenMoves(model.queenPositions.get(i), model); 
            
        }
    
    return null; 
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


