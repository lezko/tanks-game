package com.lezko.tanks.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class LobbyItem extends JPanel {

    public LobbyItem(UUID id, Runnable callback) {
        JLabel idLabel = new JLabel(id.toString());
        idLabel.setForeground(Color.white);
        add(idLabel);

        MyButton enterBtn = new MyButton("JOIN", 10);
        enterBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callback.run();
            }
        });
        add(enterBtn);

        setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        setBackground(new Color(0, 0, 0, 0));
    }
}
