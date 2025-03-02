package GUI.Comp.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.ExamBUS;
import BUS.ResultBUS;
import BUS.TestExamBUS;
import BUS.UserBus;
import DTO.ExamDTO;
import DTO.ResultDTO;
import DTO.TestExamDTO;
import DTO.UserDTO;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.CircleProgressBar;
import GUI.Utils.RoundBorder;
import style.MyFont;

public class PanelAfterExam extends JPanel {
    private final int WIDTH = 1160;

    private Font resultItemLabelFont = MyFont.fontHeader.deriveFont(20f);
    private Font resultItemValueFont = MyFont.fontText.deriveFont(20f);

    private ResultBUS resultBUS = new ResultBUS();
    private UserBus userBUS = new UserBus();
    private ExamBUS examBUS = new ExamBUS();
    private TestExamBUS testExamBUS = new TestExamBUS();

    private UserDTO student;
    private ExamDTO exam;
    private TestExamDTO test;
    private ResultDTO result;

    public PanelAfterExam() {
        initComponents();
    }

    public PanelAfterExam(ResultDTO result) {
        this.result = result;
        student = userBUS.findByID(result.getUserId());
        exam = examBUS.findByExCode(result.getExCode());
        test = testExamBUS.findByTestCode(exam.getTestCode());

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200, 765));

        main = new PanelBackground();
        main.setPreferredSize(new Dimension(WIDTH, 725));
        main.setLayout(new FlowLayout());

        content = new PanelBackground();
        content.setPreferredSize(new Dimension(WIDTH, 725));
        content.setBorder(new EmptyBorder(20, 0, 10, 0));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        initTitle();
        initResult();
        initButtonContainer();

        main.add(content);
        add(main);
    }

    private void initTitle() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 95);
        container.setLayout(new GridBagLayout());
        container.setBackground(new Color(225, 99, 73));
        container.setRound(0);

        Font font = MyFont.fontHeader.deriveFont(30f);

        JLabel title = new JLabel("Bạn đã hoàn thành bài thi");
        title.setForeground(Color.WHITE);
        title.setFont(font);

        container.add(title);
        content.add(container);
    }

    private void initResult() {
        var container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 409);
        container.setLayout(new GridBagLayout());

        resultContainer = new PanelBackground();
        resultContainer.setAbsoluteSize(1082, 409);
        resultContainer.setLayout(new BoxLayout(resultContainer, BoxLayout.X_AXIS));

        fetchResultInformation();

        resultContainer.add(initScoreCircle());
        resultContainer.add(initResultInformation());

        container.add(resultContainer);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(container);
    }

    private PanelBackground initScoreCircle() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(445, 409);
        container.setLayout(new GridBagLayout());

        RoundBorder border = new RoundBorder(Color.BLACK, 10);
        border.TOP_RIGHT = false;
        border.BOTTOM_RIGHT = false;

        container.setBorder(border);

        Font font = MyFont.fontText.deriveFont(30f);

        circleProgressBar = new CircleProgressBar(new Color(225, 99, 73),  "%.2f / %d", font);
        circleProgressBar.setStringPainted(true);
        circleProgressBar.setValue(result.getRsMark());
        circleProgressBar.setForeground(Color.BLACK);
        circleProgressBar.setPreferredSize(new Dimension(210, 210));
        circleProgressBar.setBackground(new Color(0, 0, 0, 0));

        container.add(circleProgressBar);

        return container;
    }

    private PanelBackground initResultInformation() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(637, 409);
        container.setLayout(new GridBagLayout());

        RoundBorder border = new RoundBorder(Color.BLACK, 10);
        border.TOP_LEFT = false;
        border.BOTTOM_LEFT = false;
        container.setBorder(border);

        PanelBackground resultInformation = new PanelBackground();
        resultInformation.setAbsoluteSize(607, 160);
        resultInformation.setLayout(new BoxLayout(resultInformation, BoxLayout.Y_AXIS));

        resultInformation.add(initResultInformationItem("Mã đề thi: ", exCodeField));
        resultInformation.add(initResultInformationItem("Đề thi: ", examTitle));
        resultInformation.add(initResultInformationItem("Thời gian làm bài: ", examTime));
        resultInformation.add(initResultInformationItem("Số lượt thi: ", takeExamTime));
        resultInformation.add(initResultInformationItem("Ngày thi: ", examDate));
        container.add(resultInformation);

        return container;
    }

    // modify later
    private void fetchResultInformation() {
        int studentTakeExamTime = resultBUS.getTakeExamTime(student, test);
        int maxTakeExamTime = test.getTestLimit();
        
        exCodeField = new JLabel(result.getExCode());
        examTitle = new JLabel(test.getTitle());
        examTime = new JLabel(String.valueOf(test.getTestTime()) + " phút");
        takeExamTime = new JLabel(String.format("%d / %d lượt thi", studentTakeExamTime, maxTakeExamTime));
        examDate = new JLabel(result.getRsDate().toString());
    }

    private PanelBackground initResultInformationItem(String labelText, JLabel value) {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(607, 30);
        container.setAlignmentX(LEFT_ALIGNMENT);
        container.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(labelText);
        label.setFont(resultItemLabelFont);

        value.setFont(resultItemValueFont);

        container.add(label);
        container.add(value);

        return container;
    }

    private void initButtonContainer() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 100);
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));

        againButton = new JButton("Thi lại");
        againButton.setBackground(new Color(225, 99, 73));
        againButton.setFont(MyFont.fontHeader.deriveFont(20f));
        againButton.setForeground(new Color(255, 255, 255));
        againButton.setPreferredSize(new Dimension(150, 50));

        container.add(againButton);

        homeButton = new JButton("Quay về trang chủ");
        homeButton.setBackground(new Color(225, 99, 73));
        homeButton.setFont(MyFont.fontHeader.deriveFont(20f));
        homeButton.setForeground(new Color(255, 255, 255));
        homeButton.setPreferredSize(new Dimension(222, 50));

        container.add(homeButton);

        content.add(Box.createRigidArea(new Dimension(0, 73)));
        content.add(container);
    }

    private PanelBackground main;
    private PanelBackground content;
    private PanelBackground resultContainer;
    private JLabel exCodeField;
    private JLabel examTitle;
    private JLabel examTime;
    private JLabel takeExamTime;
    private JLabel examDate;
    private CircleProgressBar circleProgressBar;
    private JButton againButton;
    private JButton homeButton;
}
