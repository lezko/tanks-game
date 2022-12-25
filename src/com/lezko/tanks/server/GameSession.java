package com.lezko.tanks.server;

import com.lezko.simplejson.ArrObj;
import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.game.Player;
import com.lezko.tanks.ui.GameObjectEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class GameSession implements Runnable {

    public static class LimitExceedException extends RuntimeException {
        public LimitExceedException() {
            super("Game session limit exceed");
        }
    }

    private final int CLIENT_LIMIT = 5;

    private final UUID id = UUID.randomUUID();
    private final Game game = new Game(800, 600);
    private final Map<UUID, ClientHandler> clientHandlers = new HashMap<>();
    private final Map<UUID, Player> clientPlayers = new HashMap<>(); // 1 - clientHandler id, 2 - game player id

    private final CountDownLatch latch = new CountDownLatch(1);

    public synchronized UUID removeClient(UUID clientHandlerId) {
        game.removePlayer(clientPlayers.get(clientHandlerId));
        clientHandlers.remove(clientHandlerId);
        clientPlayers.remove(clientHandlerId);
        return clientHandlerId;
    }

    public void addClient(ClientHandler clientHandler) throws InterruptedException, LimitExceedException {
        if (clientHandlers.size() == CLIENT_LIMIT) {
            throw new LimitExceedException();
        }

        latch.await();

        clientHandlers.put(clientHandler.getId(), clientHandler);
        Player newPlayer = game.addPlayer();
        clientPlayers.put(clientHandler.getId(), newPlayer);
    }

    public void updatePlayer(UUID clientHandlerId, String data) {
        synchronized (this) {
            clientPlayers.get(clientHandlerId).getTank().update(data);
        }
    }

    @Override
    public void run() {
        game.start();
        game.setCallback(() -> {
            synchronized (this) {
                for (ClientHandler client : clientHandlers.values()) {
                    try {
                        if (client.UDPReady()) {
                            client.sendUDP(stringifyData());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        latch.countDown();
    }

    public int getPlayersCount() {
        return clientHandlers.size();
    }

    // todo optimize data collection
    private String stringifyData() {
        ArrObj arr = new ArrObj();
        for (GameObject o : game.getObjects()) {
            arr.append(new GameObjectEvent(o).toString());
        }

        return arr.toString();
    }

    public UUID getId() {
        return id;
    }
}
