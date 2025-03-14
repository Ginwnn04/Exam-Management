package GUI.Utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JProgressBar;

public class CircleProgressBar extends JProgressBar {
    private CircleProgressUI circleProgressUI;

    public CircleProgressBar(Color progressBarColor) {
        circleProgressUI = new CircleProgressUI(progressBarColor);
        setUI(circleProgressUI);
    }

    public CircleProgressBar(Color progressBarColor, Font font, int maxValue) {
        circleProgressUI = new CircleProgressUI(progressBarColor, font, maxValue);
        setUI(circleProgressUI);
    }

    public void setMaxValue() {

    }
}
