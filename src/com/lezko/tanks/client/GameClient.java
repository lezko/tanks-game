package com.lezko.tanks.client;

import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class GameClient {

    private Socket server;
    private Scanner scanner;
    private StringBuilder sb;
    private BufferedReader in;
    private PrintWriter out;
    private UUID id = UUID.randomUUID();

    public GameClient(String address, int port) throws IOException {
        server = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(), true);
    }

    public void sendControlsState(String state) {
        out.println("controls " + state);
    }

    public List<GameObjectUpdateData> fetchData() throws IOException, ClassNotFoundException {
        out.println("fetch");

        String response = in.readLine();
        scanner = new Scanner(response);
        if (Objects.equals(scanner.next(), "null")) {
            return null;
        }

        List<GameObjectUpdateData> data = new ArrayList<>();
        while (scanner.hasNext()) {
            sb = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                sb.append(scanner.next()).append(" ");
            }
            data.add(GameObjectUpdateData.parse(sb.toString()));
        }

        return data;
    }
}
