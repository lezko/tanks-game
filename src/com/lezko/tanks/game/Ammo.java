package com.lezko.tanks.game;

import java.util.function.Consumer;

public class Ammo extends GameObject {

    private static final int SIZE = 10;
    private static int MOVING_SPEED = 4;

    private Consumer<Integer> onHit;

    public Ammo(double x, double y, int angle, Game game, Consumer<Integer> onHit) {
        this(x, y, angle, SIZE, game, onHit);
    }

    public Ammo(double x, double y, int angle, int size, Game game, Consumer<Integer> onHit) {
        super(x, y, size, game);
        this.onHit = onHit;
        setState(State.ALIVE);
        setMoving(false);
        setAngle(angle);
        setMovingSpeed(MOVING_SPEED);
    }

    public void destroy() {
        super.destroy();
        System.out.println("ammo destroyed");
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

        for (GameObject object : getGame().getObjects()) {
            if (object instanceof Target) {
                Target target = (Target) object;
                if (intersect(target)) {
                    target.destroy();
                    destroy();

                    onHit.accept(target.getCost());
                }
            }
        }
    }
}
