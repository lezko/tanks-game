package com.lezko.tanks.controller;

import com.lezko.tanks.game.Tank;

import java.io.InputStream;
import java.util.Scanner;

public class IOTankController extends TankController {

    private Scanner scanner;

    public IOTankController(InputStream in, Tank tank) {

        super(tank);

        scanner = new Scanner(in);
        String input;
        while (true) {
            input = scanner.nextLine();
            switch (input) {
                case "w":
                    setForwards(true);
                    break;
                case "s":
                    setBackwards(true);
                    break;
                case "a":
                    setLeft(true);
                    break;
                case "d":
                    setRight(true);
                    break;
                case "q":
                    setShoot(true);
                    break;
                default:
                    reset();
            }

            updateTank();
        }
    }
}
