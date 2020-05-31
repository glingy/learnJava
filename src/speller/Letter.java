package speller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

public class Letter extends Entity {
    public char c;

    public Letter(int x, int y, char c) {
        rect = new Rectangle2D.Double(x, y, 50, 50);
        this.c = c;
    }

    public void fall() {
        ((Rectangle2D.Double) rect).y += 2;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        Font font = new Font("Menlo", Font.BOLD, 60);
        GlyphVector vector =font.createGlyphVector(g.getFontRenderContext(), String.valueOf(c));
        Rectangle2D bounds = vector.getPixelBounds(g.getFontRenderContext(), 50, 50); // If I were to draw it at 50, 50, what would the bounds of the letter be? (For centering purposes)...
        //System.out.println(bounds);
        g.setFont(font);
        float x = (float) (rect.getX() - bounds.getX() + 50.5);
        float y = (float) (rect.getY() - (bounds.getY() + bounds.getHeight()) + 50.5 + bounds.getHeight());

        g.drawGlyphVector(vector, x, y);

        ((Rectangle2D.Double) rect).width = bounds.getWidth();
        ((Rectangle2D.Double) rect).height = bounds.getHeight();

        //g.setColor(Color.RED);
        //g.draw(rect);
    }
}
