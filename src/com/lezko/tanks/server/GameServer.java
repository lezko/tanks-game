package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class GameServer {

    private Game game;

    private DatagramSocket socket;
    private DatagramPacket sendPacket, receivePacket;
    private byte[] sendBuf, receiveBuf = new byte[256];

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

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        socket = new DatagramSocket(9999);

        try {
            while (true) {
                System.out.println("[Server] Waiting for client to connect...");
                receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                socket.receive(receivePacket);

                String response = new String(receiveBuf).trim();
                if (response.startsWith("controls")) {

                }

                System.out.println("[Server] Client connected");
                ClientHandler handler = new ClientHandler(game, receivePacket.getAddress(), receivePacket.getPort());
//                pool.execute(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    private String stringifyData() {
        StringBuilder s = new StringBuilder();
//        s.append(game.getObjects().size()).append(" ");
        for (GameObject o : game.getObjects()) {
            s.append(GameObjectUpdateData.stringify(GameObjectUpdateData.fromGameObject(o))).append(" ");
        }

        return s.toString();
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }
}
