package brickbreaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameCanvas {
    public static final int CANVAS_HEIGHT = 600;
    public static final int CANVAS_WIDTH  = 600;

    private BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
    private Graphics2D graphics = image.createGraphics();

    private JFrame window = new JFrame("New Window!");

    private JPanel canvas = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    };

    public GameCanvas() {
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        window.setResizable(false);

        canvas.setBackground(Color.BLACK);
        window.add(canvas);
        window.getContentPane().setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public JPanel getCanvas() {
        return canvas;
    }

    public JFrame getWindow() {
        return window;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void repaint() {
        window.repaint();
    }
}
