package com.lezko.tanks.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPReceiver {

    private final DatagramSocket socket;
    private final byte[] buff = new byte[1024];

    private InetAddress address;
    private int port;

    public UDPReceiver(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public String getLine() throws IOException {
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);

        address = packet.getAddress();
        port = packet.getPort();

        byte[] data = new byte[packet.getLength()];
        System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());

        return new String(data);
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
