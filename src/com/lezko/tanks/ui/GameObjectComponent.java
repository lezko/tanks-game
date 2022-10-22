package com.lezko.tanks.ui;

import com.lezko.tanks.game.GameObject;

import javax.swing.*;
import java.awt.*;

public class GameObjectComponent extends JComponent {

    private int x, y, angle;
    private final int size;

    public GameObjectComponent(int size) {
        this.size = size;
    }
    public void update(GameObjectUpdateData data) {
        x = (int) data.getX();
        y = (int) data.getY();
        angle = data.getAngle();

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
