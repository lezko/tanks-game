package com.lezko.tanks.game;

public class Target extends GameObject {

    private double MOVING_SPEED = 1;

    public Target(int x, int y, int size, Game game) {
        super(x, y, size, game);
        setMovingSpeed(MOVING_SPEED);
        setAngle((int) (Math.random() * 360));
        setMoving(true);
    }

    @Override
    public void update() {
        double[] shift = getShift();
        double a = shift[0], b = shift[1];

        setX(getX() + a);
        setY(getY() - b);

        if (!insideGameField()) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("target destroyed");
    }
}
