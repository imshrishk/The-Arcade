package BrickBreaker;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreakerGameFrame extends JPanel implements KeyListener, ActionListener {
    private static final int X_BOUND_START = 0;
    private static final int X_BOUND_END = 683;
    private static final int Y_BOUND_START = 0;
    private static final int Y_BOUND_END = 560;
    private static final int SLIDER_Y_POSITION = 500;
    private static final int TIMER_DELAY = 8;
    private static final int INITIAL_LIVES = 3;
    private static final int INITIAL_LEVEL = 1;
    private static final int BRICK_ROWS = 3;
    private static final int BRICK_COLUMNS = 7;
    private static final int NUM_UNBREAKABLE_BRICKS = 5;

    private boolean play = false, gameOver = false;
    private int lives = INITIAL_LIVES;
    private int level = INITIAL_LEVEL;
    private int score = 0;
    private Brick[] bricks = new Brick[BRICK_ROWS * BRICK_COLUMNS];
    private int[] unbreakableBricksIndex = new int[NUM_UNBREAKABLE_BRICKS];
    private Ball ball = Ball.initBall();
    private Slider slider = new Slider(310, 0, 100, 8);
    private Timer timer;

    public BrickBreakerGameFrame() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(TIMER_DELAY, this);
        timer.start();

        Brick.initBricks(NUM_UNBREAKABLE_BRICKS, unbreakableBricksIndex, BRICK_ROWS, BRICK_COLUMNS, bricks, level);
    }

    @Override
    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(0, 0, 1000, 1000);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(X_BOUND_END, 0, 3, 592);
        g.fillRect(0, Y_BOUND_END, 692, 3);

        // The ball
        g.setColor(Color.yellow);
        g.fillOval(ball.getX() - ball.getWidth() / 2, ball.getY() - ball.getHeight() / 2, ball.getWidth(), ball.getHeight());

        // Bricks
        for (Brick brick : bricks) {
            if (!brick.broken) {
                g.setColor(getBrickColor(brick));
                g.fillRect(brick.x - Brick.width / 2, brick.y - Brick.height / 2, Brick.width, Brick.height);
            }
        }

        // The slider
        g.setColor(Color.green);
        g.fillRect(slider.getX(), SLIDER_Y_POSITION, slider.width, slider.height);

        // Text overlays
        g.setColor(Color.white);
        g.drawString("LEVEL: " + level, 50, 450);
        g.drawString("Score: " + score, 50, 470);
        g.drawString("Lives: " + lives, 600, 50);
        if (!play) g.drawString("PAUSED", 600, 70);
        if (gameOver) g.drawString("GAME OVER", 600, 90);

        g.dispose();
    }

    private Color getBrickColor(Brick brick) {
        if (brick.unbreakable) return Brick.unbreakableColor;
        switch (brick.brickHealth) {
            case 1: return Brick.healthColor1;
            case 2: return Brick.healthColor2;
            case 3: return Brick.healthColour3;
            default: return Color.black;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            ball.updatePositions();
            slider.updatePositions(X_BOUND_START, X_BOUND_END);

            ball.checkCollision(X_BOUND_START, X_BOUND_END, Y_BOUND_START, Y_BOUND_END, slider.getX());
            if (ball.outOfBounds()) {
                lives--;
                resetBall();
                play = false;
                if (lives == 0) gameOver = true;
                return;
            }
            if (ball.checkForCollisionWithBrick(bricks, level)) {
                score++;
                if (score % BRICK_COLUMNS == 0) {
                    ball.updateVelocities(1, 1);
                }
            }
            if (Brick.checkAllBricksDestroyed(bricks)) {
                level++;
                Brick.initBricks(NUM_UNBREAKABLE_BRICKS, unbreakableBricksIndex, BRICK_ROWS, BRICK_COLUMNS, bricks, level);
                resetBall();
            }
        }
        repaint();
    }

    private void resetBall() {
        ball = Ball.initBall();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    play = true;
                    slider.updateVelocity(7);
                    break;
                case KeyEvent.VK_LEFT:
                    play = true;
                    slider.updateVelocity(-7);
                    break;
                case KeyEvent.VK_ENTER:
                    play = !play;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                slider.updateVelocity(0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            Main.brickBreakerGameFrame.setVisible(false);
            Main.obJFrame.dispose();
            Main.showScreen();
        }
    }
}