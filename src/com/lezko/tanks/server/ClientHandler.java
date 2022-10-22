package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.game.Tank;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientHandler implements Runnable {

    private Game game;
    private Tank tank;


    private InetAddress address;
    private int port;
    private final DatagramSocket socket = new DatagramSocket();
    private DatagramPacket sendPacket, receivePacket;

    private byte[] sendBuf, receiveBuf = new byte[256];

    public ClientHandler(Game game, InetAddress address, int port) throws IOException {
        this.game = game;
        this.address = address;
        this.port = port;

        tank = game.addPlayer().getTank();

    }

    public void send(String data) throws IOException {
        sendBuf = data.getBytes();
        sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(sendPacket);
    }

    @Override
    public void run() {
        try {
            while (true) {
                receiveBuf = new byte[256];
                receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                String response = new String(receiveBuf).trim();
                System.out.println(response);
                if (response.startsWith("controls")) {
                    updateTank(response.split(" ")[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTank(String state) {
        boolean forwards = state.charAt(0) == '1';
        boolean backwards = state.charAt(1) == '1';
        boolean left = state.charAt(2) == '1';
        boolean right = state.charAt(3) == '1';
        boolean shoot = state.charAt(4) == '1';

        if (!forwards && !backwards) {
            tank.setMoving(false);
        } else {
            if (forwards) {
                tank.setMoveDirection(Tank.MoveDirection.FORWARDS);
            } else {
                tank.setMoveDirection(Tank.MoveDirection.BACKWARDS);
            }
            tank.setMoving(true);
        }

        if (!left && !right) {
            tank.setRotating(false);
        } else {
            if (left) {
                tank.setRotateDirection(Tank.RotateDirection.COUNTERCLOCKWISE);
            } else {
                tank.setRotateDirection(Tank.RotateDirection.CLOCKWISE);
            }
            tank.setRotating(true);
        }

        if (shoot) {
            tank.shoot();
        }
    }
}
