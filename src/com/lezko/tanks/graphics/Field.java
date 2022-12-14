package com.lezko.tanks.graphics;

import com.lezko.tanks.game.Ammo;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.game.Tank;
import com.lezko.tanks.game.Target;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Field extends JPanel {

    private final Map<GameObject, GameObjectComponent> gameObjectComponents = new HashMap<>();

    private final int width;
    private final int height;

    private JLabel scoreLabel = new JLabel();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        setLayout(null);
        setPreferredSize(new Dimension(width, height));
    }

    public void reset() {
        removeAll();
        gameObjectComponents.clear();
    }

    public void update(List<GameObject> gameObjects) {
        for (GameObject object : gameObjects) {
            if (!gameObjectComponents.containsKey(object)) {
                GameObjectComponent component = null;
                if (object instanceof Ammo) {
                    component = new AmmoComponent((Ammo) object);
                }
                if (object instanceof Target) {
                    component = new TargetComponent((Target) object);
                }
                if (object instanceof Tank) {
                    component = new TankComponent((Tank) object);
                }

                add(component);
                gameObjectComponents.put(object, component);
            }
        }

        Iterator<Map.Entry<GameObject, GameObjectComponent>> iterator = gameObjectComponents.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<GameObject, GameObjectComponent> entry = iterator.next();

            GameObject object = entry.getKey();
            GameObjectComponent component = entry.getValue();

            if (object.getState() == GameObject.State.DESTROYED) {
                
                remove(component);
                iterator.remove();
            } else {
                component.update();
            }
//            repaint(component.getX(), component.getY(), component.getComponentSize(), component.getComponentSize());
        }

        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(0, 0, width, height);
    }
}
