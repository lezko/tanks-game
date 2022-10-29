package com.lezko.tanks.server;

import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.net.UDPSender;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class GameServer {

    private final int SESSION_LIMIT = 5;

    private UDPReceiver receiver;
    private final Map<UUID, GameSession> sessions = new HashMap<>();

    public GameServer() throws IOException {
        receiver = new UDPReceiver(9999);

        System.out.println("[Server] Waiting for clients to connect...");
        try {
            while (true) {
                // todo make handlers for each request
                String response = receiver.getLine();
                String[] arr = response.split(" ");

                if (response.startsWith("controls")) {
                    UUID sessionId = UUID.fromString(arr[1]);
                    UUID clientId = UUID.fromString(arr[2]);
                    sessions.get(sessionId).updatePlayer(clientId, arr[3]);
                } else if (response.startsWith("join")) {
                    UUID sessionId = UUID.fromString(arr[1]);
                    sessions.get(sessionId).addClient(receiver.getAddress(), receiver.getPort());
                } else if (response.startsWith("create")) {
                    UDPSender sender = new UDPSender(receiver.getAddress(), receiver.getPort());
                    if (sessions.size() == SESSION_LIMIT) {
                        sender.send("Sessions limit exceed");
                    } else {
                        CountDownLatch latch = new CountDownLatch(1);
                        GameSession newSession = new GameSession(latch);
                        sessions.put(newSession.getId(), newSession);
                        new Thread(newSession).start();
                        latch.await();

                        UUID clientId = newSession.addClient(receiver.getAddress(), receiver.getPort());
                        sender.send(newSession.getId().toString() + " " +  clientId.toString());
                        System.out.println("created session " + newSession.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }
}
