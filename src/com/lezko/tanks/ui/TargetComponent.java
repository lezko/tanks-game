package com.lezko.tanks.ui;

import com.lezko.tanks.game.Target;

import java.awt.*;

public class TargetComponent extends GameObjectComponent {

    public TargetComponent(int size) {
        super(size);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getComponentSize(), getComponentSize());
    }
}
