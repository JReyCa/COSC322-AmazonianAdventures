/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.util.ArrayList;
import java.util.Map;
import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
import ygraphs.ai.smart_fox.games.GameClient;

public class OnlineGame extends Game {
    
    private GameClient client;
    private int roomNumber;
    private String username;
    
    private String heuristic;
    
    public OnlineGame(String heuristic, String username, String password, int roomNumber) {
        super("Showdown");
        
        client = new GameClient(username, password, this);
        this.username = username;
        this.roomNumber = roomNumber;
        
        this.heuristic = heuristic;
    }
    
    // called when the client object is set
    @Override
    public void onLogin() {
        if (roomNumber < 0 || roomNumber > 17) {
            System.out.println("We logged in without setting a valid room to join!");
            System.exit(0);
        }
        
        client.joinRoom(client.getRoomList().get(roomNumber));
        System.out.println("Successfully joined room " + roomNumber);
    }
    
    // called when a message is received by the client
    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> messageDetails) {
        if(messageType.equals(GameMessage.GAME_ACTION_START)){	 
	    boolean weAreWhite = ((String) messageDetails.get("player-white")).equals(this.userName());
            System.out.println("We're playing as the " + (weAreWhite ? "white" : "black") + " player");
            
            Bot bot1 = Bot.netBot(weAreWhite, heuristic, super.getModel());
            Bot bot2 = Bot.foreignBot(!weAreWhite, super.getModel());
            
            super.initialize(bot1, bot2, !weAreWhite);
	}
        else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
            if (getCurrentBot() == getBot2()) {
                handleOpponentMove(messageDetails);
            }
	}
        
        return true;
    }
    
    @Override
    public void startTurn() {
        if (isBot1sTurn()) {
            LocalTask next = new LocalTask(retrieveMove());
            next.run();
        } else {
            
        }
    }
    
    @Override
    public void runTurn(Move move) {
        if (getCurrentBot() == getBot1()) {
            int[] oldWithOffset = new int[] { move.getOldQueenPosition()[0] + 1, move.getOldQueenPosition()[1] + 1 };
            int[] newWithOffset = new int[] { move.getNewQueenPosition()[0] + 1, move.getNewQueenPosition()[1] + 1 };
            int[] arrowWithOffset = new int[] { move.getArrowPosition()[0] + 1, move.getArrowPosition()[1] + 1 };
            
            client.sendMoveMessage(oldWithOffset, newWithOffset, arrowWithOffset);
        }
        
        super.runTurn(move);
    }
    
    //handle the event that the opponent makes a move. 
    private void handleOpponentMove(Map<String, Object> msgDetails){
	int[] oldQueenPosition = convertDetail((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
	int[] newQueenPosition = convertDetail((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT));
	int[] arrowPosition = convertDetail((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS));
        
        int[] oldWithOffset = new int[] { oldQueenPosition[0] - 1, oldQueenPosition[1] - 1 };
        int[] newWithOffset = new int[] { newQueenPosition[0] - 1, newQueenPosition[1] - 1 };
        int[] arrowWithOffset = new int[] { arrowPosition[0] - 1, arrowPosition[1] - 1 };
        
        System.out.println(msgDetails);
        LocalTask next = new LocalTask(new Move(oldWithOffset, newWithOffset, arrowWithOffset));
        next.run();
    }
    
    private int[] convertDetail(ArrayList<Integer> detail) {
        return new int[] { detail.get(0), detail.get(1) };
    }
    
    @Override
    public String userName() {
        return username;
    }
    
    
}
