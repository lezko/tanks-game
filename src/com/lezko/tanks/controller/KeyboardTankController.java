package com.lezko.tanks.controller;

import com.lezko.tanks.game.Tank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

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

    public KeyboardTankController(JComponent component) {
        this(component, null);
    }

    public KeyboardTankController(JComponent component, KeyPreset preset) {
        if (preset == null) {
            preset = defaultPreset;
        }

        String id = UUID.randomUUID().toString();

        // forwards-backwards actions
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.forwards), "FORWARDS" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.forwards), "RELEASED FORWARDS" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.backwards), "BACKWARDS" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.backwards), "RELEASED BACKWARDS" + id);

        component.getActionMap().put("FORWARDS" + id, new ActionForwards());
        component.getActionMap().put("RELEASED FORWARDS" + id, new ActionReleasedForwards());
        component.getActionMap().put("BACKWARDS" + id, new ActionBackwards());
        component.getActionMap().put("RELEASED BACKWARDS" + id, new ActionReleasedBackwards());

        // left-right actions
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.left), "LEFT" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.left), "RELEASED LEFT" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.right), "RIGHT" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.right), "RELEASED RIGHT" + id);

        component.getActionMap().put("LEFT" + id, new ActionLeft());
        component.getActionMap().put("RELEASED LEFT" + id, new ActionReleasedLeft());
        component.getActionMap().put("RIGHT" + id, new ActionRight());
        component.getActionMap().put("RELEASED RIGHT" + id, new ActionReleasedRight());

        // shoot actions
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(preset.shoot), "SHOOT" + id);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + preset.shoot), "RELEASED SHOOT" + id);

        component.getActionMap().put("SHOOT" + id, new ActionShoot());
        component.getActionMap().put("RELEASED SHOOT" + id, new ActionReleasedShoot());
    }

    private class TankAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
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
