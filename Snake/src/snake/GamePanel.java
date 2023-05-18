/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

/**
 *
 * @author shahmeerqureshi98
 */
public class GamePanel extends JPanel implements ActionListener {

    // dimensions of the window
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    
    // object size
    static final int UNIT_SIZE = 25;
    
    // defines number of objects that can fit in the screen
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
    
    // delay for timer - the higher the number, the slower the game and vice versa
    static final int DELAY = 75;
    
    // arrays to define size of snake: x and y coordinates
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    
    // number of body parts at start of game 
    int bodyParts = 6;
    
    int applesEaten; // number of apples eaten
    int appleX; // x coordinates of apple
    int appleY; // y coordinates of apple
    
    // initial direction of snake at start of game
    char direction = 'R';
    
    // initial state of game is not running
    boolean running = false;
    
    // declares a timer variable
    Timer timer;
    
    // declares a random variable
    Random random;
    
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // sets the preferred size of component
        this.setBackground(Color.black); // background color
        this.setFocusable(true); // used to set focusability of the component, can receive keyboard inputs and respond to key events when it is interacted
        this.addKeyListener(new MyKeyAdapter()); // adds a key listener to the current component, so it can listen for keyboard events
        startGame(); // when game panel is constructed, start the game
    }
    
    public void startGame() {
        newApple(); // creates a new apple when game is started
        running = true; // indicates the game is currently running 
        timer = new Timer(DELAY, this); // a new javax.swing.Timer object is created with a DELAY milliseconds between each tick, 'this' is registered as a listener to receive timer events
        timer.start(); // timer is started, begins the game loop and causes the timer to generate events
    }
    
    public void paintComponent(Graphics g) { // Graphics is a parameter used to perform various drawing operations
        /* overridden method called automatically when the panel needs to be repainted, 
        first calls the paintComponent method of the superclass to ensure proper painting, 
        then invokes the 'draw' method to perform custom drawing operations using the Graphics object */
        super.paintComponent(g); 
        draw(g);
    }
    
    public void draw(Graphics g) {
        
        if(running) {
        // creates grid lines
            /*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        } */
        // sets color and shape of apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        
        // body parts of snake
            for(int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
                else {
                    g.setColor(new Color(45, 180, 0));
                    // g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize()); 
        }
        else {
            gameOver(g);
        }
    }
    
    public void newApple() { // generates coordinates of new apples
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; 
    }
    
    public void move() {
        for(int i = bodyParts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    
    public void checkApple() { // checks if apples are eaten
        // checks if snake's head touches x and y coordinates of apple
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    
    public void checkCollisions() { // checks if snake's head collides with its own body or with game borders
        // checks if head collides with body, if x and y coordinates of head are the same as x and y coordinates of body, the game should stop
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // checks if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // checks if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // checks if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        // checks if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }   
    }
    
    public void gameOver(Graphics g) { // used to display a message 'game over'
        // Display the Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize()); 
        
        // Game Over Message
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2); // used to center text
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter { // KeyAdapter is used to define custom logic to handle keyword events in the application
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                break;
            }
        }
    }
    
    
}
