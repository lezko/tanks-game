package com.lezko.tanks.controller;

import com.lezko.tanks.game.Tank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Scanner;

public class TankController {

    boolean forwards = false;
    boolean backwards = false;
    boolean left = false;
    boolean right = false;
    boolean shoot = false;

    public void setForwards(boolean forwards) {
        this.forwards = forwards;
    }

    public void setBackwards(boolean backwards) {
        this.backwards = backwards;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public void reset() {
        forwards = false;
        backwards = false;
        left = false;
        right = false;
        shoot = false;
    }

    private final Tank tank;

    public void updateTank() {
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

    public TankController(Tank tank) {
        this.tank = tank;
    }
}