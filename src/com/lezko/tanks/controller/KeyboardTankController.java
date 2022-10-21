package com.lezko.tanks.controller;

import com.lezko.tanks.game.Tank;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyboardTankController extends TankController {

    public static class KeyPreset {
        private String forwards;
        private String backwards;
        private String left;
        private String right;
        private String shoot;

        public KeyPreset(String forwards, String backwards, String left, String right, String shoot) {
            this.forwards = forwards;
            this.backwards = backwards;
            this.left = left;
            this.right = right;
            this.shoot = shoot;
        }

        public String getForwards() {
            return forwards;
        }

        public void setForwards(String forwards) {
            this.forwards = forwards;
        }

        public String getBackwards() {
            return backwards;
        }

        public void setBackwards(String backwards) {
            this.backwards = backwards;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getShoot() {
            return shoot;
        }

        public void setShoot(String shoot) {
            this.shoot = shoot;
        }
    }

    private final KeyPreset defaultPreset = new KeyPreset("W", "S", "A", "D", "SPACE");

    public KeyboardTankController(Tank tank, JComponent component) {
        this(tank, component, null);
    }

    public KeyboardTankController(Tank tank, JComponent component, KeyPreset preset) {
        super(tank);

        if (preset == null) {
            preset = defaultPreset;
        }

        int hash = tank.hashCode();

        // forwards-backwards actions
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.forwards), "FORWARDS" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.forwards), "RELEASED FORWARDS" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.backwards), "BACKWARDS" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.backwards), "RELEASED BACKWARDS" + hash);

        component.getActionMap().put("FORWARDS" + hash, new ActionForwards());
        component.getActionMap().put("RELEASED FORWARDS" + hash, new ActionReleasedForwards());
        component.getActionMap().put("BACKWARDS" + hash, new ActionBackwards());
        component.getActionMap().put("RELEASED BACKWARDS" + hash, new ActionReleasedBackwards());

        // left-right actions
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.left), "LEFT" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.left), "RELEASED LEFT" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.right), "RIGHT" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.right), "RELEASED RIGHT" + hash);

        component.getActionMap().put("LEFT" + hash, new ActionLeft());
        component.getActionMap().put("RELEASED LEFT" + hash, new ActionReleasedLeft());
        component.getActionMap().put("RIGHT" + hash, new ActionRight());
        component.getActionMap().put("RELEASED RIGHT" + hash, new ActionReleasedRight());

        // shoot actions
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.shoot), "SHOOT" + hash);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.shoot), "RELEASED SHOOT" + hash);

        component.getActionMap().put("SHOOT" + hash, new ActionShoot());
        component.getActionMap().put("RELEASED SHOOT" + hash, new ActionReleasedShoot());
    }

    private class TankAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updateTank();
        }
    }

//     forwards-backwards controls
    private class ActionForwards extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setForwards(true);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionReleasedForwards extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setForwards(false);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionBackwards extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setBackwards(true);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionReleasedBackwards extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setBackwards(false);
            super.actionPerformed(actionEvent);
        }
    }

//     left-right controls
    private class ActionLeft extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setLeft(true);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionReleasedLeft extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setLeft(false);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionRight extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setRight(true);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionReleasedRight extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setRight(false);
            super.actionPerformed(actionEvent);
        }
    }

//     shoot controls
    private class ActionShoot extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setShoot(true);
            super.actionPerformed(actionEvent);
        }
    }

    private class ActionReleasedShoot extends TankAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setShoot(false);
            super.actionPerformed(actionEvent);
        }
    }
}
