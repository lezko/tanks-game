package com.lezko.tanks.game;

public class Ammo extends GameObject {

    public enum State {
        ALIVE,
        DESTROYED
    }

    private static double ACCELERATION = 1;
    private static int SIZE = 3;

    private State state;

    public Ammo(int x, int y) {
        this(x, y, SIZE);
    }

    public Ammo(int x, int y, int size) {
        super(x, y, size);
        state = State.ALIVE;
        setAcceleration(ACCELERATION);
        setMoving(true);
    }

    public void destroy() {
        state = State.DESTROYED;
        System.out.println("destroyed");
    }

    @Override
    public void update() {
        int[] shift = getShift();
        int a = shift[0], b = shift[1];

        setX(getX() + a);
        setY(getY() - b);
    }

    public State getState() {
        return state;
    }
}
