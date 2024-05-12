package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import BrickBreaker.BrickBreakerGameFrame;
import FlappyBird.FlappyBird;
import Pong.PongGameFrame;
import Snake.SnakeGameFrame;

public class Main {

    public static SnakeGameFrame snakeGameFrame;
    public static FlappyBird flappyBird;
    public static PongGameFrame pongGameFrame;
    public static BrickBreakerGameFrame brickBreakerGameFrame;

    public static JFrame obJFrame;
    public static JPanel gameChooserFrame;

    public static void main(String[] args) {

        showScreen();
    }

    public static void showScreen() {
        obJFrame = new JFrame("The Arcade");
        obJFrame.setBounds(10, 10, 700, 600);
        obJFrame.setResizable(false);
        obJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameChooserFrame = new JPanel(new GridLayout(3, 1));
        gameChooserFrame.setBackground(Color.black);

        JLabel titLabel = createLabel("THE ARCADE", 25, Color.white);
        JLabel chooseGame = createLabel("PICK YOUR POISON", 14, Color.white);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.setBackground(Color.black);
        labelPanel.add(titLabel);
        labelPanel.add(chooseGame);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.black);
        buttonPanel.add(createGameButton("SLITHER.IO", Color.RED, Color.white, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeGameFrame = new SnakeGameFrame();
                switchToGame(snakeGameFrame, 720, 720);
            }
        }));
        buttonPanel.add(createGameButton("FLAPPY BIRD", Color.GREEN, Color.black, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flappyBird = new FlappyBird();
                switchToGame(flappyBird, 800, 800);
            }
        }));
        buttonPanel.add(createGameButton("BRICK BREAKER", Color.BLUE, Color.white, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brickBreakerGameFrame = new BrickBreakerGameFrame();
                switchToGame(brickBreakerGameFrame);
            }
        }));
        buttonPanel.add(createGameButton("PING PONG", Color.ORANGE, Color.black, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pongGameFrame = new PongGameFrame();
                gameChooserFrame.setVisible(false);
                obJFrame.setSize(1000, 555);
                obJFrame.add(pongGameFrame);
                pongGameFrame.requestFocus();
            }
        }));

        gameChooserFrame.add(labelPanel);
        gameChooserFrame.add(buttonPanel);

        obJFrame.add(gameChooserFrame);
        obJFrame.setVisible(true);
    }

    private static JLabel createLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, fontSize));
        label.setForeground(color);
        return label;
    }

    private static JButton createGameButton(String text, Color backgroundColor, Color foregroundColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 14));
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.addActionListener(actionListener);
        return button;
    }

    private static void switchToGame(JPanel gamePanel) {
        gameChooserFrame.setVisible(false);
        obJFrame.add(gamePanel);
        gamePanel.requestFocus();
    }

    private static void switchToGame(JPanel gamePanel, int width, int height) {
        gameChooserFrame.setVisible(false);
        obJFrame.setSize(width, height);
        obJFrame.add(gamePanel);
        gamePanel.requestFocus();
    }
}