/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
import ygraphs.ai.smart_fox.games.GameClient;
import ygraphs.ai.smart_fox.games.GameModel;
import ygraphs.ai.smart_fox.games.GamePlayer;
/**
 *
 * @author EmilyEarl
 */
public class Bot extends GameClient{
    
    private String playerColour = "";
    private String heuristic = "";

    public Bot(String handle, String passwd, GamePlayer delegate,boolean white, String heuristicGiven) {
        super(handle, passwd, delegate);
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
