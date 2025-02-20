package GUI.Comp.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import GUI.Comp.DateChooser.DateChooser;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.GridBagConstraintsBuilder;
import GUI.Utils.RandomCode;
import GUI.Utils.RoundBorder;

public class DialogTestExam extends JDialog {
    private GridBagConstraintsBuilder gbcBuilder;

    public DialogTestExam(JFrame parent) {
        super(parent, "Tạo cấu trúc đề thi", true);
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

        initTitleAndTestCode();
        initInformationPanel();
        initQuestionTableContainer();
        initSaveButton();

        JScrollPane scrollablePanel = new JScrollPane();
        scrollablePanel.setViewportView(content);
        scrollablePanel.setMinimumSize(new Dimension(1200, 765));

        main.add(scrollablePanel, BorderLayout.CENTER);

        gbcBuilder.reset();
        GridBagConstraints gbc = gbcBuilder.setPosition(0, 0)
                                           .setAnchor(GridBagConstraints.CENTER)
                                           .result();

        add(main, gbc);
    }

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
        testCode = new JTextField();
        testCode.setEditable(false);
        testCode.setPreferredSize(new Dimension(200, 50));

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Mã đề");
        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));
        testCode.setBorder(titleBorder);

        String code = RandomCode.generate(6);
        testCode.setText(code);

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

        topic = new JComboBox<>();
        topic.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        topic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn chủ đề", "Item 1", "Item 2", "Item 3" }));

        container.add(Box.createRigidArea(new Dimension(0, 30)), gbcBuilder.setPosition(0, 0).result());

        container.add(new JLabel("Chủ đề: "), gbcBuilder.setPosition(0, 1).result());
        container.add(topic, gbcBuilder.setPosition(1, 1)
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

        initStartDateChooser();
        content.add(informationPanel);
    }

    private void initStartDateChooser() {
        var dateContainer = new PanelBackground();
        dateContainer.setLayout(new BoxLayout(dateContainer, BoxLayout.Y_AXIS));
        dateContainer.setAbsoluteSize(500, 315);

        var roundedBorder = new RoundBorder(Color.gray, 10);
        var titleBorder = new TitledBorder(roundedBorder, "Ngày bắt đầu thi: ");

        titleBorder.setTitleJustification(TitledBorder.CENTER);
        titleBorder.setTitleFont(new Font("Roboto", Font.BOLD, 13));
        dateContainer.setBorder(titleBorder);

        startDateChooser = new DateChooser();
        startDateChooser.setMaximumSize(new Dimension(260, 315));
        startDateChooser.setAlignmentX(CENTER_ALIGNMENT);

        dateContainer.add(startDateChooser);

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
                {null, null, null, null, null},
                {null, null, null, null, null},
            },
            new String [] {
                "ID", "Câu hỏi", "Chủ đề", "Độ khó", "Điểm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) questionTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        questionTable.setRowHeight(30);
    }

    private void initSaveButton() {
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        saveButton = new JButton("Lưu");
        saveButton.setFont(new Font("Roboto", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setForeground(Color.white);
        saveButton.setBackground(Color.green);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Lưu thành công", "", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });

        content.add(saveButton);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private PanelBackground main;
    private PanelBackground content;
    private PanelBackground informationPanel;
    private PanelBackground titleAndTestCodeContainer;
    private JTextField examTitle;
    private JTextField testCode;
    private JComboBox<String> topic;
    private JTextField testLimit;
    private JTextField time;
    private DateChooser startDateChooser;
    private JTextField easyQuestionCount;
    private JTextField mediumQuestionCount;
    private JTextField hardQuestionCount;
    private JTable questionTable;
    private JScrollPane questionTableScrollPane;
    private JButton saveButton;
}
