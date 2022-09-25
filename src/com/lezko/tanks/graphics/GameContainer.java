package com.lezko.tanks.graphics;

import com.lezko.tanks.controller.TankController;
import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.game.Tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class GameContainer extends JPanel {

    private Image backgroundImage;
    private Game game;
    private final JLabel scoreLabel = new JLabel();
    private Field field;
    private final JFrame frame;

    public GameContainer(JFrame frame) {
        this.frame = frame;

        try {
            backgroundImage = ImageIO.read(new File("img/space-background.jpeg"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        JButton startButton = new JButton("Start game");
        add(startButton);
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                startGame();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    private void startGame() {
        game = new Game(800, 600);
        game.start();

        if (field == null) {
            field = new Field(game.getWidth(), game.getHeight());
        }

        add(scoreLabel);

        for (GameObject object : game.getObjects()) {
            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                TankController.initKeyboardController(frame, tank);
            }
        }

        game.setCallback(() -> {
            field.update(game.getObjects());
            scoreLabel.setText("Score: " + game.getPlayer().getScore());
        });

        field.setOpaque(false);
        add(field);
    }

    private void finishGame() {
        game.finish();

        field.reset();
        remove(field);

        remove(scoreLabel);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
