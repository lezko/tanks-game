package com.lezko.tanks.graphics;

import com.lezko.tanks.game.Target;

import java.awt.*;

public class TargetComponent extends GameObjectComponent {

    public TargetComponent(Target target) {
        super(target);
        setPreferredSize(new Dimension(target.getSize(), target.getSize()));
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getComponentSize(), getComponentSize());
    }
}
