
package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.JFrame;

import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
import ygraphs.ai.smart_fox.games.GameClient;
import ygraphs.ai.smart_fox.games.GamePlayer;

public abstract class Game extends GamePlayer {
    private static final long MIN_TURN_LENGTH = 4000;
    private static final long MAX_TURN_LENGTH = 30000;
    
    // these are the essentials for displaying the game and tracking the moves
    private JFrame guiFrame;                // the window that displays the game
    private final BoardModel model;         // a wrapper for a 2D string array defining the board
    private final GameDisplay display;      // this paints things on the JFrame
    
    // these are the AI players
    private Bot bot1;
    private Bot bot2;
    
    private Bot currentBot; // the player whose turn it currently is
    
    public String baseName;
    
    // **** GETTERS & SETTERS ****
    public BoardModel getModel() { return model; }
    
    public Bot getBot1() { return bot1; }
    public Bot getBot2() { return bot2; }
    
    public boolean isBot1sTurn() { return currentBot == bot1; }
    // ************
    
    // constructor
    public Game(String gameName) {
        this.model = new BoardModel();
        this.display = new GameDisplay(this.model);
        this.baseName = gameName;
    }
    
    // **** SETUP ****
    public void initialize(Bot bot1, Bot bot2, boolean bot1Starts) {
        this.bot1 = bot1;
        this.bot2 = bot2;
        setupGUI();
        
        currentBot = bot1Starts ? bot1 : bot2;
        startTurn();
    }
    
    // create the window and paint the game display on it
    private void setupGUI(){
        String gameName = baseName + ": " + bot1.getBotName() + " vs. " + bot2.getBotName();
        
        guiFrame = new JFrame();

        guiFrame.setSize(800, 600);
        guiFrame.setTitle(gameName);	

        guiFrame.setLocation(200, 200);
        guiFrame.repaint();
        guiFrame.setLayout(null);

        Container contentPane = guiFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(Box.createVerticalGlue());
	
        contentPane.add(display, BorderLayout.CENTER);
        
        guiFrame.setVisible(true);
    }
    //************
    
    public abstract void startTurn();
    
    public void runTurn(Move move) {
        currentBot.makeMove(move);
        display.repaint();
        System.out.println(currentBot.getBotName() + ": \n" + move.toString());
        
        currentBot = isBot1sTurn() ? bot2 : bot1;
    }
    
    public Move retrieveMove() {
        return currentBot.pickMove();
    }
    
    // this class creates a coroutine
    public class LocalTask extends TimerTask {
        private Move move;
        
        public LocalTask(Move move) {
            this.move = move;
        }
    
        @Override
        public void run() {
//            try {
//                Thread.sleep(MIN_TURN_LENGTH);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            
            // figure out when the AI started its turn
            Date startDate = new Date();
            long startTime = startDate.getTime();
            
            runTurn(move);
            
            // figure out when the AI was finished its turn and delay if it was crazy fast
            Date endDate = new Date();
            long endTime = endDate.getTime();
            
            long turnTime = endTime - startTime;
//            if (turnTime < MIN_TURN_LENGTH) {
//                try {
//                    Thread.sleep(MIN_TURN_LENGTH - turnTime);
//                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            
            startTurn();
        }
    }
}
