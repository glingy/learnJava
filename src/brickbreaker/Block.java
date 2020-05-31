package brickbreaker;

import java.awt.Color;
import java.awt.Graphics;

class Block {
    private Color color = Color.GREEN;
    private boolean broken = false;

    public Block() {
        broken = true;
    }

    public Block(Color color) {
        this.color = color;
    }

    /**
     * Collide with the block.
     * @return true if the block was destroyed. False otherwise.
     */
    public boolean collide() {
        broken = true;
        return true;
    }

    public boolean isBroken() {
        return broken;
    }

    public Color getColor() {
        return color;
    }
}
