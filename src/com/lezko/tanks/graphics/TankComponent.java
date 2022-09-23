package com.lezko.tanks.graphics;

import com.lezko.tanks.models.Tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TankComponent extends JComponent {

    private Tank tank;
    private Image backgroundImage;

    public TankComponent(Tank tank) {
        this.tank = tank;
        setPreferredSize(new Dimension(tank.getSize(), tank.getSize()));
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        try {
            backgroundImage = ImageIO.read(new File("img/tank-red.png"));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public Tank getTank() {
        return tank;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(-tank.getAngle() + 90), tank.getSize() / 2.0, tank.getSize() / 2.0);
        g2d.drawImage(backgroundImage, 0, 0, tank.getSize(), tank.getSize(), this);
    }

    public int getX() {
        return tank.getX();
    }

    public int getY() {
        return tank.getY();
    }

    public int getComponentSize() {
        return tank.getSize();
    }
}
