package com.lezko.tanks.game;

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

    private int COOL_DOWN = 50;
    private int overheat = 0;

    public Tank(int x, int y, int size, Game game, Player player) {
        super(x, y, size, game);
        this.player = player;
        setMaxMovingSpeed(MAX_MOVING_SPEED);
        setAcceleration(ACCELERATION);

        setType(Type.TANK);
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
        if (overheat > 0) {
            return;
        }
        getGame().addObject(new Ammo(getX() + getSize() / 2.0, getY() + getSize() / 2.0, getAngle(), getGame(), (n -> player.addScore(n))));
        overheat = COOL_DOWN;
    }

    @Override
    public void update() {
        overheat = Math.max(overheat - 1, 0);

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

    public void update(String state) {
        boolean forwards = state.charAt(0) == '1';
        boolean backwards = state.charAt(1) == '1';
        boolean left = state.charAt(2) == '1';
        boolean right = state.charAt(3) == '1';
        boolean shoot = state.charAt(4) == '1';

        if (!forwards && !backwards) {
            setMoving(false);
        } else {
            if (forwards) {
                setMoveDirection(Tank.MoveDirection.FORWARDS);
            } else {
                setMoveDirection(Tank.MoveDirection.BACKWARDS);
            }
            setMoving(true);
        }

        if (!left && !right) {
            setRotating(false);
        } else {
            if (left) {
                setRotateDirection(Tank.RotateDirection.COUNTERCLOCKWISE);
            } else {
                setRotateDirection(Tank.RotateDirection.CLOCKWISE);
            }
            setRotating(true);
        }

        if (shoot) {
            shoot();
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
