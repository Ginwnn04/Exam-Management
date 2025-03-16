package GUI.Comp.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.QuestionBUS;
import BUS.TestBUS;
import BUS.TestStructureBUS;
import BUS.TopicBUS;
import DTO.QuestionDTO;
import DTO.TestDTO;
import DTO.TestStructureDTO;
import DTO.TopicDTO;
import Enum.DialogType;
import Exceptions.DuplicateTopicException;
import Exceptions.EmptyQuestionsException;
import GUI.Comp.DateChooser.DateChooser;
import GUI.Comp.DateChooser.SelectedDate;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.TestStructurePanel;
import GUI.Utils.Pair;
import GUI.Utils.RandomCode;
import GUI.Utils.RoundBorder;
import style.ColorConfig;
import style.MyFont;

public class DialogTest extends JDialog {
    private DialogType dialogType;
    private int selectedTestExamId;

    private TestBUS BUS;
    private TopicBUS topicBUS = new TopicBUS();
    private TestStructureBUS testStructureBUS = new TestStructureBUS();
    private QuestionBUS questionBUS = new QuestionBUS();

    private ArrayList<TestStructurePanel> testStructurePanels = new ArrayList<>();
    private List<TopicDTO> topics;

    private final int WIDTH = 1180;

    /**
     * For create
     */
    public DialogTest(TestBUS BUS, JFrame parent) {
        super(parent, "Tạo cấu trúc đề thi", true);

        dialogType = DialogType.Create;
        this.BUS = BUS;

        setupDialog();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * For update
     * @param id id of the test exam
     */
    public DialogTest(int id, TestBUS BUS, JFrame parent, DialogType type) {
        super(parent, type == DialogType.Update ? "Cập nhật cấu trúc đề thi" : "Xem cấu trúc đề thi", true);

        dialogType = type;

        this.BUS = BUS;
        selectedTestExamId = id;

        setupDialog();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void setupDialog() {
        initComponents();

        switch (dialogType) {
            case Create:
                setupCreateDialog();
                break;

            case Update:
                setupUpdateDialog();
                setModel();
                break;

            case View:
                setupViewDialog();
                setModel();
                break;
        
            default:
                break;
        }
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(1200, 765));
        setPreferredSize(new Dimension(1200, 765));

        topics = topicBUS.getAllTopic();

        main = new PanelBackground();
        main.setPreferredSize(getPreferredSize());
        main.setLayout(new BorderLayout());

        content = new PanelBackground();
        content.setAbsoluteSize(WIDTH, 765);
        content.setBorder(new EmptyBorder(0, 0, 0, 15));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        initIdLabel();
        initTitleAndTestCode();
        initInformationPanel();
        initTestStructureContainer();
        initSaveButton();

        addScroll();

        int fixedHeightValue = dialogType != DialogType.Create ? 0 : -20;
        fixedContentHeight(fixedHeightValue);

        if (dialogType != DialogType.Create) setModel();

        add(main);
    }

    private void addScroll() {
        scrollablePanel = new JScrollPane();
        scrollablePanel.setViewportView(content);
        scrollablePanel.setMinimumSize(new Dimension(1200, 765));
        scrollablePanel.getVerticalScrollBar().setUnitIncrement(10);
        scrollablePanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        main.add(scrollablePanel, BorderLayout.CENTER);
    }

    private void fixedContentHeight(int value) {
        int height = (int) content.getPreferredSize().getHeight() + value;
        content.setAbsoluteSize(WIDTH, height);

        content.validate();
        content.repaint();
        scrollablePanel.validate();
        scrollablePanel.repaint();
    }

    //#region Update Model

    private void initIdLabel() {
        if (dialogType != DialogType.Create) return;

        content.add(Box.createRigidArea(new Dimension(0, 20)));

        PanelBackground container = new PanelBackground();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setAbsoluteSize(WIDTH, 30);
        container.setBorder(new EmptyBorder(0, 10, 0, 10));

        idLabel = new JLabel();
        idLabel.setFont(MyFont.fontText.deriveFont(16f));
        idLabel.setText("ID cấu trúc đề thi: " + selectedTestExamId);

        container.add(idLabel);

        content.add(container);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void setModel() {
        TestDTO model = BUS.findById(selectedTestExamId);
        if (model == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy cấu trúc đề thi", "Lỗi", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        // var currentTopicOpt = topics.stream().filter(topic -> topic.getId() == model.getTopicId()).findFirst();

        examTitle.setText(model.getTitle());
        testCodeLabel.setText(model.getTestCode());
        testLimit.setText(String.valueOf(model.getTestLimit()));
        time.setText(String.valueOf(model.getTestTime()));

        var date = model.getTestDate().toLocalDate();
        SelectedDate testDate = new SelectedDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        testDateChooser.setSelectedDate(testDate);

        addTestStructuresModelItems(model);
    }

    public void addTestStructuresModelItems(TestDTO model) {
        var list = testStructureBUS.getAllByTestCode(model.getTestCode());

        for (var testStructure : list) {
            addTestStructure(testStructure);
        }
    }

    //#endregion

    private void initTitleAndTestCode() {
        titleAndTestCodeContainer = new PanelBackground();
        titleAndTestCodeContainer.setLayout(new FlowLayout());
        titleAndTestCodeContainer.setAbsoluteSize(WIDTH, 60);
        titleAndTestCodeContainer.setBorder(new EmptyBorder(0, 10, 0, 10));

        initExamTitle();
        titleAndTestCodeContainer.add(Box.createRigidArea(new Dimension(10, 0)));
        initExamTestCode();

        content.add(titleAndTestCodeContainer);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    private void initExamTitle() {
        examTitle = new JTextField();
        examTitle.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tiêu đề");
        examTitle.setPreferredSize(new Dimension(880, 50));
        
        titleAndTestCodeContainer.add(examTitle);
    }

    private void initExamTestCode() {
        testCodeLabel = new JLabel();
        testCodeLabel.setPreferredSize(new Dimension(200, 50));

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Mã đề");
        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));
        testCodeLabel.setBorder(titleBorder);

        String code = RandomCode.generate(3);
        testCodeLabel.setText(code);
        testCodeLabel.setHorizontalAlignment(JLabel.CENTER);

        titleAndTestCodeContainer.add(testCodeLabel);
    }

    private void initInformationPanel() {
        informationPanel = new PanelBackground();
        informationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        informationPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        informationPanel.setAbsoluteSize(900, 280);

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Thông tin bài thi: ");
        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));

        informationPanel.setBorder(titleBorder);


        initExamInformation();
        initTestDateChooser();
        content.add(informationPanel);
    }

    private PanelBackground addExamInformationItem(String label, JComponent component) {
        var container = new PanelBackground();
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        container.setAbsoluteSize(270, 50);

        container.add(new JLabel(label));
        container.add(component);
        container.setAlignmentX(RIGHT_ALIGNMENT);

        return container;
    }

    private void initExamInformation() {
        var container = new PanelBackground();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAbsoluteSize(360, 205);

        int height = 30;

        container.add(Box.createRigidArea(new Dimension(0, 20)));

        time = new JTextField();
        time.setPreferredSize(new Dimension(70, height));;
        JLabel temp = new JLabel(" phút");
        temp.setFont(MyFont.fontText);
        var timeContainer = addExamInformationItem("Thời gian thi:", time);
        timeContainer.add(temp);
        container.add(timeContainer);

        testLimit = new JTextField();
        testLimit.setPreferredSize(new Dimension(70, height));;
        container.add(addExamInformationItem("Số lượt thi:", testLimit));

        Integer[] examCountData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        examCount = new JComboBox<>(examCountData);
        examCount.setFont(MyFont.fontText);
        examCount.setPreferredSize(new Dimension(70, height));;

        container.add(addExamInformationItem("Số đề thi:", examCount));

        informationPanel.add(container);
    }

    private void initTestDateChooser() {
        var dateContainer = new PanelBackground();
        dateContainer.setLayout(new BoxLayout(dateContainer, BoxLayout.Y_AXIS));
        dateContainer.setAbsoluteSize(500, 225);

        PanelBackground labelContainer = new PanelBackground();
        labelContainer.setAbsoluteSize(500, 30);

        JLabel label = new JLabel("Ngày thi:");
        label.setHorizontalAlignment(JLabel.LEFT);

        labelContainer.add(label);

        testDateChooser = new DateChooser();
        testDateChooser.setMaximumSize(new Dimension(260, 215));
        testDateChooser.setAlignmentX(CENTER_ALIGNMENT);

        dateContainer.add(labelContainer);
        dateContainer.add(testDateChooser);

        informationPanel.add(dateContainer);
    }

    private void addTestStructure() {
        TestStructurePanel testStructurePanel = new TestStructurePanel(topics, testCodeLabel.getText(), questionBUS);
        testStructurePanel.addOnDeleteCallback(this::deleteTestStructure);

        int height = (int) testStructureContainer.getPreferredSize().getHeight() + 210;
        testStructureContainer.setAbsoluteSize(WIDTH, height);

        testStructurePanels.add(testStructurePanel);
        testStructureContainer.add(testStructurePanel);
        
        testStructureContainer.validate();
        testStructureContainer.repaint();

        if (testStructurePanels.size() > 1) fixedContentHeight(210);
    }

    private void addTestStructure(TestStructureDTO model) {
        TestStructurePanel testStructurePanel = new TestStructurePanel(topics, model.getTestCode(), questionBUS);
        testStructurePanel.setModel(model);

        boolean canEdit = dialogType == DialogType.Create;
        testStructurePanel.setEditable(canEdit);

        int height = (int) testStructureContainer.getPreferredSize().getHeight() + 210;
        testStructureContainer.setAbsoluteSize(WIDTH, height);

        testStructurePanels.add(testStructurePanel);
        testStructureContainer.add(testStructurePanel);
        
        testStructureContainer.validate();
        testStructureContainer.repaint();

        if (testStructurePanels.size() > 1) fixedContentHeight(210);
    }

    private void deleteTestStructure(TestStructurePanel panel) {
        if (testStructurePanels.size() <= 1) return;

        int height = (int) testStructureContainer.getPreferredSize().getHeight() - 210;
        testStructureContainer.setAbsoluteSize(WIDTH, height);

        testStructurePanels.remove(panel);
        testStructureContainer.remove(panel);
        
        testStructureContainer.validate();
        testStructureContainer.repaint();

        fixedContentHeight(-210);
    }

    private void initTestStructureContainer() {
        testStructureContainer = new PanelBackground();
        testStructureContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        testStructureContainer.setAbsoluteSize(WIDTH, 40);

        JLabel label = new JLabel("Cấu trúc đề thi");
        label.setFont(MyFont.fontHeader);
        PanelBackground labelContainer = new PanelBackground();
        labelContainer.setAbsoluteSize(WIDTH, 30);
        labelContainer.add(label);

        testStructureContainer.add(labelContainer);

        content.add(testStructureContainer);
    }

    private void initAddNewTestStructureButton() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 40);
        container.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("+ Thêm cấu trúc");
        addButton.setBackground(ColorConfig.BLUE);
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addTestStructure());

        container.add(addButton);
        content.add(container);
    }

    private void initSaveButton() {
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        saveButton = new JButton();
        saveButton.setFont(new Font("Roboto", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setForeground(Color.white);
        saveButton.setBackground(Color.green);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        saveButton.addActionListener(this::onSave);

        content.add(saveButton);
    }

    private void setupCreateDialog() {
        addTestStructure();
        initAddNewTestStructureButton();
        saveButton.setText("Tạo đề thi");
    }

    private void setupUpdateDialog() {
        saveButton.setText("Lưu");
        examCount.setEnabled(false);
    }

    private void setupViewDialog() {
        saveButton.setVisible(false);
        examTitle.setEnabled(false);
        testLimit.setEnabled(false);
        time.setEnabled(false);
        testDateChooser.setEnabled(false);
        examCount.setEnabled(false);
    }

    private void onSave(ActionEvent e) {
        var check = canSave();

        if (!check.getFirst()) {
            JOptionPane.showMessageDialog(this, check.getLast(), "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TestDTO data = gatherFormData();

        List<TestStructureDTO> listTestStructure = getTestStructureList();
        if (listTestStructure == null) return;

        String action = dialogType == DialogType.Create ? "Tạo" : "Cập nhật";
        var result = dialogType == DialogType.Create ? create(data, listTestStructure) != null : update(data);

        if (result) JOptionPane.showMessageDialog(this, action + " thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(this, action + " thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);

        this.dispose();
    }

    private List<TestStructureDTO> getTestStructureList() {
        List<TestStructureDTO> listTestStructure;

        try {
            listTestStructure = gatherTestStructureListData();
        }
        catch (EmptyQuestionsException ignore) {
            JOptionPane.showMessageDialog(this, 
                                  "Không có câu hỏi trong cấu trúc này! Hãy thiết lập số câu hỏi", 
                                    "Thông báo", 
                                          JOptionPane.ERROR_MESSAGE);

            return null;
        }
        catch (DuplicateTopicException ignore) {
            JOptionPane.showMessageDialog(this, 
                                  "1 Chủ đề chỉ được xuất hiện 1 lần", 
                                    "Thông báo", 
                                          JOptionPane.ERROR_MESSAGE);

            return null;
        }

        return listTestStructure;
    }

    private TestDTO create(TestDTO data, List<TestStructureDTO> listTestStructure) {
        return BUS.create(data, (int) examCount.getSelectedItem(), listTestStructure);
    }

    private boolean update(TestDTO data) {
        return BUS.update(selectedTestExamId, data);
    }

    private Pair<Boolean, String> canSave() { 
        try {
            if (examTitle.getText().isEmpty() || time.getText().isEmpty() || testLimit.getText().isEmpty())
                return new Pair<>(false, "Vui lòng nhập đầy đủ thông tin");

//            var _ = Short.parseShort(testLimit.getText());
//            var _ = Integer.parseInt(time.getText());
        }
        catch (NumberFormatException ignore) {
            return new Pair<>(false, "Thời gian và Số lượt thi chỉ được nhập số");
        }

        return new Pair<Boolean,String>(true, "");
    }

    private TestDTO gatherFormData() {
        var date = testDateChooser.getSelectedDate();
        Date starDate = Date.valueOf(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());

        return TestDTO.builder()
                      .setTestCode(testCodeLabel.getText())
                      .setTitle(examTitle.getText())
                      .setTestLimit(Short.parseShort(testLimit.getText()))
                      .setTestTime(Integer.parseInt(time.getText()))
                      .setTestDate(starDate)
                      .setTestStatus(true);
    }

    private List<TestStructureDTO> gatherTestStructureListData() throws EmptyQuestionsException, DuplicateTopicException {
        ArrayList<TestStructureDTO> listTestStructure = new ArrayList<>();
        int questionCount = 0;
        Set<Integer> set = new HashSet<>();

        for (var testStructurePanel : testStructurePanels) {
            var request = testStructurePanel.result();

            if (!set.add(request.getTopicID())) throw new DuplicateTopicException();

            listTestStructure.add(request);

            questionCount += request.getNumEasy();
            questionCount += request.getNumMedium();
            questionCount += request.getNumDiff();
        }

        if (questionCount == 0) throw new EmptyQuestionsException();
        return listTestStructure;
    }

    private PanelBackground main;
    private PanelBackground content;
    private JScrollPane scrollablePanel;
    private PanelBackground informationPanel;
    private PanelBackground titleAndTestCodeContainer;
    private JLabel idLabel;
    private JTextField examTitle;
    private JLabel testCodeLabel;
    private JTextField testLimit;
    private JTextField time;
    private DateChooser testDateChooser;
    private JComboBox<Integer> examCount;
    private PanelBackground testStructureContainer;
    private JButton saveButton;
}