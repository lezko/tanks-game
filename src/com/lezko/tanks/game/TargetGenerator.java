package com.lezko.tanks.game;

public class TargetGenerator {

    private int period;
    private int t = 0;
    private Game game;

    private int SIZE = 10;

    public TargetGenerator(int period, Game game) {
        this.period = period;
        this.game = game;
    }

    public void generate() {
        if (t < period) {
            t++;
            return;
        }
        int x = (int) (Math.random() * game.getWidth());
        int y = (int) (Math.random() * game.getHeight());
        game.addObject(new Target(x, y, SIZE, game));
        t = 0;
    }
}
