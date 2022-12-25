package com.lezko.tanks.ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Field extends JPanel {

    private final Map<UUID, GameObjectComponent> gameObjectComponents = new HashMap<>();

    private final int width;
    private final int height;

    private JLabel scoreLabel = new JLabel();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
    }

    public void reset() {
        removeAll();
        gameObjectComponents.clear();
    }

    public void update(List<GameObjectEvent> dataList) {
        if (dataList == null) {
            return;
        }

        Set<UUID> ids = new HashSet<>();
        for (GameObjectEvent data : dataList) {
            UUID id = data.id;
            ids.add(id);

            if (gameObjectComponents.containsKey(id)) {
                GameObjectComponent component = gameObjectComponents.get(id);
                component.update(data);
            } else {
                GameObjectComponent component = null;

                switch (data.type) {
                    case AMMO:
                        component = new AmmoComponent(data.size);
                        break;
                    case TANK:
                        component = new TankComponent(data.size);
                        break;
                    case TARGET:
                        component = new TargetComponent(data.size);
                        break;
                }

                add(component);
                gameObjectComponents.put(id, component);
            }
        }

        List<UUID> removeList = new ArrayList<>();
        for (UUID id : gameObjectComponents.keySet()) {
            if (!ids.contains(id)) {
                removeList.add(id);
            }
        }
        for (UUID id : removeList) {
            remove(gameObjectComponents.get(id));
            gameObjectComponents.remove(id);
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
