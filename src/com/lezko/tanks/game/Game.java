package com.lezko.tanks.game;

import java.util.*;

/**
 * Created by uleziko_t_a on 22.09.2022.
 */

public class Game {

    public enum State {
        NOT_STARTED,
        IN_PROGRESS,
        PAUSED,
        FINISHED
    }

    private State state;

    private Timer timer;
    private Runnable callback;
    private final List<GameObject> objects = new LinkedList<>();
    private Player player;

    private int width;
    private int height;

    public Player getPlayer() {
        return player;
    }

    public Game(int width, int height) {
        this.width = width;
        this.height = height;

        state = State.NOT_STARTED;
    }

    public void start() {
        if (state == State.IN_PROGRESS) {
            System.err.println("Cannot start game: its already running");
            return;
        }

        player = new Player(this);
        initTimer();
        state = State.IN_PROGRESS;
    }

    public void finish() {
        timer.cancel();
        timer.purge();
        objects.clear();

        state = State.FINISHED;
    }

    private void initTimer() {
        TargetGenerator generator = new TargetGenerator(200, this);
        Game game = this;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generator.generate();
                game.tick();
            }
        }, 0, 10);
    }

    public State getState() {
        return state;
    }

    public void pause() {
        timer.cancel();
        timer.purge();

        state = State.PAUSED;
    }

    public void resume() {
        initTimer();

        state = State.IN_PROGRESS;
    }

    public void reset() {
        finish();
        start();
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    private void tick() {
        ListIterator<GameObject> iterator = objects.listIterator();
        while (iterator.hasNext()) {
            GameObject object = iterator.next();
            object.update();
            if (object.getState() == GameObject.State.DESTROYED) {
                iterator.remove();
            }
        }
        if (callback != null) {
            callback.run();
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
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
