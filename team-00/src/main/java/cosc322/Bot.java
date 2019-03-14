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
    
    private String playerColour = "uncoloured!";
    private String botName = "nameless!";
    private String botType = "untyped!";
    
    public static String DUMB = "dumb";
    public static String FOEREIGN = "foreign";
    
    private BoardModel model;

    public Bot(boolean isWhite, String botName, String botType, BoardModel model) {
        if (isWhite){
            this.playerColour = "white"; }
        else {
            this.playerColour = "black";
        }
        
        this.botType = botType;
        this.model = model;
        this.botName = botName;
    }
    
    // 
    
    // try to make the specified move and print out the result
    private boolean makeMove(int[] oldQueenPosition, int[] newQueenPosition, int[] arrowPosition) {
        String moveMessage = model.makeMove(oldQueenPosition, newQueenPosition, arrowPosition);
        
        if (moveMessage.equalsIgnoreCase(model.VALID)) {
            System.out.println(botName + ":\n"
                            + "Queen at [" + oldQueenPosition[0] + "," + oldQueenPosition[1] + "] to "
                            + "[" + newQueenPosition[0] + "," + newQueenPosition[1] + "].\n"
                            + "Arrow fired to [" + arrowPosition[0] + "," + arrowPosition[1] + "].");
            return true;
        }
        
        System.out.println(moveMessage);
        return false;
    }
}
