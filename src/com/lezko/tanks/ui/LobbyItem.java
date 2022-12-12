package com.lezko.tanks.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class LobbyItem extends JPanel {

    private final UUID sessionId;

    public LobbyItem(UUID id, int playersCount, Runnable callback) {
        sessionId = id;

        setLayout(new BorderLayout());

        JLabel idLabel = new JLabel(id.toString() + ", " + "Players: " + playersCount);
        idLabel.setForeground(Color.white);
        add(idLabel, BorderLayout.LINE_START);

        MyButton enterBtn = new MyButton("JOIN", 10);
        enterBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callback.run();
            }
        });
        add(enterBtn, BorderLayout.LINE_END);

        setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        setBackground(new Color(0, 0, 0, 0));
    }
}
