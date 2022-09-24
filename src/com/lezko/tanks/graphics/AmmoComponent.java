package com.lezko.tanks.graphics;

import com.lezko.tanks.game.Ammo;

import java.awt.*;

public class AmmoComponent extends GameObjectComponent {

    public AmmoComponent(Ammo ammo) {
        super(ammo);
        setPreferredSize(new Dimension(ammo.getSize(), ammo.getSize()));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(0, 0, getComponentSize(), getComponentSize());
    }
}
