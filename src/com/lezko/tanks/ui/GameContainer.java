package com.lezko.tanks.ui;

import com.lezko.tanks.client.GameClient;
import com.lezko.tanks.controller.KeyboardTankController;
import com.lezko.tanks.controller.TankController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

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

    private final MyButton startBtn = new MyButton("Start game");
    private final MyButton pauseBtn = new MyButton("Pause game");
    private final MyButton resumeBtn = new MyButton("Resume game");
    private final MyButton restartBtn = new MyButton("Restart game");
    private final MyButton exitLobbyBtn = new MyButton("Exit lobby");

    private final MyButton createLobbyBtn = new MyButton("Create lobby");

    private JPanel lobbyContainer;

    public GameContainer(JFrame frame) throws IOException {
        this.frame = frame;

        try {
            backgroundImage = ImageIO.read(new File("img/space-background.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        initField();
        client = new GameClient("localhost", 9999, dataList -> {
            field.update(dataList);
        });

        initLobbyContainer();
        add(new Box.Filler(new Dimension(0, 0), new Dimension(0, Short.MAX_VALUE), new Dimension(0, Short.MAX_VALUE)));
        initButtons();

        showLobbyList();
    }

    private void initButtons() {
        createLobbyBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    client.createSession();
                    showLobbyList();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        createLobbyBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(createLobbyBtn);

        exitLobbyBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    exitSession();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        exitLobbyBtn.setAlignmentX(CENTER_ALIGNMENT);
        exitLobbyBtn.setVisible(false);
        add(exitLobbyBtn);
    }

    private void showLobbyList() throws IOException {
        lobbyContainer.removeAll();

        Map<UUID, Integer> sessions = client.getSessions();
        for (Map.Entry<UUID, Integer> entry : sessions.entrySet()) {
            lobbyContainer.add(new LobbyItem(entry.getKey(), entry.getValue(), () -> {
                try {
                    joinSession(entry.getKey());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        revalidate();
    }

    private void joinSession(UUID sessionId) throws IOException {
        lobbyContainer.setVisible(false);
        client.joinSession(sessionId);

        field.setVisible(true);
        createLobbyBtn.setVisible(false);
        exitLobbyBtn.setVisible(true);
        startGameTimer();
    }

    private void exitSession() throws IOException {
        lobbyContainer.setVisible(true);
        client.exitSession();

        field.setVisible(false);
        createLobbyBtn.setVisible(true);
        exitLobbyBtn.setVisible(false);
        stopGameTimer();
    }

    private void initLobbyContainer() {
        lobbyContainer = new JPanel();
        lobbyContainer.setOpaque(false);
        lobbyContainer.setLayout(new BoxLayout(lobbyContainer, BoxLayout.PAGE_AXIS));
        lobbyContainer.setAlignmentX(CENTER_ALIGNMENT);
        add(lobbyContainer);
    }

    private void initField() {
        field = new Field(800, 600);
        field.setOpaque(false);
        field.setVisible(false);
        add(field);
    }

    private void startGameTimer() {
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

    private void stopGameTimer() {
        timer.cancel();
        timer.purge();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
