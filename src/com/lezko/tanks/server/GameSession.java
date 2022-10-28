package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.net.UDPSender;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameSession implements Runnable {

    private final int CLIENT_LIMIT = 5;

    private final UUID id = UUID.randomUUID();
    private Game game;
    private final Map<UUID, ClientHandler> clients = new HashMap<>();

    public void addClient(InetAddress address, int port) throws IOException {
        if (clients.size() == CLIENT_LIMIT) {
            new UDPSender(address, port).send("Players limit exceed");
            return;
        }

        ClientHandler handler = new ClientHandler(game, address, port);
        clients.put(handler.getId(), handler);
    }

    public void updatePlayer(UUID clientId, String data) {
        clients.get(clientId).updateTank(data);
    }

    @Override
    public void run() {
        game = new Game(800, 600);
        game.start();
        game.setCallback(() -> {
            for (ClientHandler client : clients.values()) {
                try {
                    client.send(stringifyData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // todo optimize data collection
    private String stringifyData() {
        StringBuilder s = new StringBuilder();
        for (GameObject o : game.getObjects()) {
            s.append(GameObjectUpdateData.stringify(GameObjectUpdateData.fromGameObject(o))).append(" ");
        }

        return s.toString();
    }

    public UUID getId() {
        return id;
    }
}
