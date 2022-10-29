package com.lezko.tanks.client;

import com.lezko.tanks.net.UDPReceiver;
import com.lezko.tanks.net.UDPSender;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.function.Consumer;

public class GameClient {

    private UUID clientId;
    private UUID serverSessionId;

    private final UDPSender sender;
    private final UDPReceiver receiver;

    private final Consumer<List<GameObjectUpdateData>> callback;

    private Scanner scanner;
    private StringBuilder sb;

    private boolean listening = false;

    public GameClient(String address, int port, Consumer<List<GameObjectUpdateData>> callback) throws IOException {
        sender = new UDPSender(InetAddress.getByName(address), port);
        receiver = new UDPReceiver(sender.getSocket());
        this.callback = callback;
    }

    public List<UUID> getSessions() throws IOException {
        sender.send("sessions");
        List<UUID> result = new ArrayList<>();
        for (String str : receiver.getLine().split(" ")) {
            if (str.trim().equals("")) {
                break;
            }
            result.add(UUID.fromString(str));
        }

        return result;
    }

    public void sendControlsState(String state) throws IOException {
        sender.send("controls " + serverSessionId + " " + clientId + " " + state);
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

    public void createSession() throws IOException {
        sender.send("create");

        String response = receiver.getLine();
        String[] arr = response.split(" ");
        serverSessionId = UUID.fromString(arr[0]);
    }

    public void joinSession(UUID sessionId) throws IOException {
        serverSessionId = sessionId;
        sender.send("join" + " " + sessionId);
        String response = receiver.getLine();
        clientId = UUID.fromString(response);
    }

    public void startListening() {
        listening = true;
        Thread listeningThread = new Thread(() -> {
            try {
                while (true) {
                    if (!listening) {
                        break;
                    }

                    String str = receiver.getLine();
                    callback.accept(parseData(str));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listeningThread.start();
    }

    public void stopListening() {
        listening = false;
    }
}
