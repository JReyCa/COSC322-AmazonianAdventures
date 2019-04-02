/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.util.Random;

public class Bot {
    // heuristic types (or 'foreign' when representing another machine)
    public static final String DUMB = "dumb";
    public static final String FOREIGN = "foreign";
    public static final String MINMAX = "minmax";
    
    private boolean isWhite;
    private boolean isOnline;
    
    private String botName = "nameless!";
    private String botType = "untyped!";
    
    private BoardModel model;
    
    public String getBotName() {
        return botName;
    }
    
    public String getBotType() {
        return botType;
    }
    
    public void setColour(boolean colour) {
        isWhite = colour;
        nameBot(colour, botType, "Net Bot");
    }
    
    //**** INSTANTIATION ****
    // constructor
    private Bot(boolean isWhite, String baseName, String botType, BoardModel model, boolean isOnline) {
        this.isWhite = isWhite;
        this.botType = botType;
        this.model = model;
        this.isOnline = isOnline;
        this.botName = nameBot(isWhite, botType, baseName);
    }
    
    // a bot for testing locally
    public static Bot testBot(boolean isWhite, String botType, BoardModel model) {
        if (botType.equalsIgnoreCase(FOREIGN)) {
            System.out.println("Don't create a test bot as 'foreign'");
            System.exit(0);
        }
        
        return new Bot(isWhite, "Test Bot", botType, model, false);
    }
    
    // a bot for competing through the server in the tournament
    public static Bot netBot(boolean isWhite, String botType, BoardModel model) {
        if (botType.equalsIgnoreCase(FOREIGN)) {
            System.out.println("Don't create a net bot as 'foreign'");
            System.exit(0);
        }
        
        return new Bot(isWhite, "Valiant Exemplar", botType, model, true);
    }
    
    // a bot that represents the actions of the enemy player on the server
    public static Bot foreignBot(boolean isWhite, BoardModel model) {
        return new Bot(isWhite, "Dark Nemisis", FOREIGN, model, false);
    }
    
    private String nameBot(boolean isWhite, String type, String baseName) {
        String colourName = " (" + (isWhite ? "white" : "black") + ")";
        String typeName = " - " + type;
        return baseName + typeName + colourName;
    }
    // ********
    
    // try to make the specified move and print out the result
    public void makeMove(Move move) {
        if(move.getArrowPosition()[0]<0){
            System.out.println("I lost!");
            System.exit(0);
        }
        model.makeMove(move);
    }
    
    // figure out what move to make based on what kind of AI we are
    public Move pickMove() {
        switch(botType) {
            case Bot.DUMB:
                return pickDumbMove();
            case Bot.MINMAX:
                return minMax.pickMinMaxMove(model, isWhite);
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
            if (failCounter >= 5000) {
                System.out.println("The dumb bot was too dumb to find a valid move with a gazillion tries!");
                System.exit(0);
            }
            
            failCounter++;
        }
        while (!model.validateMove(move).equalsIgnoreCase(model.VALID));
        
        return move;
    }
}
