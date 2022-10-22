package com.lezko.tanks.ui;

import com.lezko.tanks.client.GameClient;
import com.lezko.tanks.controller.KeyboardTankController;
import com.lezko.tanks.controller.TankController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Timer;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

public class GameContainer extends JPanel {

    private Image backgroundImage;
    private Field field;
    private final JFrame frame;
    private Timer timer;

    private GameClient client;
    private final TankController controller = new KeyboardTankController(this);

    private final JPanel controlPanel = new JPanel();
    private final JPanel scorePanel = new JPanel();

    private final JLabel scoreLabel = new JLabel();
    private final JLabel timeLabel = new JLabel();

    private final MyButton startButton = new MyButton("Start game");
    private final MyButton pauseButton = new MyButton("Pause game");
    private final MyButton resumeButton = new MyButton("Resume game");
    private final MyButton restartButton = new MyButton("Restart game");

    private GridBagConstraints gbc = new GridBagConstraints();

    public GameContainer(JFrame frame) {
        this.frame = frame;

        try {
            backgroundImage = ImageIO.read(new File("img/space-background.jpeg"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        setLayout(new GridBagLayout());
        initButtonsListeners();
        initGame();
    }

    private void initButtonsListeners() {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pauseGame();
            }
        });
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                resumeGame();
            }
        });
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void updateGame() {
        updateControlPanel();
        updateScorePanel();

        revalidate();
        repaint();
    }

    private void updateControlPanel() {
        controlPanel.removeAll();
//        switch (game.getState()) {
//            case IN_PROGRESS:
//                remove(controlPanel);
//                gbc.gridy = 2;
//                add(controlPanel, gbc);
//                controlPanel.add(pauseButton);
//                break;
//            case PAUSED:
//                controlPanel.add(resumeButton);
//                break;
//            case FINISHED:
//                controlPanel.add(restartButton);
//                break;
//        }
    }

    private void initControlPanel() {
        controlPanel.add(startButton);

        controlPanel.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(controlPanel, gbc);
    }

    private void updateScorePanel() {
        scorePanel.removeAll();
//        switch (game.getState()) {
//            case IN_PROGRESS:
//                scorePanel.add(timeLabel);
//                scorePanel.add(scoreLabel);
//
//                gbc.gridy = 0;
//                add(scorePanel, gbc);
//                break;
//            case PAUSED:
//                scorePanel.add(timeLabel);
//                scorePanel.add(scoreLabel);
//                break;
//            case FINISHED:
//                scorePanel.add(scoreLabel);
//        }
    }

    private void initScorePanel() {
        scorePanel.setOpaque(false);

        timeLabel.setForeground(Color.CYAN);
        scoreLabel.setForeground(Color.CYAN);

        scorePanel.add(timeLabel);
        scorePanel.add(scoreLabel);
    }

    private void initGame() {
        initControlPanel();
        initScorePanel();
    }

    private void startGame() throws IOException {

        if (field == null) {
            field = new Field(800, 600);
        }

        client = new GameClient("localhost", 9999, dataList -> {
            field.update(dataList);
        });
        new Thread(client).start();

        field.setOpaque(false);
        gbc.gridy = 1;
        add(field, gbc);

        initGameTimer();
        updateGame();
    }

    private void initGameTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    client.sendControlsState(controller.stringifyState());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 10);
    }

    private void pauseGame() {
        timer.cancel();
        timer.purge();

//        game.pause();
        updateGame();
    }

    private void resumeGame() {
        initGameTimer();

//        game.resume();
        updateGame();
    }

    private void finishGame() {
        timer.cancel();
        timer.purge();

//        game.finish();

        field.reset();
        remove(field);

        updateGame();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
