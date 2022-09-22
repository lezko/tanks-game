package com.lezko.tanks.game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Frame extends JFrame {

    public Frame() {
        Field field = new Field(800, 600);
        add(field);

        JButton button = new JButton("button");
        add(button, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
