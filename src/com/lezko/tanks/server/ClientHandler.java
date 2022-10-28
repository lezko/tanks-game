package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.Tank;
import com.lezko.tanks.net.UDPSender;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class ClientHandler {

    private final UUID id = UUID.randomUUID();

    private Game game;
    private Tank tank;

    private UDPSender sender;

    public ClientHandler(Game game, InetAddress address, int port) throws IOException {
        this.game = game;
        sender = new UDPSender(address, port);
        tank = game.addPlayer().getTank();
    }

    public void send(String data) throws IOException {
        sender.send(data);
    }

    public Tank getTank() {
        return tank;
    }

    public UUID getId() {
        return id;
    }

    // todo move method inside Tank class
    public void updateTank(String state) {
        // todo make "update controls" object
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
