package speller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

public class World {
    private GameCanvas canvas = new GameCanvas("Letter Catcher!");
    private ArrayList<Letter> letters = new ArrayList<>();
    private Bucket bucket = new Bucket(100, 520);
    private String str = "H";

    private int counter = 0; // the number of frames before we add another letter to the screen
    private void resetLetterCounter() {
        counter = (int) (Math.random() * 100);
    }

    public void update() {
        clearCanvas();
        updateLetters();
        if (counter-- == 0) {
            resetLetterCounter();
            addRandomLetter();
        }
        bucket.move();
        bucket.draw(canvas.getGraphics());
        drawString();
        canvas.repaint();
    }

    private void drawString() {
        canvas.getGraphics().setColor(Color.BLACK);
        canvas.getGraphics().setFont(new Font("Menlo", Font.PLAIN, 20));
        canvas.getGraphics().drawString(str, 5, GameCanvas.CANVAS_HEIGHT - 5);
    }

    private void clearCanvas() {
        canvas.getGraphics().setColor(new Color(0xD2F0FF));
        canvas.getGraphics().fillRect(0, 0, GameCanvas.CANVAS_WIDTH, GameCanvas.CANVAS_HEIGHT);
    }

    private void addRandomLetter() {
        Letter random = new Letter(
                (int) (Math.random() * (GameCanvas.CANVAS_WIDTH - 50)), // why - 50?
                0,
                (char) ((Math.random() * 26) + 'A')
        );
        letters.add(random);
    }

    private void updateLetters() {
        for (int i = 0; i < letters.size(); i++) { // why not for-each?
            Letter letter = letters.get(i);
            letter.fall();
            if (letter.rect.getMaxY() > bucket.rect.getMinY() &&
                    letter.rect.getMaxY() < bucket.getMinEntryY() &&
                    letter.rect.getMinX() > bucket.rect.getMinX() &&
                    letter.rect.getMaxX() < bucket.rect.getMaxX()) {
                letters.remove(letter);
                //noinspection StringConcatenationInLoop
                str += letter.c;
                i--; // why?
            } else if (letter.rect.getMinY() > GameCanvas.CANVAS_HEIGHT) {
                letters.remove(letter);
                i--;
            }
            letter.draw(canvas.getGraphics());
        }
    }

    public World() {
        canvas.getWindow().addKeyListener(bucket);
    }

    public void startLevel() {
        new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        }).start();
    }
}
