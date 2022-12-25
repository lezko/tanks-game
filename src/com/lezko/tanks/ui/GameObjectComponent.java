package com.lezko.tanks.ui;

import javax.swing.*;

public class GameObjectComponent extends JComponent {

    private int x, y, angle;
    private final int size;

    public GameObjectComponent(int size) {
        this.size = size;
    }
    public void update(GameObjectEvent data) {
        x = (int) data.x;
        y = (int) data.y;
        angle = data.angle;

        setBounds(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getComponentSize() {
        return size;
    }

    public int getAngle() {
        return angle;
    }
}
