package brickbreaker;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockManager extends Entity {
    public static final int NUM_ROWS   = 10;
    public static final int NUM_COLS   = 15;

    public static final int BOTTOM_MARGIN = 400;

    public static final int BLOCK_MARG_X = 3;
    public static final int BLOCK_MARG_Y = 3;

    public static final int BLOCK_HEIGHT = ((GameCanvas.CANVAS_HEIGHT - BOTTOM_MARGIN) / NUM_ROWS) - (BLOCK_MARG_Y * 2);
    public static final int BLOCK_WIDTH = (GameCanvas.CANVAS_WIDTH / NUM_COLS) - (BLOCK_MARG_X * 2);
    public static final int COL_WIDTH    = BLOCK_WIDTH + (BLOCK_MARG_X * 2);
    public static final int ROW_HEIGHT    = BLOCK_HEIGHT + (BLOCK_MARG_Y * 2);


    private GameCanvas canvas;
    private Block[][] blocks = new Block[NUM_ROWS][NUM_COLS];

    public BlockManager(GameCanvas canvas) {
        this.canvas = canvas;
        canvas.getCanvas().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getPoint());
                Point collision = getCollision(e.getPoint());
                System.out.println(collision);
                if (collision != null) {
                    breakBlock(collision);
                }
            }
        });
    }

    /**
     * Loads an array of colors and draws them, assuming the canvas is already clear.
     * @param newBlocks The array of new blocks
     */
    public void loadBlocks(Block[][] newBlocks) {
        blocks = newBlocks;
        Graphics graphics = canvas.getGraphics();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                graphics.setColor(blocks[i][j].getColor());
                graphics.fillRect(
                        BLOCK_MARG_X + (j * COL_WIDTH),
                        BLOCK_MARG_Y + (i * ROW_HEIGHT),
                        BLOCK_WIDTH,
                        BLOCK_HEIGHT );
            }
        }
        canvas.repaint();
    }

    public void breakBlock(Point point) {
        if (!blocks[point.y][point.x].isBroken()) {
            if (blocks[point.y][point.x].collide()) {
                canvas.getGraphics().clearRect(
                        BLOCK_MARG_X + (point.x * COL_WIDTH),
                        BLOCK_MARG_Y + (point.y * ROW_HEIGHT),
                        BLOCK_WIDTH,
                        BLOCK_HEIGHT);
                canvas.repaint();
            }
        }
    }

    public Point getCollision(Point point) {
        int col = point.x / COL_WIDTH;
        int row = point.y / ROW_HEIGHT;

        int offx = point.x % COL_WIDTH;
        int offy = point.y % ROW_HEIGHT;
        if (    offx < (COL_WIDTH - BLOCK_MARG_X) &&
                offx > BLOCK_MARG_X &&
                offy < (ROW_HEIGHT - BLOCK_MARG_Y) &&
                offy > BLOCK_MARG_Y &&
                col  < NUM_COLS &&
                row  < NUM_ROWS  ) {
            return blocks[row][col].isBroken() ? null : new Point(col, row);
        } else {
            return null;
        }
    }


    @Override
    public void update(World world) {}
}
