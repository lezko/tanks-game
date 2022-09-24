package com.lezko.tanks.controller;

import com.lezko.tanks.game.Tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankController {

    private static boolean f = false;

    public static void initKeyboardController(Component component, Tank tank) {

        if (f) {
            return;
        }
        f = true;

        component.addKeyListener(new KeyListener() {
            void updateTank() {
                if (!keyHolder.forwards && !keyHolder.backwards) {
                    tank.setMoving(false);
                } else {
                    if (keyHolder.forwards) {
                        tank.setMoveDirection(Tank.MoveDirection.FORWARDS);
                    } else {
                        tank.setMoveDirection(Tank.MoveDirection.BACKWARDS);
                    }
                    tank.setMoving(true);
                }

                if (!keyHolder.left && !keyHolder.right) {
                    tank.setRotating(false);
                } else {
                    if (keyHolder.left) {
                        tank.setRotateDirection(Tank.RotateDirection.COUNTERCLOCKWISE);
                    } else {
                        tank.setRotateDirection(Tank.RotateDirection.CLOCKWISE);
                    }
                    tank.setRotating(true);
                }
            }

             class KeyHolder {
                 boolean forwards = false;
                 boolean backwards = false;
                 boolean left = false;
                 boolean right = false;
                 boolean shoot = false;
            }

            KeyHolder keyHolder = new KeyHolder();

            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                switch (key) {
                    case 87:
                        keyHolder.forwards = true;
                        break;
                    case 83:
                        keyHolder.backwards = true;
                        break;
                    case 65:
                        keyHolder.left = true;
                        break;
                    case 68:
                        keyHolder.right = true;
                        break;
                    case 32:
                        keyHolder.shoot = true;
                        tank.shoot();
                        break;
                }
                updateTank();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                switch (key) {
                    case 87:
                        keyHolder.forwards = false;
                        break;
                    case 83:
                        keyHolder.backwards = false;
                        break;
                    case 65:
                        keyHolder.left = false;
                        break;
                    case 68:
                        keyHolder.right = false;
                        break;
                    case 32:
                        keyHolder.shoot = false;
                        break;
                }
                updateTank();
            }
        });
    }

//    public static void initIOController(InputStream in, Tank tank) {
//        Scanner scanner = new Scanner(in);
//        String input;
//        while (true) {
//            input = scanner.nextLine();
//            switch (input) {
//                case "u":
//                    tank.setMoveDirection(Tank.MoveDirection.UP);
//                    tank.setMoving(true);
//                    break;
//                case "d":
//                    tank.setMoveDirection(Tank.MoveDirection.DOWN);
//                    tank.setMoving(true);
//                    break;
//                case "l":
//                    tank.setMoveDirection(Tank.MoveDirection.LEFT);
//                    tank.setMoving(true);
//                    break;
//                case "r":
//                    tank.setMoveDirection(Tank.MoveDirection.RIGHT);
//                    tank.setMoving(true);
//                    break;
//                default:
//                    tank.setMoving(false);
//            }
//        }
//    }
}
