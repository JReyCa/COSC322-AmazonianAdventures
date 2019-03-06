/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

/**
 *
 * @author EmilyEarl
 */
public class Bot {
    
    private String playerColour = "";
    private String heuristic = "";

    public Bot(boolean white, String heuristicGiven) {
        if (white){
        this.playerColour = "white"; }
        else {
            this.playerColour = "black";
        }
        this.heuristic = heuristicGiven; 
    }
    
    public void findPossibleMoves(){
        if (heuristic.equals("minDist")){
            
        }
        
    }
    public void minDistMoves(){
        // 1. for each point, p, compare db = dist(p,Black) and dw = dist(p,white) 
        // 2. if db < dw: Black point
        // 3. else if db > dw: White point 
        // 4. else point is neutral. 
    }
    
    public void isMoveValid(){ 
        // see if position selected is a queen. 
        // see if position selected is an arrow. 
        // see if position selected is horizontal, vertical, or diagonal away. 
        // see if position selected has to go through arrow or queen to get there. 
        
        
    }
    
    public void moveQueen(){ 
        // look at all possible moves, pick the best, and move a queen there
    } 
    
    public void shootArrow(){ 
        // look at places around, select best place to shoot arrow. 
    }
    
}
