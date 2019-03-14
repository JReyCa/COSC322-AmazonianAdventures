
package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.JFrame;

public class CustomGame {
    private JFrame guiFrame;        // the window that displays the game
    private BoardModel model;       // a wrapper for a 2D string array defining the board
    private GameDisplay display;    // this paints things on the JFrame
    
    private Bot whiteBot;   // represents the player controlling the white queens
    private Bot blackBot;   // represents the player controlling the black queens
    
    private String gameName = "unnamed";    // this is displayed on the header of the game window
    private boolean isWhiteTurn = true;     // is this the white player's turn, or the black player's turn?
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
            System.out.println("task started");
            
            
            
            System.out.println("task ended");
        }
    }
}
