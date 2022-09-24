package com.lezko.tanks.graphics;

import com.lezko.tanks.game.GameObject;

import javax.swing.*;
import java.awt.*;

public class GameObjectComponent extends JComponent {

    private int x;
    private int y;

    private final GameObject object;

    public GameObjectComponent(GameObject object) {
        this.object = object;
        setPreferredSize(new Dimension(object.getSize(), object.getSize()));
    }

    public void update() {
        x = (int) object.getX();
        y = (int) object.getY();

        setBounds(x, y, getComponentSize(), getComponentSize());
    }

    public GameObject getObject() {
        return object;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getComponentSize() {
        return object.getSize();
    }
}
