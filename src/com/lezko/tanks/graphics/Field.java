package com.lezko.tanks.graphics;

import com.lezko.tanks.models.Tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Field extends JPanel {

    private Image backgroundImage;

    private int width;
    private int height;

    private int ammoCount = 0;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        setLayout(null);
        setPreferredSize(new Dimension(width, height));

        try {
            backgroundImage = ImageIO.read(new File("img/space-background.jpeg"));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void addTank(TankComponent tankComponent) {
        add(tankComponent);
        tankComponent.setBounds(tankComponent.getX(), tankComponent.getY(), tankComponent.getComponentSize(), tankComponent.getComponentSize());
    }

    public void update() {
        for (Component component : getComponents()) {
            if (component instanceof TankComponent) {
                TankComponent tankComponent = (TankComponent) component;
                tankComponent.setBounds(tankComponent.getX(), tankComponent.getY(), tankComponent.getComponentSize(), tankComponent.getComponentSize());

                Tank tank = tankComponent.getTank();

            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0, width, height, this);
    }
}
