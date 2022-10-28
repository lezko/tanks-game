package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.IOException;
import java.util.*;

public class GameServer {

    private Game game;
    private UDPReceiver receiver;

    private final Map<UUID, GameSession> sessions = new HashMap<>();

    // todo use map to store active connections
    private final List<ClientHandler> handlers = new ArrayList<>();

    public GameServer() throws IOException {
        game = new Game(800, 600);
        game.start();
        game.setCallback(() -> {
            for (ClientHandler handler : handlers) {
                try {
                    handler.send(stringifyData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        receiver = new UDPReceiver(9999);

        try {
            while (true) {
                System.out.println("[Server] Waiting for client to connect...");

                // todo make handlers for each request
                String response = receiver.getLine();
                if (response.startsWith("controls")) {
                    String id = response.split(" ")[1];
                    for (ClientHandler handler : handlers) {
                        if (handler.getTank().getId().toString().equals(id)) {
                            handler.updateTank(response.split(" ")[2]);
                        }
                    }

                    continue;
                }

                System.out.println("[Server] Client connected");
                ClientHandler handler = new ClientHandler(game, receiver.getAddress(), receiver.getPort());
                handler.send(handler.getTank().getId().toString());
                handlers.add(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // todo optimize data collection
    private String stringifyData() {
        StringBuilder s = new StringBuilder();
        for (GameObject o : game.getObjects()) {
            s.append(GameObjectUpdateData.stringify(GameObjectUpdateData.fromGameObject(o))).append(" ");
        }

        return s.toString();
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }
}
