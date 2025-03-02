package GUI.Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundBorder implements Border {
    private final Color borderColor;
    private final int radius;

    /**
     * Flag for top left corner
     */
    public boolean TOP_LEFT = true;

    /**
     * Flag for bottom left corner
     */
    public boolean BOTTOM_LEFT = true;

    /**
     * Flag for top right corner
     */
    public boolean TOP_RIGHT = true;

    /**
     * Flag for bottom right corner
     */
    public boolean BOTTOM_RIGHT = true;

    public RoundBorder(Color borderColor, int radius){
        this.borderColor = borderColor;
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 2, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(borderColor);

        if (TOP_LEFT) drawTopLeftCorner(g, x, y);
        if (TOP_RIGHT) drawTopRightCorner(g, x, y, width);
        if (BOTTOM_LEFT) drawBottomLeftCorner(g, x, y, height);
        if (BOTTOM_RIGHT) drawBottomRightCorner(g, x, y, width, height);

        drawTopLine(g, x, y, width);
        drawLeftLine(g, x, y, height);
        drawRightLine(g, x, y, width, height);
        drawBottomLine(g, x, y, width, height);
    }

    private void drawTopLine(Graphics g, int x, int y, int width) {
        x = TOP_LEFT ? x + radius : x;
        int x2 = TOP_RIGHT ? x + width - radius * 2 : x + width - 1;

        x2 += TOP_LEFT ? 0 : radius;

        g.drawLine(x, y, x2, y);
    }

    private void drawLeftLine(Graphics g, int x, int y, int height) {
        y = TOP_LEFT ? y + radius : y;
        int y2 = BOTTOM_LEFT ? y + height - radius * 2 : y + height - 1;

        y2 += TOP_LEFT ? 0 : radius;
        
        g.drawLine(x, y, x, y2);
    }

    private void drawBottomLine(Graphics g, int x, int y, int width, int height) {
        x = BOTTOM_LEFT ? x + radius : x;
        int x2 = BOTTOM_RIGHT ? x + width - radius * 2 : x + width - 1;

        x2 += BOTTOM_LEFT ? 0 : radius;

        g.drawLine(x, y + height - 1, x2, y + height - 1);
    }

    private void drawRightLine(Graphics g, int x, int y, int width, int height) {
        y = TOP_RIGHT ? y + radius : y;
        int y2 = BOTTOM_RIGHT ? y + height - radius * 2 : y + height - 1;

        y2 += TOP_RIGHT ? 0 : radius;

        g.drawLine(x + width - 1, y, x + width - 1, y2);
    }

    // bruh dont ask me math, ask internet

    private void drawTopLeftCorner(Graphics g, int x, int y) {
        g.drawArc(x, y, 2 * radius, 2 * radius, 90, 90);
    }

    private void drawBottomLeftCorner(Graphics g, int x, int y, int height) {
        g.drawArc(x, y + height - 2 * radius - 1, 2 * radius, 2 * radius, 180, 90);
    }

    private void drawTopRightCorner(Graphics g, int x, int y, int width) {
        g.drawArc(x + width - 2 * radius - 1, y, 2 * radius, 2 * radius, 0, 90);
    }

    private void drawBottomRightCorner(Graphics g, int x, int y, int width, int height) {
        g.drawArc(x + width - 2 * radius - 1, y + height - 2 * radius - 1, 2 * radius, 2 * radius, 270, 90);
    }
}
