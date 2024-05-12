package Pong;

import javax.swing.*;
import java.awt.*;
import Main.Main;

public class PongGameFrame extends JFrame {

    GamePanel panel;

    public PongGameFrame(){
        Main.obJFrame.dispose();
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}