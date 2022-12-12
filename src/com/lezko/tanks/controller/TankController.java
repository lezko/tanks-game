package com.lezko.tanks.controller;

import java.util.HashMap;
import java.util.Map;

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

    public String stringifyState() {
        return
            String.valueOf(forwards ? 1 : 0)
            + String.valueOf(backwards ? 1 : 0)
            + String.valueOf(left ? 1 : 0)
            + String.valueOf(right ? 1 : 0)
            + String.valueOf(shoot ? 1 : 0)
            ;
    }

    public Map<String, Boolean> getState() {
        Map<String, Boolean> state = new HashMap<>();
        state.put("forwards", forwards);
        state.put("backwards", backwards);
        state.put("left", left);
        state.put("right", right);
        state.put("shoot", shoot);

        return state;
    }
}