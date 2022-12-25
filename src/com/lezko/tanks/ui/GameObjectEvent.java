package com.lezko.tanks.ui;

import com.lezko.simplejson.MapObj;
import com.lezko.simplejson.Obj;
import com.lezko.tanks.game.GameObject;

import java.util.UUID;

public class GameObjectEvent {

    public final UUID id;
    public final GameObject.Type type;
    public final GameObject.State state;
    public final double x, y;
    public final int size, angle;

    public GameObjectEvent(double x, double y, int size, int angle, GameObject.Type type, UUID id, GameObject.State state) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.angle = angle;
        this.type = type;
        this.id = id;
        this.state = state;
    }

    public GameObjectEvent(GameObject o) {
        this(o.getX(), o.getY(), o.getSize(), o.getAngle(), o.getType(), o.getId(), o.getState());
    }

    public static GameObjectEvent fromString(String s) {
        return GameObjectEvent.fromJson(Obj.fromString(s));
    }

    public static GameObjectEvent fromJson(Obj o) {
        return new GameObjectEvent(
            Double.parseDouble(o.get("x").val()),
            Double.parseDouble(o.get("y").val()),
            Integer.parseInt(o.get("size").val()),
            Integer.parseInt(o.get("angle").val()),
            GameObject.Type.valueOf(o.get("type").val()),
            UUID.fromString(o.get("id").val()),
            GameObject.State.valueOf(o.get("state").val())
        );
    }

    public String toString() {
        MapObj o = new MapObj();
        o.put("x", String.valueOf(x));
        o.put("y", String.valueOf(y));
        o.put("size", String.valueOf(size));
        o.put("angle", String.valueOf(angle));
        o.put("type", String.valueOf(type));
        o.put("id", String.valueOf(id));
        o.put("state", String.valueOf(state));
        return o.toString();
    }
}
