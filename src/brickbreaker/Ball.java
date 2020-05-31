package brickbreaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Entity {
    private int x;
    private int y;
    private int vx = 4;
    private int vy = 4;
    private int r;

    public Ball(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void clear(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(x, y, r * 2, r * 2);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillOval(x, y, r * 2, r * 2);
    }

    @Override
    public void update(World world) {
        x += vx;
        y += vy;
        // check world bounds

        if (x - r < 0 || x + r > GameCanvas.CANVAS_WIDTH) {
            if ((x - r < 0 && vx < 0) || (x + r > GameCanvas.CANVAS_WIDTH && vx > 0)) {
                vx *= -1;
                x += vx * 2; // reverse change in x from earlier
            }
            return; // let's just deal with getting the ball onscreen before dealing with other collisions.
        } else if (y - r < 0 || y + r > GameCanvas.CANVAS_HEIGHT) {
            if ((y - r < 0 && vy < 0) || (y + r > GameCanvas.CANVAS_HEIGHT && vy > 0)) {
                vy *= -1;
                y += vy * 2; // reverse change in y from earlier
            }
            return; // let's just deal with getting the ball onscreen before dealing with other collisions.
        }


        // check paddle

        Rectangle paddle = world.getEntity(Paddle.class).getBoundingRect();

        /**
         *  (0, 0)----------------------------------------------|
         *     |                                                |
         *     |                 brickbreaker.Paddle                         |
         *     |                                                |
         *     |                                                |
         *     |------------------------------------------------|
         */

        // Is any part of the ball intersecting the paddle? (assumes the ball is a rectangle for simplicity)
        if (y - r < paddle.getMaxY() && y + r > paddle.getMinY() && x + r > paddle.getMinX() && x - r < paddle.getMaxX()) {
            // Where did it hit? Top/left (near 0), or bottom/right (near max?)
            // Easily distinguish between top/bottom and left/right by using the center of the paddle
            boolean top = y < paddle.getCenterY();
            boolean left = x < paddle.getCenterX();

            // Now is it shallower in the x or y direction from the outer bounds? (negative for center of ball outside bounds on that axis)
            double ydist = top ? y - paddle.getMinY() : paddle.getMaxY() - y;
            double xdist = top ? x - paddle.getMinX() : paddle.getMaxX() - x;
            if (xdist < ydist) {
                // x is shallower, need to change x velocity only
                // If we're not already moving in the right direction...
                if (! ( (left && vx < 0) || (!left && vx > 0) ) ) {
                    // reverse the x velocity
                    vx *= -1;
                    x += vx * 2; // reverse change in x from earlier so we don't sit inside the paddle for a frame...
                }
            } else {
                // y is shallower, need to change y velocity only
                // If we're not already moving in the right direction...
                if (! ( (top && vy < 0) || (!top && vy > 0) ) ) {
                    // reverse the y velocity
                    vy *= -1;
                    y += vy * 2; // reverse change in y from earlier so we don't sit inside the paddle for a frame...
                }
            }
        }
    }


}

/**
 * Options...
 * 1) brickbreaker.Ball manages everything, modifications to physics...?
 * 2) Engine manages everything... better option
 *
 * How does the engine handle adaptions? Possibly keep state...?
 *  - Run each adaption / modification after each other, save old and new states? Each can modify the state...
 *  - dependent upon others... To prevent, has to undo the previous...
 * - Adaption init, loop, deinit? on init, can disable other adaptions? No... can't disable others, but can prevent normal physics...
 *  - during loop, can modify the world... and change stuff
 * - normal physics just one adaption? One adaption which can be disabled?
 * No... each piece should be one. Each piece its own part that can be disabled independently? - order dependent...
 *  - multiple balls? - Each physics object takes care of it's own collisions...?
 *  - physics object onbeforeUpdate, onAfterUpdate...
 *  - game engine calls onbefore on everything, then update, then onafter... onbefore can preventUpdate
 *  - Physics object class...!
 */