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

    private UUID id;

    private UDPSender sender;
    private UDPReceiver receiver;

    private Consumer<List<GameObjectUpdateData>> callback;

    private Scanner scanner;
    private StringBuilder sb;

    private byte[] sendBuf = new byte[256], receiveBuf = new byte[256];

    public GameClient(String address, int port, Consumer<List<GameObjectUpdateData>> callback) throws IOException {
        sender = new UDPSender(InetAddress.getByName(address), port);
        this.callback = callback;

        sender.send("create");

        String response = receiver.getLine();
        id = UUID.fromString(response);
        System.out.println(id);
    }

    public void sendControlsState(String state) throws IOException {
        sender.send("controls " + id + " " + state);
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
//        try {
//            while (true) {
//                receiveBuf = new byte[1024];
//                receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
//                socket.receive(receivePacket);
//
//                receiveBuf = new byte[receivePacket.getLength()];
//                System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), receiveBuf, 0, receivePacket.getLength());
//
//                String str = new String(receiveBuf);
//                callback.accept(parseData(str));
//            }
//        } catch (RuntimeException | IOException e) {
//            e.printStackTrace();
//        }
    }
}
