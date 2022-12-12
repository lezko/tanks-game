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
import java.util.List;
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

    private final MyButton startButton = new MyButton("Start game");
    private final MyButton pauseButton = new MyButton("Pause game");
    private final MyButton resumeButton = new MyButton("Resume game");
    private final MyButton restartButton = new MyButton("Restart game");
    private final MyButton exitLobbyButton = new MyButton("Exit lobby");

    private final MyButton createLobbyBtn = new MyButton("Create lobby");

    private JPanel lobbyContainer;

    public GameContainer(JFrame frame) throws IOException {
        this.frame = frame;

        try {
            backgroundImage = ImageIO.read(new File("img/space-background.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initField();
        client = new GameClient("localhost", 9999, dataList -> {
            field.update(dataList);
        });

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

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initLobbyContainer();

        add(new Box.Filler(new Dimension(0, 0), new Dimension(0, Short.MAX_VALUE), new Dimension(0, Short.MAX_VALUE)));

        createLobbyBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(createLobbyBtn);
        showLobbyList();
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
        startGame();
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

    private void startGame() {
        client.startListening();
        field.setVisible(true);
        initGameTimer();
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

    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
