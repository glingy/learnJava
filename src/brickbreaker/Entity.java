package brickbreaker;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Entity {
    private ArrayList<UpdateListener> listeners = new ArrayList<>();

    public void addUpdateListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public void removeUpdateListener(UpdateListener listener) {
        listeners.remove(listener);
    }

    public void preUpdate(World world) {
        for (UpdateListener listener : listeners) {
            listener.onBeforeUpdate(world);
        }
    }

    public void postUpdate(World world) {
        for (UpdateListener listener : listeners) {
            listener.onAfterUpdate(world);
        }
    }

    public abstract void update(World world);

    public void clear(Graphics2D graphics) {}
    public void draw(Graphics2D graphics) {}
}
