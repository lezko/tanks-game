package com.lezko.tanks.game;

import com.lezko.tanks.models.Tank;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */
public class Game {

    private List<Tank> tanks = new LinkedList<>();
    private Timer timer = new Timer();

    private int width;
    private int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;

        Game game = this;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.tick();
            }
        }, 0, 1000);
    }

    public void addTank() {
        tanks.add(new Tank(0, 0));
    }

    private void tick() {
        for (Tank tank : tanks) {
            tank.update();
        }
        log();
    }

    private void log() {
        int[][] field = new int[width][height];
        for (Tank tank : tanks) {
            field[tank.getY()][tank.getX()] = 1;
        }

        for (int i = 0; i < width; i++) {
            System.out.print('=');
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < width; i++) {
            System.out.print('=');
        }
        System.out.println();
    }
}
