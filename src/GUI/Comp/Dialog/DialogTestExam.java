package GUI.Comp.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.TestExamBUS;
import BUS.TopicBUS;
import DTO.TestExamDTO;
import DTO.TopicDTO;
import GUI.Comp.DateChooser.DateChooser;
import GUI.Comp.DateChooser.SelectedDate;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.GridBagConstraintsBuilder;
import GUI.Utils.RandomCode;
import GUI.Utils.RoundBorder;

public class DialogTestExam extends JDialog {
    private GridBagConstraintsBuilder gbcBuilder;
    private boolean isUpdateDialog;
    private int selectedTestExamId;
    private TestExamBUS BUS;
    private TopicBUS topicBUS = new TopicBUS();

    /**
     * For create
     */
    public DialogTestExam(TestExamBUS BUS, JFrame parent) {
        super(parent, "Tạo cấu trúc đề thi", true);

        isUpdateDialog = false;
        this.BUS = BUS;
        gbcBuilder = new GridBagConstraintsBuilder();
        initComponents();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * For update
     * @param id id of the test exam
     */
    public DialogTestExam(int id, TestExamBUS BUS, JFrame parent) {
        super(parent, "Cập nhật cấu trúc đề thi", true);

        isUpdateDialog = true;
        this.BUS = BUS;
        selectedTestExamId = id;

        gbcBuilder = new GridBagConstraintsBuilder();
        initComponents();

        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(1200, 765));
        setPreferredSize(new Dimension(1200, 765));

        main = new PanelBackground();
        main.setPreferredSize(getPreferredSize());
        main.setLayout(new BorderLayout());

        content = new PanelBackground();
        content.setMinimumSize(new Dimension(1180, 765));
        content.setPreferredSize(new Dimension(1180, 765));
        content.setBorder(new EmptyBorder(0, 0, 0, 15));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        initIdLabel();
        initTitleAndTestCode();
        initInformationPanel();
        initQuestionTableContainer();
        initSaveButton();

        JScrollPane scrollablePanel = new JScrollPane();
        scrollablePanel.setViewportView(content);
        scrollablePanel.setMinimumSize(new Dimension(1200, 765));

        if (isUpdateDialog) setModel();

        main.add(scrollablePanel, BorderLayout.CENTER);

        gbcBuilder.reset();
        GridBagConstraints gbc = gbcBuilder.setPosition(0, 0)
                                           .setAnchor(GridBagConstraints.CENTER)
                                           .result();

        add(main, gbc);
    }

    //#region Update Model

    private void initIdLabel() {
        if (!isUpdateDialog) return;

        content.add(Box.createRigidArea(new Dimension(0, 20)));

        PanelBackground container = new PanelBackground();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setAbsoluteSize(1180, 30);
        container.setBorder(new EmptyBorder(0, 10, 0, 10));

        idLabel = new JLabel();
        idLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        idLabel.setText("ID cấu trúc đề thi: " + selectedTestExamId);

        container.add(idLabel);

        content.add(container);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void setModel() {
        TestExamDTO model = BUS.findById(selectedTestExamId);
        if (model == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy cấu trúc đề thi", "Lỗi", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        examTitle.setText(model.getTitle());
        testCode.setText(model.getTestCode());
        topicCb.setSelectedIndex(model.getTopicId());
        testLimit.setText(String.valueOf(model.getTestLimit()));
        time.setText(String.valueOf(model.getTestTime()));
        easyQuestionCount.setText(String.valueOf(model.getEasyQuestionCount()));
        mediumQuestionCount.setText(String.valueOf(model.getMediumQuestionCount()));
        hardQuestionCount.setText(String.valueOf(model.getDiffQuestionCount()));

        var date = model.getTestDate().toLocalDate();
        SelectedDate testDate = new SelectedDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        testDateChooser.setSelectedDate(testDate);
    }

    //#endregion

    private void initTitleAndTestCode() {
        titleAndTestCodeContainer = new PanelBackground();
        titleAndTestCodeContainer.setLayout(new FlowLayout());
        titleAndTestCodeContainer.setAbsoluteSize(1180, 60);
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
        testCode = new JLabel();
        testCode.setPreferredSize(new Dimension(200, 50));

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Mã đề");
        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));
        testCode.setBorder(titleBorder);

        String code = RandomCode.generate(3);
        testCode.setText(code);
        testCode.setHorizontalAlignment(JLabel.CENTER);

        titleAndTestCodeContainer.add(testCode);
    }

    private void initInformationPanel() {
        informationPanel = new PanelBackground();
        informationPanel.setLayout(new GridBagLayout());
        informationPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        informationPanel.setAbsoluteSize(1000, 255);

        var container = new PanelBackground();
        container.setLayout(new GridBagLayout());
        container.setAbsoluteSize(500, 255);

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Thông tin bài thi: ");
        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));

        container.setBorder(titleBorder);

        topicCb = new JComboBox<>();
        topicCb.setFont(new java.awt.Font("Roboto", 0, 16));
        setTopicItems();
        topicCb.addActionListener(this::onTopicChange);

        container.add(Box.createRigidArea(new Dimension(0, 30)), gbcBuilder.setPosition(0, 0).result());

        container.add(new JLabel("Chủ đề: "), gbcBuilder.setPosition(0, 1).result());
        container.add(topicCb, gbcBuilder.setPosition(1, 1)
                                       .setSize(3, 1)
                                       .setWeights(1, 1)
                                       .setFill(GridBagConstraints.HORIZONTAL)
                                       .result());

        gbcBuilder.reset();
        testLimit = new JTextField();
        container.add(new JLabel("Số lượt thi: "), gbcBuilder.setPosition(0, 2).result());
        container.add(testLimit, gbcBuilder.setPosition(1, 2)
                                           .setWeights(1, 1)
                                           .setFill(GridBagConstraints.HORIZONTAL)
                                           .setInsets(0, 0, 0, 10)
                                           .result());
        gbcBuilder.reset();

        time = new JTextField();
        container.add(new JLabel("Thời gian thi: "), gbcBuilder.setPosition(2, 2).result());
        container.add(time, gbcBuilder.setPosition(3, 2)
                                      .setWeights(1, 1)
                                      .setFill(GridBagConstraints.HORIZONTAL)
                                      .result());

        gbcBuilder.reset();
        easyQuestionCount = new JTextField();
        mediumQuestionCount = new JTextField();
        hardQuestionCount = new JTextField();

        container.add(new JLabel("Số câu dễ: "), gbcBuilder.setPosition(0, 3).result());
        container.add(easyQuestionCount, gbcBuilder.setPosition(1, 3)
                                                   .setWeights(1, 1)
                                                   .setFill(GridBagConstraints.HORIZONTAL)
                                                   .setInsets(0, 0, 0, 10)
                                                   .result());

        gbcBuilder.reset();

        container.add(new JLabel("Số câu trung bình: "), gbcBuilder.setPosition(0, 4).result());
        container.add(mediumQuestionCount, gbcBuilder.setPosition(1, 4)
                                                     .setWeights(1, 1)
                                                     .setFill(GridBagConstraints.HORIZONTAL)
                                                     .result());

        gbcBuilder.reset();
        
        container.add(new JLabel("Số câu khó: "), gbcBuilder.setPosition(2, 3).result());
        container.add(hardQuestionCount, gbcBuilder.setPosition(3, 3)
                                                   .setWeights(1, 1)
                                                   .setFill(GridBagConstraints.HORIZONTAL)
                                                   .result());

        informationPanel.add(container, gbcBuilder.setPosition(0, 0)
                                                  .setWeights(1, 1)
                                                  .setInsets(0, 20, 0, 0)
                                                  .result());

        initTestDateChooser();
        content.add(informationPanel);
    }

    private void setTopicItems() {
        topicCb.removeAllItems();
        var topics = topicBUS.getAllTopic();

        topics.forEach(topicCb::addItem);

        topicCb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof TopicDTO) {
                    TopicDTO topic = (TopicDTO) value;
                    setText(topic.getTitle());
                }

                return this;
            }
        });
    }

    private void initTestDateChooser() {
        var dateContainer = new PanelBackground();
        dateContainer.setLayout(new BoxLayout(dateContainer, BoxLayout.Y_AXIS));
        dateContainer.setAbsoluteSize(500, 315);

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Ngày bắt đầu thi: ");

        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));
        dateContainer.setBorder(titleBorder);

        testDateChooser = new DateChooser();
        testDateChooser.setMaximumSize(new Dimension(260, 315));
        testDateChooser.setAlignmentX(CENTER_ALIGNMENT);

        dateContainer.add(testDateChooser);

        gbcBuilder.reset();
        informationPanel.add(dateContainer, gbcBuilder.setPosition(1, 0)
                                                      .setWeights(1, 1)
                                                      .setInsets(0, 20, 0, 0)
                                                      .result());
    }

    private void initQuestionTableContainer() {
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        var container = new PanelBackground();
        container.setAbsoluteSize(1180, 50);
        container.setLayout(new BorderLayout());

        JLabel title = new JLabel("Danh sách câu hỏi");
        title.setFont(new Font("Roboto", Font.BOLD, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);

        container.add(Box.createRigidArea(new Dimension(520, 10)), BorderLayout.WEST);
        container.add(title, BorderLayout.CENTER);

        content.add(container);
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        var tableContainer = new PanelBackground();
        tableContainer.setLayout(new BorderLayout());

        questionTableScrollPane = new JScrollPane();
        initQuestionTable();
        questionTableScrollPane.setViewportView(questionTable);

        tableContainer.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        tableContainer.add(questionTableScrollPane, BorderLayout.CENTER);
        tableContainer.add(Box.createRigidArea(new Dimension(25, 0)), BorderLayout.EAST);
        content.add(tableContainer);
    }

    private void initQuestionTable() {
        questionTable = new JTable();

        questionTable.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        questionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
            },
            new String [] {
                "ID", "Câu hỏi", "Chủ đề", "Độ khó"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) questionTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        questionTable.setRowHeight(30);

        renderTable();
    }

    private void renderTable() {
        DefaultTableModel model = (DefaultTableModel) questionTable.getModel();
        model.setRowCount(0);

        TopicDTO selectedTopic = (TopicDTO) topicCb.getSelectedItem();
        int topicId = selectedTopic.getId();

        BUS.getByTopicId(topicId).forEach(question -> {
            model.addRow(new Object[] {
                question.getId(),
                question.getContent(),
                question.getTopicId(),
                question.getLevel()
            });
        });

        model.fireTableDataChanged();
        questionTable.setModel(model);
    }

    private void initSaveButton() {
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        saveButton = new JButton("Lưu");
        saveButton.setFont(new Font("Roboto", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setForeground(Color.white);
        saveButton.setBackground(Color.green);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        saveButton.addActionListener(this::onSave);

        content.add(saveButton);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void onTopicChange(ActionEvent e) {
        renderTable();
    }

    private void onSave(ActionEvent e) {
        TestExamDTO data = gatherFormData();
        boolean result;
        String action;

        action = !isUpdateDialog ? "Tạo" : "Cập nhật";
        result = !isUpdateDialog ? create(data) : update(data);

        if (result) JOptionPane.showMessageDialog(this, action + " thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(this, action + " thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);

        this.dispose();
    }

    private boolean create(TestExamDTO data) {
        return BUS.create(data);
    }

    private boolean update(TestExamDTO data) {
        return BUS.update(selectedTestExamId, data);
    }

    private TestExamDTO gatherFormData() {
        var date = testDateChooser.getSelectedDate();
        Date starDate = Date.valueOf(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());

        TopicDTO selectedTopic = (TopicDTO) topicCb.getSelectedItem();
        int topicId = selectedTopic.getId();

        return TestExamDTO.builder()
                          .setTestCode(testCode.getText())
                          .setTitle(examTitle.getText())
                          .setTopicId(topicId)
                          .setEasyQuestionCount(Integer.parseInt(easyQuestionCount.getText()))
                          .setMediumQuestionCount(Integer.parseInt(mediumQuestionCount.getText()))
                          .setDiffQuestionCount(Integer.parseInt(hardQuestionCount.getText()))
                          .setTestLimit(Short.parseShort(testLimit.getText()))
                          .setTestTime(Integer.parseInt(time.getText()))
                          .setTestDate(starDate)
                          .setTestStatus(true);
    }

    private PanelBackground main;
    private PanelBackground content;
    private PanelBackground informationPanel;
    private PanelBackground titleAndTestCodeContainer;
    private JLabel idLabel;
    private JTextField examTitle;
    private JLabel testCode;
    private JComboBox<TopicDTO> topicCb;
    private JTextField testLimit;
    private JTextField time;
    private DateChooser testDateChooser;
    private JTextField easyQuestionCount;
    private JTextField mediumQuestionCount;
    private JTextField hardQuestionCount;
    private JTable questionTable;
    private JScrollPane questionTableScrollPane;
    private JButton saveButton;
}