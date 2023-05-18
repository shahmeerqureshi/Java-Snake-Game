/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javax.swing.JFrame;

/**
 *
 * @author shahmeerqureshi98
 */
public class GameFrame extends JFrame {
    
    GameFrame() {
        
        this.add(new GamePanel()); // adds a new instance of GamePanel as a child component to current JFrame 
        this.setTitle("Snake"); // sets title of the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close operation: application will terminate if frame is closed by the user 
        this.setResizable(false); // ensures user cannot resize JFrame window, size remains fixed 
        this.pack(); // automatically resizes JFrame to fit its preferred size, resized appropriately to fit its contents without unncessary empty space
        this.setVisible(true); // this ensures the JFrame window is visible to the user, allowing them to interact with the GUI components
        this.setLocationRelativeTo(null); // sets location of JFrame window relative to the screen, null means the frame will be centered on the screen
        
    }
    
}
