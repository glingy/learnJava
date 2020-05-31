package speller;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    public Rectangle2D rect;

    public abstract void draw(Graphics2D g);
}
