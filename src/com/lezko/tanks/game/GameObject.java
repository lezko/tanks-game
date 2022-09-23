package com.lezko.tanks.game;

public class GameObject {

    private int x;
    private int y;

    private int size;

    private double acceleration;

    private double movingSpeed = 0;

    private int maxMovingSpeed;
    private int angle = 90;
    private boolean isMoving = false;

    public GameObject(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int[] getShift() {
        movingSpeed = Math.min(movingSpeed + acceleration, maxMovingSpeed);
        double a = Math.cos(Math.toRadians(angle)) * movingSpeed;
        double b = Math.sin(Math.toRadians(angle)) * movingSpeed;

        return new int[]{(int) a, (int) b};
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxMovingSpeed(int maxMovingSpeed) {
        this.maxMovingSpeed = maxMovingSpeed;
    }

    public int getAngle() {
        return angle;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    public double getMovingSpeed() {
        return movingSpeed;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void update() {}
}
