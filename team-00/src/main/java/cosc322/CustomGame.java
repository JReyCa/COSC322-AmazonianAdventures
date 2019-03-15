
package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Date;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.JFrame;

public class CustomGame {
    private static final long MIN_TURN_LENGTH = 6000;
    
    private JFrame guiFrame;        // the window that displays the game
    private BoardModel model;       // a wrapper for a 2D string array defining the board
    private GameDisplay display;    // this paints things on the JFrame
    
    private Bot whiteBot;   // represents the player controlling the white queens
    private Bot blackBot;   // represents the player controlling the black queens
    
    private String gameName = "unnamed";    // this is displayed on the header of the game window
    private boolean isWhiteTurn = true;     // is this the white player's turn, or the black player's turn?
    private int turnCounter = 0;
    private Bot currentBot;                 // the player whose turn it currently is
    
    // **** MAIN METHOD! ****
    public static void main(String[] args) {
        CustomGame game = new CustomGame("Amazons - Testing Base Bot!");
    }
    
    // constructor
    public CustomGame(String gameName) {
        this.model = new BoardModel();
        this.display = new GameDisplay(this.model);
        this.gameName = gameName;
        this.whiteBot = new Bot(true, "White Bot", Bot.DUMB, this.model);
        this.blackBot = new Bot(false, "Black Bot", Bot.DUMB, this.model);
        
        initialize();
        
        TurnTask testTask = new TurnTask();
        testTask.run();
    }
    
    // do initial set up
    private void initialize() {
        isWhiteTurn = true;
        currentBot = whiteBot;
        
        setupGUI();
    }
    
    private void runTurn() {
        currentBot.makeMove();
        display.repaint();
        nextTurn();
    }
    
    // switch to the other player
    private void nextTurn() {
        isWhiteTurn = !isWhiteTurn;
        currentBot = isWhiteTurn ? whiteBot : blackBot;
    }
    
    // create the window and paint the game display on it
    private void setupGUI(){
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
    
    public class TurnTask extends TimerTask {
    
        @Override
        public void run() {
            
            // figure out when the AI started its turn
            Date startDate = new Date();
            long startTime = startDate.getTime();
            
            // run the actual turn
            runTurn();
            turnCounter++;
            
            // figure out when the AI was finished its turn and delay if it was crazy fast
            Date endDate = new Date();
            long endTime = endDate.getTime();
            
            long turnTime = endTime - startTime;
            if (turnTime < MIN_TURN_LENGTH) {
                try {
                    Thread.sleep(MIN_TURN_LENGTH - turnTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // initiate the next turn
            TurnTask next = new TurnTask();
            next.run();
        }
    }
}
