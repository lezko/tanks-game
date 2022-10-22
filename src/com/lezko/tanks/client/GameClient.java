package com.lezko.tanks.client;

import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.function.Consumer;

public class GameClient implements Runnable {

    private UUID id = UUID.randomUUID();

    private Consumer<List<GameObjectUpdateData>> callback;

    private Scanner scanner;
    private StringBuilder sb;

    private final InetAddress address;
    private final int port;

    private final DatagramSocket socket = new DatagramSocket();
    private DatagramPacket sendPacket, receivePacket;

    private byte[] sendBuf = new byte[256], receiveBuf = new byte[256];

    public GameClient(String address, int port, Consumer<List<GameObjectUpdateData>> callback) throws IOException {
        this.address = InetAddress.getByName(address);
        this.port = port;

        this.callback = callback;

        sendPacket = new DatagramPacket(sendBuf, sendBuf.length, this.address, this.port);
        socket.send(sendPacket);
    }

    public void sendControlsState(String state) throws IOException {
        sendBuf = state.getBytes();
        sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(sendPacket);
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
                receiveBuf = new byte[1024];
                receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                socket.receive(receivePacket);

                receiveBuf = new byte[receivePacket.getLength()];
                System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), receiveBuf, 0, receivePacket.getLength());

                String str = new String(receiveBuf);
                callback.accept(parseData(str));
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }
}
