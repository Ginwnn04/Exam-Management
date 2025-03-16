package GUI.Comp.Dialog.Statistics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import BUS.ExamBUS;
import BUS.TestBUS;
import DTO.ExamDTO;
import DTO.ResultDTO;
import DTO.TestDTO;
import Enum.TestResultEnum;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.RoundBorder;
import style.ColorConfig;
import style.MyFont;

public class PanelChart extends PanelBackground {
    private final int WIDTH = 1180;
    private ArrayList<BiConsumer<PanelBackground, PanelBackground>> onChangeTabCallback = new ArrayList<>();

    private TestDTO testExam;
    private List<ResultDTO> listResult;

    private ExamBUS examBUS = new ExamBUS();

    // for filter user that take more than one exam (take the highest mark one)
    private HashMap<Integer, ResultDTO> resultData = new HashMap<>();
    private int passCount = 0, failCount = 0;

    public PanelChart(TestDTO testExam, List<ResultDTO> listResult) {
        this.listResult = listResult;
        this.testExam = testExam;
        
        prepareData();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addListStudentButton();
        addTitle();
        addDateLabel();
        addTestInformation();
        addChartsContainer();
    }

    private void addListStudentButton() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 50);
        container.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton toListStudentButton = new JButton("Danh sách học sinh làm bài");
        toListStudentButton.setPreferredSize(new Dimension(226, 44));
        toListStudentButton.setBackground(ColorConfig.BLUE);
        toListStudentButton.setForeground(Color.WHITE);
        toListStudentButton.setAlignmentX(RIGHT_ALIGNMENT);
        toListStudentButton.addActionListener(this::OnChangeTab);

        container.add(toListStudentButton);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(container);
    }

    private void addTitle() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 40);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Thống kê đề thi:");
        label.setFont(MyFont.fontHeader.deriveFont(24f));

        JLabel title = new JLabel(testExam.getTitle());
        title.setFont(MyFont.fontHeader.deriveFont(24f));
        title.setForeground(ColorConfig.BLUE);
        
        container.add(label);
        container.add(title);
        add(container);
    }

    private void addDateLabel() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 30);
        container.setLayout(new GridBagLayout());

        JLabel date = new JLabel("Ngày thi: " + testExam.getTestDate());
        date.setFont(MyFont.fontText.deriveFont(16f));

        container.add(date);
        add(container);
    }

    private void addTestInformation() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(728, 135);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        RoundBorder border = new RoundBorder(new Color(0xC1C1C1), 10);
        container.setBorder(border);

        PanelBackground boxLeft = new PanelBackground();
        boxLeft.setAbsoluteSize(340, 100);
        boxLeft.setLayout(new BoxLayout(boxLeft, BoxLayout.Y_AXIS));

        PanelBackground boxRight = new PanelBackground();
        boxRight.setAbsoluteSize(300, 100);
        boxRight.setLayout(new BoxLayout(boxRight, BoxLayout.Y_AXIS));

        // test code
        Label testCode = new Label("Mã đề thi: " + testExam.getTestCode());
        testCode.setFont(MyFont.fontText.deriveFont(16f).deriveFont(Font.PLAIN));

        // student count
        int studentCount = resultData.keySet().size();
        Label studentNum = new Label(String.format("Số học sinh thi: %d học sinh", studentCount));
        studentNum.setFont(MyFont.fontText.deriveFont(16f));

        // exam count
        int examCount = examBUS.getExamCount(testExam.getTestCode());
        Label testNum = new Label(String.format("Số mã đề thi: %d mã đề", examCount));
        testNum.setFont(MyFont.fontText.deriveFont(16f));
        testNum.setAlignment(Label.RIGHT);

        // exam time
        Label testTime = new Label(String.format("Thời gian thi: %d phút", testExam.getTestTime()));
        testTime.setFont(MyFont.fontText.deriveFont(16f));
        testTime.setAlignment(Label.RIGHT);

        boxLeft.add(testCode);
        boxLeft.add(studentNum);
        boxRight.add(testNum);
        boxRight.add(testTime);

        container.add(boxLeft);
        container.add(boxRight);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(container);
    }

    private void addChartsContainer() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(1028, 450);
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 23, 0));

        container.add(getBarChart());
        container.add(getPieChart());

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(container);
    }

    private void prepareData() {
        for (var item : listResult) {
            int userId = item.getUserId();

            if (!resultData.containsKey(userId)) {
                resultData.put(userId, item);
                continue;
            }

            var result = resultData.get(userId);
            if (result.getRsMark() < item.getRsMark()) resultData.replace(userId, item);
        }

        analyzeResults();
    }

    private void analyzeResults() {
        for (var userId : resultData.keySet()) {
            var result = resultData.get(userId);

            if (result.getRsMark() >= 50) passCount++;
            else failCount++;
        }
    }

    private DefaultCategoryDataset getBarChartData() {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        
        data.addValue(passCount, "", TestResultEnum.PASS.getDisplayText());
        data.addValue(failCount, "", TestResultEnum.FAIL.getDisplayText());

        return data;
    }

    private DefaultPieDataset<String> getPieChartData() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue(TestResultEnum.PASS.getDisplayText(), passCount);
        data.setValue(TestResultEnum.FAIL.getDisplayText(), failCount);

        return data;
    }

    private PanelBackground getBarChart() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(489, 424);
        container.setLayout(new GridBagLayout());

        RoundBorder border = new RoundBorder(new Color(0xC1C1C1), 30);
        container.setBorder(border);

        JFreeChart chart = ChartFactory.createBarChart(
            "",
            "", 
            "Số học sinh", 
            getBarChartData());

        styleBarChart(chart);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setMinimumSize(new Dimension(400, 400));

        container.add(panel);
        container.validate();

        return container;
    }

    private void styleBarChart(JFreeChart chart) {
        Color lightGray = new Color(0xC1C1C1);
        
        chart.removeLegend();

        CategoryPlot cplot = (CategoryPlot) chart.getPlot();
        cplot.setBackgroundPaint(Color.WHITE);
        cplot.setRangeGridlinePaint(lightGray);

        var yAxis = cplot.getRangeAxis();
        yAxis.setAxisLinePaint(Color.white);
        yAxis.setTickMarkPaint(Color.white);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        var xAxis = cplot.getDomainAxis();
        xAxis.setAxisLinePaint(Color.WHITE);
        xAxis.setTickMarkPaint(Color.WHITE);

        BarRenderer renderer = (BarRenderer) cplot.getRenderer();
        renderer.setSeriesPaint(0, ColorConfig.BLUE);
        renderer.setMaximumBarWidth(0.1);
    }

    private PanelBackground getPieChart() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(489, 424);
        container.setLayout(new GridBagLayout());

        RoundBorder border = new RoundBorder(new Color(0xC1C1C1), 30);
        container.setBorder(border);
        
        JFreeChart chart = ChartFactory.createPieChart("", getPieChartData());
        stylePieChart(chart);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setMinimumSize(new Dimension(400, 400));

        container.add(panel);
        container.validate();

        return container;
    }

    @SuppressWarnings("unchecked")
    private void stylePieChart(JFreeChart chart) {
        chart.setBorderVisible(false);
        
        PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelGenerator(null);

        plot.setSectionPaint(TestResultEnum.PASS.getDisplayText(), ColorConfig.BLUE);
        plot.setSectionPaint(TestResultEnum.FAIL.getDisplayText(), Color.RED);
    }

    private void OnChangeTab(ActionEvent e) {
        for (var callback : onChangeTabCallback) {
            callback.accept(this, null);
        }
    }

    public void addOnChangeTabCallback(BiConsumer<PanelBackground, PanelBackground> callback) {
        onChangeTabCallback.add(callback);
    }
}
