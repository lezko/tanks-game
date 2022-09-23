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

    private Runnable callback;
    private List<GameObject> objects = new LinkedList<>();
    private Timer timer = new Timer();

    private int width;
    private int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;

        addTank();
        addTank();
        Game game = this;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.tick();
            }
        }, 0, 10);
    }

    public void addTank() {
        Tank tank = new Tank(100, 100, 40);
        objects.add(tank);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    private void tick() {
        for (GameObject object : objects) {
            object.update();
        }
        if (callback != null) {
            callback.run();
        }
        // debug
//        System.out.println(tanks.get(0).getRotationAngle());
//        log();
    }

    private boolean contains(GameObject object) {
        return object.getX() <= width && object.getY() <= height;
    }

//    private void log() {
//        int[][] field = new int[height][width];
//        for (Tank tank : objects) {
//            field[tank.getY()][tank.getX()] = 1;
//        }
//
//        for (int i = 0; i < width; i++) {
//            System.out.print('=');
//        }
//        System.out.println();
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                System.out.print(field[i][j]);
//            }
//            System.out.println();
//        }
//        for (int i = 0; i < width; i++) {
//            System.out.print('=');
//        }
//        System.out.println();
//    }


    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
