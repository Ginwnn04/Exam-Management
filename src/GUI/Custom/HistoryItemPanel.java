package GUI.Custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import BUS.TestBUS;
import DTO.ResultDTO;
import DTO.TestDTO;
import GUI.Comp.Panel.Result.PanelAfterExam;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.RoundBorder;
import style.ColorConfig;
import style.MyFont;

public class HistoryItemPanel extends PanelBackground {
    private RoundBorder border = new RoundBorder(Color.BLACK, 10);
    private final int WIDTH = 1100, HEIGHT = 200;

    private ResultDTO result;
    private TestDTO test;
    private Color resultColor;

    private ArrayList<Consumer<ResultDTO>> onActionCallbacks = new ArrayList<>();
    private boolean isPass;

    public HistoryItemPanel(ResultDTO result, TestDTO test, boolean isPass) {
        this.result = result;
        this.test = test;
        this.isPass = isPass;

        resultColor = isPass ? Color.GREEN : Color.RED;

        initComponents();
    }

    public void addActionCallback(Consumer<ResultDTO> callback) {
        onActionCallbacks.add(callback);
    }

    private void onAction() {
        for (var callback : onActionCallbacks) {
            callback.accept(result);
        }
    }

    private void initComponents() {
        setBorder(border);
        setAbsoluteSize(WIDTH, HEIGHT);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createRigidArea(new Dimension(50, HEIGHT - 20)));

        content = new PanelBackground();
        content.setAbsoluteSize(WIDTH - 150, HEIGHT - 20);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        addTitleAndExCode();
        addResultAndDateExam();
        addToDetailButton();

        add(content);
    }

    private void addTitleAndExCode() {
        int containerWidth = WIDTH - 150;

        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, 50);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        PanelBackground titleContainer = new PanelBackground();
        int titleContainerWidth = (int) (containerWidth * 0.6);
        titleContainer.setAbsoluteSize(titleContainerWidth, 40);
        titleContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel title = new JLabel("Đề thi: " + test.getTitle());
        title.setFont(MyFont.fontText.deriveFont(18f));        
        titleContainer.add(title);

        PanelBackground exCodeContainer = new PanelBackground();
        int exCodeContainerWidth = (int) (containerWidth * 0.3);
        exCodeContainer.setAbsoluteSize(exCodeContainerWidth, 40);
        exCodeContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        JLabel exCode = new JLabel("Mã đề: " + result.getExCode());
        exCode.setFont(MyFont.fontText.deriveFont(18f));
        exCode.setAlignmentX(RIGHT_ALIGNMENT);

        exCodeContainer.add(exCode);

        container.add(titleContainer);
        container.add(exCodeContainer);

        content.add(container);
    }

    private void addResultAndDateExam() {
        int containerWidth = WIDTH - 150;

        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, 50);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        PanelBackground resultContainer = new PanelBackground();
        int resultContainerWidth = (int) (containerWidth * 0.6);
        resultContainer.setAbsoluteSize(resultContainerWidth, 40);
        resultContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        String resultText = isPass ? "Đạt" : "Không đạt";
        
        JLabel resultLabel = new JLabel("Kết quả: " + resultText);
        resultLabel.setFont(MyFont.fontText.deriveFont(18f));
        resultLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        resultContainer.add(resultLabel);

        PanelBackground dateExamContainer = new PanelBackground();
        int dateExamContainerWidth = (int) (containerWidth * 0.3);
        dateExamContainer.setAbsoluteSize(dateExamContainerWidth, 40);
        dateExamContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        JLabel dateExam = new JLabel("Ngày thi: " + result.getRsDate());
        dateExam.setFont(MyFont.fontText.deriveFont(18f));

        dateExamContainer.add(dateExam);

        container.add(resultContainer);
        container.add(dateExamContainer);

        content.add(container);
    }

    private void addToDetailButton() {
        int containerWidth = (int) (WIDTH * 0.8);

        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, 60);
        container.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton toDetail = new JButton("Xem chi tiết kết quả bài làm");
        toDetail.setBackground(ColorConfig.BLUE);
        toDetail.setFont(MyFont.fontText);
        toDetail.setForeground(Color.WHITE);
        toDetail.addActionListener(e -> onAction());

        container.add(toDetail);

        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(container);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = 50;
        int height = getHeight();
        int radius = border.getRadius();
        
        int x = 0, y = 0;

        g.setColor(resultColor);
        g.fillRect(x, y, width, height);

        g.setColor(Color.WHITE);
        g.fillRect(x, y, radius, radius);
        g.fillRect(x, height - radius, radius, radius);

        fillTopLeft(g);
        fillBottomLeft(g);
    }

    private void fillTopLeft(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int radius = border.getRadius();
        int x = 0, y = 0;

        g2d.setColor(resultColor);
        Path2D path = new Path2D.Double();
        path.moveTo(x + radius, y + radius);
        path.lineTo(x + radius * 2, y + radius);
        path.append(new Arc2D.Double(x, y, 2 * radius, 2 * radius, 90, 90, Arc2D.OPEN), true); // Append the arc
        path.closePath();

        g2d.fill(path);
    }

    private void fillBottomLeft(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int radius = border.getRadius();
        int x = 0, y = 0;
        int height = getHeight();

        g2d.setColor(resultColor);
        Path2D path = new Path2D.Double();
        path.moveTo(x + radius, height - radius);
        path.lineTo(x + radius * 2, height - radius);
        path.append(new Arc2D.Double(x, y + height - 2 * radius - 1, 2 * radius, 2 * radius, 180, 90, Arc2D.OPEN), true); // Append the arc
        path.closePath();

        g2d.fill(path);
    }

    private PanelBackground content;
}
