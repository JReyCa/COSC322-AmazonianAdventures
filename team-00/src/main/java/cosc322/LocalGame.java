/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.util.Map;

public class LocalGame extends Game {
    
    public LocalGame(String heuristic1, String heuristic2) {
        super("Local Game");
        
        Bot bot1 = Bot.testBot(true, heuristic1, super.getModel());
        Bot bot2 = Bot.testBot(false, heuristic2, super.getModel());
        
        super.initialize(bot1, bot2, true);
    }
    
    @Override
    public void onLogin() {}
    
    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> messageDetails) { return true; }
    
    @Override
    public String userName() { return null; }
}
