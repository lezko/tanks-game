package com.lezko.tanks.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameUI extends JFrame {

    private GameContainer gameContainer;
    private final int WIDTH = 1000;
    private final int HEIGHT = 800;

    public GameUI() throws IOException {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        gameContainer = new GameContainer(this);

        add(gameContainer);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new GameUI();
    }
}
