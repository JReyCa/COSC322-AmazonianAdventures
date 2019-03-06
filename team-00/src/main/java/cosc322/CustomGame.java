
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
    
    public CustomGame() {
        this.model = new BoardModel(10);
        setupGUI(this.model);
        
        this.model.makeMove(0, 5, 1, 5, 0, 3);
    }
    
    private void setupGUI(BoardModel model){
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
	
        GameDisplay display = new GameDisplay(model);
        contentPane.add(display, BorderLayout.CENTER);
    }
}
