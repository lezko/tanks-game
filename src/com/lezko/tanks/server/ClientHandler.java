package com.lezko.tanks.server;

import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;
import com.lezko.tanks.game.Tank;
import com.lezko.tanks.ui.GameObjectUpdateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Game game;
    private Socket client;
    private Tank tank;

    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Game game, Socket client) throws IOException {
        this.game = game;
        this.client = client;

        tank = game.addPlayer().getTank();

        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String response = in.readLine();
                if (response.startsWith("controls")) {
                    updateTank(response.split(" ")[1]);
                } else {
                    out.println(stringifyData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            in.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }

    private String stringifyData() {
        StringBuilder s = new StringBuilder();
        s.append(game.getObjects().size()).append(" ");
        for (GameObject o : game.getObjects()) {
            s.append(GameObjectUpdateData.stringify(GameObjectUpdateData.fromGameObject(o))).append(" ");
        }

        return s.toString();
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
