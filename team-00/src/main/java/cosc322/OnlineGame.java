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
    
    public OnlineGame(String heuristic, String username, String password, int roomNumber) {
        super("Showdown");
        
        client = new GameClient(username, password, this);
        this.username = username;
        this.roomNumber = roomNumber;
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
            
            Bot bot1 = Bot.netBot(weAreWhite, Bot.DUMB, super.getModel());
            Bot bot2 = Bot.foreignBot(weAreWhite, super.getModel());
            super.initialize(bot1, bot2, weAreWhite);
	}
        else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
	    
	}
        
        return true;
    }
    
    //handle the event that the opponent makes a move. 
    private void handleOpponentMove(Map<String, Object> msgDetails){
	ArrayList<Integer> oldQueenPosition = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
	ArrayList<Integer> newQueenPosition = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
	ArrayList<Integer> arrowPosition = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);		
    }
    
    @Override
    public String userName() {
        return username;
    }
}
