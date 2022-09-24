package com.lezko.tanks.game;

import java.awt.*;

public class GameObject {

    public enum State {
        ALIVE,
        DESTROYED
    }

    private State state = State.ALIVE;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private Game game;

    private double x;
    private double y;

    private int size;

    private double acceleration = 0;
    private double movingSpeed = 0;

    private int maxMovingSpeed = 9999;
    private int angle = 90;
    private boolean isMoving = false;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameObject(double x, double y, int size, Game game) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.game = game;
    }

    public double[] getShift() {
        movingSpeed = Math.min(movingSpeed + acceleration, maxMovingSpeed);
        double a = Math.cos(Math.toRadians(angle)) * movingSpeed;
        double b = Math.sin(Math.toRadians(angle)) * movingSpeed;

        return new double[]{a, b};
    }

    public void destroy() {
        setState(State.DESTROYED);
    }

    public boolean intersect(GameObject object) {
        double x1 = getX(), y1 = getY();
        double x2 = x1 + getSize(), y2 = y1 + getSize();

        double x3 = object.getX(), y3 = object.getY();
        double x4 = x3 + object.getSize(), y4 = y3 + object.getSize();

        if (x1 > x4 || x3 > x2) {
            return false;
        }

        if (y2 < y3 || y4 < y1) {
            return false;
        }

        return true;
    }

    public boolean insideGameField() {
        return 0 <= getX() && getX() <= game.getWidth() - getSize() && 0 <= getY() && getY() <= game.getHeight() - getSize();
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
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
