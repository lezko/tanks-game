package com.lezko.tanks.game;

import com.lezko.tanks.game.Ammo;
import com.lezko.tanks.game.Game;
import com.lezko.tanks.game.GameObject;

public class Tank extends GameObject {

    public enum MoveDirection {
        FORWARDS,
        BACKWARDS
    }

    public enum RotateDirection {
        CLOCKWISE,
        COUNTERCLOCKWISE
    }

    private Player player;

    private static double ACCELERATION = 1e-1;
    private static int MAX_MOVING_SPEED = 2;

    private MoveDirection moveDirection = MoveDirection.FORWARDS;
    private RotateDirection rotateDirection = RotateDirection.COUNTERCLOCKWISE;
    private int rotationSpeed = 1;
    private boolean isRotating = false;

    public Tank(int x, int y, int size, Game game, Player player) {
        super(x, y, size, game);
        this.player = player;
        setMaxMovingSpeed(MAX_MOVING_SPEED);
        setAcceleration(ACCELERATION);
    }

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }

    public void setRotating(boolean rotating) {
        isRotating = rotating;
    }

    public RotateDirection getRotateDirection() {
        return rotateDirection;
    }

    public void setRotateDirection(RotateDirection rotateDirection) {
        this.rotateDirection = rotateDirection;
    }

    public void shoot() {
        getGame().addObject(new Ammo(getX() + getSize() / 2.0, getY() + getSize() / 2.0, getAngle(), getGame(), (n -> player.addScore(n))));
    }

    @Override
    public void update() {
        if (!isMoving() && !isRotating) {
            if (getMovingSpeed() <= 0) {
                setMovingSpeed(0);
                return;
            }
            setAcceleration(-Math.abs(getAcceleration()));
            move();
            return;
        }

        if (isRotating) {
            switch (rotateDirection) {
                case CLOCKWISE:
                    setAngle(getAngle() - rotationSpeed);
                    if (getAngle() < 0) {
                        setAngle(getAngle() + 360);
                    }
                    break;
                case COUNTERCLOCKWISE:
                    setAngle(getAngle() + rotationSpeed);
                    if (getAngle() > 360) {
                        setAngle(getAngle() % 360);
                    }
                    break;
            }
        }

        if (isMoving()) {
            setAcceleration(Math.abs(getAcceleration()));
            move();
        }
    }

    private void move() {
        double[] shift = getShift();
        double a = shift[0], b = shift[1];

        if (moveDirection == MoveDirection.BACKWARDS) {
            a = -a;
            b = -b;
        }

        setX(getX() + a);
        setY(getY() - b);

        if (!insideGameField()) {
            setX(getX() - a);
            setY(getY() + b);
        }
    }
}
