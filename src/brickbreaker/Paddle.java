package brickbreaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Paddle extends Entity {
    public static final int NORMAL_SIZE = 100;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_Y = GameCanvas.CANVAS_HEIGHT - 30;

    private double x = 300 - (NORMAL_SIZE / 2);
    private int size = NORMAL_SIZE;
    private int dir = 0;
    private Color color = Color.BLUE;
    private double speed = 4;

    public Paddle(GameCanvas canvas) {
        canvas.getWindow().addKeyListener(new KeyAdapter() {
            // Left - 37, Right - 39
            private static final int LEFT = 37;
            private static final int RIGHT = 39;

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == LEFT && getDirection() != -1) {
                    setDirection(-1);
                } else if (e.getKeyCode() == RIGHT && getDirection() != 1) {
                    setDirection(1);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == LEFT && getDirection() == -1) {
                    setDirection(0);
                } else if (e.getKeyCode() == RIGHT && getDirection() == 1) {
                    setDirection(0);
                }
            }
        });
    }

    public void update(World world) {
        x += (dir * speed);
        if (x < 0) {
            x = 0;
        } else if (x > (GameCanvas.CANVAS_WIDTH - size)){
            x = GameCanvas.CANVAS_WIDTH - size;
        }
    }

    @Override
    public void clear(Graphics2D graphics) {
        graphics.clearRect((int)x, PADDLE_Y, size, PADDLE_HEIGHT);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect((int)x, PADDLE_Y, size, PADDLE_HEIGHT);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(int dir) {
        this.dir = dir;
    }

    public double getDirection() {
        return dir;
    }

    public Rectangle getBoundingRect() {
        return new Rectangle((int) x, PADDLE_Y, size, PADDLE_HEIGHT);
    }
}
