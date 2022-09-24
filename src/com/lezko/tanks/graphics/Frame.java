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

    private final Game game;

    public Frame() {

        game = new Game(800, 600);

        Field field = new Field(game.getWidth(), game.getHeight());

        for (GameObject object : game.getObjects()) {
            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                TankController.initKeyboardController(this, tank);
            }
        }

        game.setCallback(() -> field.update(game.getObjects()));

        field.setOpaque(false);
        field.setBackground(new Color(255, 0, 0, 20));
        add(field);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
