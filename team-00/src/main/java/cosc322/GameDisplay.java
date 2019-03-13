package cosc322;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * @author julian
 * This class defines how the state of the game is displayed.
 */
public class GameDisplay extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private int boardSize;
    
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
        this.boardSize = gameModel.getSize();
        
        repaint();
    }
    
    // this is a mysterious jframe method
    protected void paintComponent(Graphics gg){
        Graphics g = (Graphics2D) gg;

        for(int i = 0; i < boardSize + 1; i++){
            g.drawLine(i * cellDim + offset, offset, i * cellDim + offset, boardSize * cellDim + offset);
            g.drawLine(offset, i*cellDim + offset, boardSize * cellDim + offset, i*cellDim + offset);					 
        }

        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){

                posX = c * cellDim + offset;
                posY = (9 - r) * cellDim + offset;

                if(gameModel.getTile(r, c).equalsIgnoreCase(BoardModel.POS_AVAILABLE)){
                        g.clearRect(posX, posY, 49, 49);					
                }

                if(gameModel.getTile(r, c).equalsIgnoreCase(BoardModel.POS_MARKED_BLACK)){
                        g.fillOval(posX, posY, 50, 50);
                } 
                else if(gameModel.getTile(r, c).equalsIgnoreCase(BoardModel.POS_MARKED_ARROW)) {
                        g.clearRect(posX + 1, posY + 1, 49, 49);
                        g.drawLine(posX, posY, posX + 50, posY + 50);
                        g.drawLine(posX, posY + 50, posX + 50, posY);
                }
                else if(gameModel.getTile(r, c).equalsIgnoreCase(BoardModel.POS_MARKED_WHITE)){
                        g.drawOval(posX, posY, 50, 50);
                }
            }
        }
    }
    
    // another mysterious jframe method!
    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }
}
