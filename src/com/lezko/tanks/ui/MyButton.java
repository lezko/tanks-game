package com.lezko.tanks.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class MyButton extends JButton {

    public MyButton(String label, int fontSize) {
        super(label.toUpperCase());
        setFocusable(false);
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.CYAN);

        Map<TextAttribute, Object> attributes = new HashMap();
        attributes.put(TextAttribute.TRACKING, 0.5);

        setFont((new Font("Roboto", Font.BOLD, fontSize)).deriveFont(attributes));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.CYAN, 3),
                BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 20)
        ));
    }

    public MyButton(String label) {
        this(label, 20);
    }
}
