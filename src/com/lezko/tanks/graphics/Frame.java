package com.lezko.tanks.graphics;

import com.lezko.tanks.controller.TankController;
import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.models.Tank;

import javax.swing.*;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Frame extends JFrame {

    private Game game;

    public Frame() {

        game = new Game(800, 600);

        Field field = new Field(game.getWidth(), game.getHeight());

        for (GameObject object : game.getObjects()) {
            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                TankComponent tankComponent = new TankComponent(tank);
                field.addTank(tankComponent);
                TankController.initKeyboardController(this, tank);
            }
        }

        game.setCallback(field::update);

        add(field);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
