/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.util.Random;

public class Bot {
    private boolean isWhite;
    
    private String botName = "nameless!";
    private String botType = "untyped!";
    
    public static final String DUMB = "dumb";
    public static final String FOEREIGN = "foreign";
    
    private BoardModel model;

    public Bot(boolean isWhite, String botName, String botType, BoardModel model) {
        this.isWhite = isWhite;
        this.botType = botType;
        this.model = model;
        this.botName = botName;
    }
    
    // try to make the specified move and print out the result
    public void makeMove() {
        Move move = pickMove();
        model.makeMove(move);
        
        System.out.println(botName + ":\n"
                        + "Queen at [" + move.getOldQueenPosition()[0] + "," + move.getOldQueenPosition()[1] + "] to "
                        + "[" + move.getNewQueenPosition()[0] + "," + move.getNewQueenPosition()[1] + "].\n"
                        + "Arrow fired to [" + move.getArrowPosition()[0] + "," + move.getArrowPosition()[1] + "].\n");
    }
    
    // figure out what move to make based on what kind of AI we are
    private Move pickMove() {
        switch(botType) {
            case Bot.DUMB:
                return pickDumbMove();
            default:
                return null;
        }
    }
    
    // if we're as dumb as a brick, pick a move at random
    private Move pickDumbMove() {
        Random random = new Random();
        Move move;
        
        // generate random moves and keep track of how much we fail
        int failCounter = 0;
        do {
            
            // find a random queen of the right colour
            int[] queenPosition;
            String chosenQueen;
            do {
                queenPosition = model.queenPositions.get(random.nextInt(model.queenPositions.size()));
                chosenQueen = model.getTile(queenPosition);
            }
            while (chosenQueen.equalsIgnoreCase(isWhite ? BoardModel.POS_MARKED_BLACK : BoardModel.POS_MARKED_WHITE));
            
            // find random locations to move the queen to and fire the arrow at
            int[] targetPosition = new int[] {random.nextInt(model.getSize()), random.nextInt(model.getSize())};
            int[] arrowPosition = new int[] {random.nextInt(model.getSize()), random.nextInt(model.getSize())};
        
            move = new Move(queenPosition, targetPosition, arrowPosition);
            
            // if we failed too much, just call it quits
            if (failCounter >= 300) {
                System.out.println("The dumb bot was too dumb to find a valid move with 100 tries!");
                System.exit(0);
            }
            
            failCounter++;
        }
        while (!model.validateMove(move).equalsIgnoreCase(model.VALID));
        
        return move;
    }
}
