package com.lezko.tanks.controller;

import com.lezko.tanks.game.Tank;

import java.io.InputStream;
import java.util.Scanner;

public class IOTankController extends TankController {

    private Scanner scanner;

    public IOTankController(InputStream in, Tank tank) {
        scanner = new Scanner(in);
        String input;
        while (true) {
            input = scanner.nextLine();
            switch (input) {
                case "w":
                    set
                    break;
                case "d":
                    break;
                case "l":
                    break;
                case "r":
                    break;
                default:
                    tank.setMoving(false);
            }

            updateTank();
        }
    }
}
