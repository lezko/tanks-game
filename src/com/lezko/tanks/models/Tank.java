package com.lezko.tanks.models;

import com.lezko.tanks.enums.Direction;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Tank {

    private int x;
    private int y;

    private Direction direction = Direction.RIGHT;
    private double accceleration = 1.2;
    private int speed = 1;
    private int maxSpeed = 30;
    private boolean isMoving = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        isMoving = true;
    }

    public void stop() {
        isMoving = false;
    }

    public void update() {
        if (!isMoving) {
            return;
        }

        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DONW:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
