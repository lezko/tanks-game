package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class GameServer {

    private Game game;
    private ServerSocket listener;
    private Socket client;


    public GameServer() throws IOException {
        game = new Game(800, 600);
        game.start();

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        listener = new ServerSocket(9999);

        try {
            while (true) {
                System.out.println("[Server] Waiting for client to connect...");
                client = listener.accept();
                System.out.println("[Server] Client connected");
                ClientHandler handler = new ClientHandler(game, client);
                pool.execute(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pool.shutdown();
        listener.close();
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }
}
