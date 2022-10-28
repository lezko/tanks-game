package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameSession implements Runnable {

    private final UUID id;
    private Game game;
    private final Map<UUID, ClientHandler> clients = new HashMap<>();

    public GameSession(UUID id) {
        this.id = id;
    }

    public void addClient(InetAddress address, int port) {

    }

    @Override
    public void run() {

    }

    public UUID getId() {
        return id;
    }
}
