package com.lezko.tanks.server;

import com.lezko.simplejson.MapObj;
import com.lezko.simplejson.Obj;
import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.net.UDPSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class GameServer {

    public static final int SESSION_LIMIT = 5;

    private final UDPReceiver receiver;
    private final ServerSocket serverSocket;
    private final Map<UUID, GameSession> sessions = new HashMap<>();
    private final Map<UUID, ClientHandler> clients = new HashMap<>();

    public GameServer() throws IOException {
        receiver = new UDPReceiver(9999);
        serverSocket = new ServerSocket(8888);

        System.out.println("[Server] Waiting for clients to connect...");

        new Thread(() -> {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[Server] Client connected");

                    ClientHandler newClient = new ClientHandler(clientSocket, sessions);
                    clients.put(newClient.getId(), newClient);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        try {
            while (true) {
                // todo make handlers for each request
                Obj req = Obj.fromString(receiver.getLine());
                System.out.println(req);
                switch (req.get("type").val()) {
                    case "controls" -> {
                        UUID sessionId = UUID.fromString(req.get("session_id").val());
                        UUID clientId = UUID.fromString(req.get("client_id").val());
                        String state = req.get("state").val();
                        sessions.get(sessionId).updatePlayer(clientId, state);
                    }
                    case "hello_udp" -> {
                        UUID id = UUID.fromString(req.get("client_id").val());
                        ClientHandler clientHandler = clients.get(id);
                        clientHandler.setSender(new UDPSender(receiver.getAddress(), receiver.getPort()));
                        synchronized (clientHandler) {
                            clientHandler.notifyAll();
                        }
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
