package com.lezko.tanks.game;

public class Player {

    private Tank tank;
    private int score;
    private Game game;

    public Player(Game game) {
        this.game = game;
        tank = new Tank(game.getWidth() / 2, game.getHeight() / 2, 40, game, this);
        game.addObject(tank);
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int n) {
        score += n;
    }
}
