
package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.Box;
import javax.swing.JFrame;

/**
 *
 * @author julian
 */
public class CustomGame {
    private JFrame guiFrame;
    private BoardModel model;
    private GameDisplay display;
    
    public static void main(String[] args) {
        CustomGame game = new CustomGame();
    }
    
    public CustomGame() {
        this.model = new BoardModel(10);
        this.display = new GameDisplay(this.model);
        setupGUI();
        
//        makeMove(9, 6, 8, 5, 8, 3, "test");
    }
    
    
    public void makeMove(int[] oldQueenPosition,
                         int[] newQueenPosition,
                         int[] arrowPosition,
                         String playerName) {
        String moveMessage = model.makeMove(oldQueenPosition,
                                            newQueenPosition,
                                            arrowPosition);
        if (moveMessage.equalsIgnoreCase(model.VALID)) {
            display.repaint();
            System.out.println(playerName + " moved:\n"
                            + "Queen at [" + oldQueenPosition[0] + "," + oldQueenPosition[1] + "] to"
                            + "[" + newQueenPosition[0] + "," + newQueenPosition[1] + "].\n"
                            + "Arrow fired to [" + arrowPosition[0] + "," + arrowPosition[1] + "].");
        }
        else {
            System.out.println(moveMessage);
        }
    }
    
    private void setupGUI(){
        guiFrame = new JFrame();

        guiFrame.setSize(800, 600);
        guiFrame.setTitle("Game of the Amazons - Team 01's Test!");	

        guiFrame.setLocation(200, 200);
        guiFrame.setVisible(true);
        guiFrame.repaint();
        guiFrame.setLayout(null);

        Container contentPane = guiFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(Box.createVerticalGlue());
	
        contentPane.add(display, BorderLayout.CENTER);
    }
}
