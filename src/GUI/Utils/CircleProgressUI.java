package GUI.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CircleProgressUI extends BasicProgressBarUI {
    private Color progressBarColor;
    private Font font;
    private String format;

    public CircleProgressUI(Color progressBarColor) {
        this.progressBarColor = progressBarColor;
        this.format = null;
    }

    public CircleProgressUI(Color progressBarColor, String format, Font font) {
        this.progressBarColor = progressBarColor;
        this.format = format;
        this.font = font;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        int v = Math.max(d.width, d.height);
        d.setSize(v, v);
        return d;
    }

    private Area createCircle(Graphics2D g2, double percentComplete, Color color) {
        Insets b = progressBar.getInsets();
        int barRectWidth = progressBar.getWidth() - b.right - b.left;
        int barRectHeight = progressBar.getHeight() - b.top - b.bottom;

        if (barRectWidth <= 0 || barRectHeight <= 0)
            return null;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(color);

        // bruh, wtf is this
        double degree = 360 * percentComplete;
        double size = Math.min(barRectWidth, barRectHeight);
        double centerX = b.left + barRectWidth * 0.5;
        double centerY = b.top + barRectHeight * 0.5;
        double outerRadius = size * 0.5;
        double innerRadius = outerRadius * 0.96;

        Shape inner = new Ellipse2D.Double(centerX - innerRadius, centerY - innerRadius, innerRadius * 2,
                innerRadius * 2);
        Shape outer = new Arc2D.Double(centerX - outerRadius, centerY - outerRadius, size, size, 90 - degree, degree,
                Arc2D.PIE);

        Area area = new Area(outer);
        area.subtract(new Area(inner));

        return area;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();

        Insets b = progressBar.getInsets();
        int barRectWidth = progressBar.getWidth() - b.right - b.left;
        int barRectHeight = progressBar.getHeight() - b.top - b.bottom;

        Color bgCircleColor = new Color(progressBarColor.getRed(), progressBarColor.getGreen(),
                progressBarColor.getBlue(), 70);
        Area backgroundCircle = createCircle(g2, 100, bgCircleColor);
        g2.fill(backgroundCircle);

        Area completeProgress = createCircle(g2, progressBar.getPercentComplete(), progressBarColor);
        g2.fill(completeProgress);

        if (!progressBar.isStringPainted()) return;

        if (format == null) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
        } 
        else paintCustomString(g2, c);

        g2.dispose();
    }

    private void paintCustomString(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(progressBar.getForeground());
        g2.setFont(font);

        String s = String.format(format, progressBar.getPercentComplete() * 100, 100);

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(s);
        int textHeight = fm.getAscent();

        int centerX = progressBar.getWidth() / 2;
        int centerY = progressBar.getHeight() / 2;

        g2.drawString(s, centerX - textWidth / 2, centerY + textHeight / 4);
    }
}
