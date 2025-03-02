package GUI.Utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JProgressBar;

public class CircleProgressBar extends JProgressBar {
    public CircleProgressBar(Color progressBarColor) {
        setUI(new CircleProgressUI(progressBarColor));
    }

    public CircleProgressBar(Color progressBarColor, String format, Font font) {
        setUI(new CircleProgressUI(progressBarColor, format, font));
    }
}
