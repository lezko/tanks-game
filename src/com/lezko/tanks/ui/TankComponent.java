package com.lezko.tanks.ui;

import com.lezko.tanks.game.Tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TankComponent extends GameObjectComponent {

    private Image backgroundImage;

    public TankComponent(int size) {
        super(size);

        try {
            backgroundImage = ImageIO.read(new File("img/tank-red.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(-getAngle() + 90), getComponentSize() / 2.0, getComponentSize() / 2.0);
        g2d.drawImage(backgroundImage, 0, 0, getComponentSize(), getComponentSize(), this);
    }
}
