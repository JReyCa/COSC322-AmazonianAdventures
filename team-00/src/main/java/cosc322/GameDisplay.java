package cosc322;

import javax.swing.JPanel;

/**
 * @author julian
 * This class defines how the state of the game is displayed.
 */
public class GameDisplay extends JPanel {
    private static final long serialVersionUID = 1L;
    
    // define the size of the display and its components
    private int width = 500;
    private int height = 500;
    private int cellDim = width / 10; 
    private int offset = width / 20;
    
    // 
    private int posX = -1;
    private int posY = -1;
    
    // set the game model that we're displaying
    private BoardModel gameModel;
    
    // constructor
    public GameDisplay(BoardModel gameModel) {
        this.gameModel = gameModel;
        
        initialize();
    }
    
    private void initialize() {
        
    }
}
