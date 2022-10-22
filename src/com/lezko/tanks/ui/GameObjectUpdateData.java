package com.lezko.tanks.ui;

import com.lezko.tanks.game.GameObject;

import java.util.UUID;

public class GameObjectUpdateData {

    private final UUID id;
    private final GameObject.Type type;
    private final GameObject.State state;
    private final double x, y;
    private final int size, angle;

    public GameObjectUpdateData(double x, double y, int size, int angle, GameObject.Type type, UUID id, GameObject.State state) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.angle = angle;
        this.type = type;
        this.id = id;
        this.state = state;
    }

    public static GameObjectUpdateData parse(String s) {
        String[] arr = s.split(" ");
        double x = Double.parseDouble(arr[0]);
        double y = Double.parseDouble(arr[1]);
        int size = Integer.parseInt(arr[2]);
        int angle = Integer.parseInt(arr[3]);
        GameObject.Type type = null;
        switch (arr[4]) {
            case "TANK":
                type = GameObject.Type.TANK;
                break;
            case "AMMO":
                type = GameObject.Type.AMMO;
                break;
            case "TARGET":
                type = GameObject.Type.TARGET;
                break;
        }
        UUID id = UUID.fromString(arr[5]);
        GameObject.State state = null;
        switch (arr[6]) {
            case "ALIVE":
                state = GameObject.State.ALIVE;
                break;
            case "DESTROYED":
                state = GameObject.State.DESTROYED;
                break;
        }

        return new GameObjectUpdateData(x, y, size, angle, type, id, state);
    }

    public static String stringify(GameObjectUpdateData data) {
        return
                String.valueOf(data.x) + " " +
                String.valueOf(data.y) + " " +
                String.valueOf(data.size) + " " +
                String.valueOf(data.angle) + " " +
                String.valueOf(data.type) + " " +
                String.valueOf(data.id) + " " +
                String.valueOf(data.state);
    }

    public static GameObjectUpdateData fromGameObject(GameObject o) {
        return new GameObjectUpdateData(o.getX(), o.getY(), o.getSize(), o.getAngle(), o.getType(), o.getId(), o.getState());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getAngle() {
        return angle;
    }

    public GameObject.Type getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public GameObject.State getState() {
        return state;
    }
}
