package com.lezko.tanks.client;

import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.net.UDPSender;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.function.Consumer;

public class GameClient implements Runnable {

    private final UUID id;
    private final UUID serverSessionId;

    private UDPSender sender;
    private UDPReceiver receiver;

    private Consumer<List<GameObjectUpdateData>> callback;

    private Scanner scanner;
    private StringBuilder sb;

    public GameClient(String address, int port, Consumer<List<GameObjectUpdateData>> callback) throws IOException {
        sender = new UDPSender(InetAddress.getByName(address), port);
        receiver = new UDPReceiver(sender.getSocket());
        this.callback = callback;

        sender.send("create");

        String response = receiver.getLine();
        String[] arr = response.split(" ");
        serverSessionId = UUID.fromString(arr[0]);
        id = UUID.fromString(arr[1]);
    }

    public void sendControlsState(String state) throws IOException {
        sender.send("controls " + serverSessionId + " " + id + " " + state);
    }

    public List<GameObjectUpdateData> parseData(String data) {
        scanner = new Scanner(data);
        if (Objects.equals(data.trim().toLowerCase(), "null")) {
            return null;
        }

        List<GameObjectUpdateData> parsedData = new ArrayList<>();
        while (scanner.hasNext()) {
            sb = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                sb.append(scanner.next()).append(" ");
            }
            parsedData.add(GameObjectUpdateData.parse(sb.toString()));
        }

        return parsedData;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = receiver.getLine();
                callback.accept(parseData(str));
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }
}
