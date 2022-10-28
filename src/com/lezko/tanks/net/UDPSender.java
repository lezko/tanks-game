package com.lezko.tanks.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSender {

    private final DatagramSocket socket = new DatagramSocket();

    private final InetAddress address;
    private final int port;

    public UDPSender(InetAddress address, int port) throws SocketException {
        this.address = address;
        this.port = port;
    }

    public void send(String s) throws IOException {
        byte[] buf = s.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }
}
