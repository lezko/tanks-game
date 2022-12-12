package com.lezko.tanks.server;

import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.net.UDPSender;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

                if (response.startsWith("sessions")) {
                    StringBuilder sb = new StringBuilder();
                    for (GameSession session : sessions.values()) {
                        sb.append(session.getId()).append(" ").append(session.getPlayersCount()).append("|");
                    }
                    new UDPSender(receiver.getAddress(), receiver.getPort()).send(sb.toString());
                } else if (response.startsWith("controls")) {
                    UUID sessionId = UUID.fromString(arr[1]);
                    UUID clientId = UUID.fromString(arr[2]);
                    sessions.get(sessionId).updatePlayer(clientId, arr[3]);
                } else if (response.startsWith("join")) {
                    UUID sessionId = UUID.fromString(arr[1]);
                    UUID newClientId = sessions.get(sessionId).addClient(receiver.getAddress(), receiver.getPort());
                    new UDPSender(receiver.getAddress(), receiver.getPort()).send(newClientId.toString());
                } else if (response.startsWith("create")) {
                    UDPSender sender = new UDPSender(receiver.getAddress(), receiver.getPort());
                    if (sessions.size() == SESSION_LIMIT) {
                        sender.send("Sessions limit exceed");
                    } else {
                        GameSession newSession = new GameSession();
                        sessions.put(newSession.getId(), newSession);
                        new Thread(newSession).start();

                        sender.send(newSession.getId().toString());
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
