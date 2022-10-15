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

    private class TankAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updateTank();
        }


    }

    // forwards-backwards controls
//    private class ActionForwards extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.forwards = true;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionReleasedForwards extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.forwards = false;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionBackwards extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.backwards = true;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionReleasedBackwards extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.backwards = false;
//            super.actionPerformed(actionEvent);
//        }
//    }

    // left-right controls
//    private class ActionLeft extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.left = true;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionReleasedLeft extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.left = false;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionRight extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.right = true;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionReleasedRight extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.right = false;
//            super.actionPerformed(actionEvent);
//        }
//    }

    // shoot controls
//    private class ActionShoot extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.shoot = true;
//            super.actionPerformed(actionEvent);
//        }
//    }
//
//    private class ActionReleasedShoot extends TankAction {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            keyHolder.shoot = false;
//            super.actionPerformed(actionEvent);
//        }
//    }

    public TankController(Tank tank) {

        this.tank = tank;
//        keyHolder = new KeyHolder();
//
//        // forwards-backwards actions
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "FORWARDS");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "RELEASED FORWARDS");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "BACKWARDS");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "RELEASED BACKWARDS");
//
//        component.getActionMap().put("FORWARDS", new ActionForwards());
//        component.getActionMap().put("RELEASED FORWARDS", new ActionReleasedForwards());
//        component.getActionMap().put("BACKWARDS", new ActionBackwards());
//        component.getActionMap().put("RELEASED BACKWARDS", new ActionReleasedBackwards());
//
//        // left-right actions
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "LEFT");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "RELEASED LEFT");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "RIGHT");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "RELEASED RIGHT");
//
//        component.getActionMap().put("LEFT", new ActionLeft());
//        component.getActionMap().put("RELEASED LEFT", new ActionReleasedLeft());
//        component.getActionMap().put("RIGHT", new ActionRight());
//        component.getActionMap().put("RELEASED RIGHT", new ActionReleasedRight());
//
//        // shoot actions
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "SHOOT");
//        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released SPACE"), "RELEASED SHOOT");
//
//        component.getActionMap().put("SHOOT", new ActionShoot());
//        component.getActionMap().put("RELEASED SHOOT", new ActionReleasedShoot());
    }
}