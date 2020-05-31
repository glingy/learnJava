package speller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

public class Bucket extends Entity implements KeyListener {
    // Keycodes: Left - 37, Right - 39
    private static final int LEFT = 37;
    private static final int RIGHT = 39;

    private int left = 0;
    private int right = 0;

    public double getMinEntryY() {
        return rect.getMinY() + 5;
    }

    public Bucket(int x, int y) {
        rect = new Rectangle2D.Double(x, y, 50, 50);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GRAY);
        try {
            Image image = ImageIO.read(Bucket.class.getResource("/Bucket.png"));
            g.drawImage(image, (int) rect.getX(), (int) rect.getY(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} // ignored

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == LEFT) {
            left = 1;
        } else if (e.getKeyCode() == RIGHT) {
            right = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == LEFT) {
            left = 0;
        } else if (e.getKeyCode() == RIGHT) {
            right = 0;
        }
    }

    public void move() {
        Rectangle2D.Double rect = (Rectangle2D.Double) this.rect;
        rect.x += (right - left) * 1.5;
        if (rect.x < 0) {
            rect.x = 0;
        } else if (rect.x > GameCanvas.CANVAS_WIDTH - rect.width) {
            rect.x = GameCanvas.CANVAS_WIDTH - rect.width;
        }
    }
}
