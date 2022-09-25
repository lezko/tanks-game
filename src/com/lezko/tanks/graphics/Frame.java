package com.lezko.tanks.graphics;

import com.lezko.tanks.controller.TankController;
import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.game.Tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Frame extends JFrame {

    private GameContainer gameContainer;
    private final int WIDTH = 1000;
    private final int HEIGHT = 800;

    public Frame() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        gameContainer = new GameContainer(this);

        add(gameContainer);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
