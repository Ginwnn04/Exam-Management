package GUI.Comp.Panel;

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
import javax.swing.JLabel;
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
import GUI.Utils.RoundBorder;

public class PanelCreateExam extends JPanel {
    private GridBagConstraintsBuilder gbcBuilder;

    public PanelCreateExam(){
        gbcBuilder = new GridBagConstraintsBuilder();
        initComponents();
    }
    
    private void initComponents(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200, 765));

        main = new PanelBackground();
        main.setPreferredSize(new Dimension(1180, 745));
        main.setLayout(new FlowLayout());

        content = new PanelBackground();
        content.setPreferredSize(getPreferredSize());
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        initTitle();
        initExamTitle();
        initInformationPanel();
        initQuestionTableContainer();

        main.add(content);

        gbcBuilder.reset();
        GridBagConstraints gbc = gbcBuilder.setPosition(0, 0)
                                        .setAnchor(GridBagConstraints.CENTER)
                                        .result();

        add(main, gbc);
    }
    
    private void initTitle(){
        title = new JLabel("Tạo đề thi");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Roboto", Font.BOLD, 30));

        content.add(title);
        content.add(Box.createRigidArea(new Dimension(1180, 30)));
    }

    private void initExamTitle(){
        examTitleContainer = new PanelBackground();
        examTitleContainer.setLayout(new FlowLayout());
        examTitleContainer.setAbsoluteSize(1180, 60);
        examTitleContainer.setBorder(new EmptyBorder(0, 10, 0, 10));

        examTitle = new JTextField();
        examTitle.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tiêu đề");
        examTitle.setPreferredSize(new Dimension(1140, 40));
        
        examTitleContainer.add(examTitle);
        content.add(examTitleContainer);
    }

    private void initInformationPanel(){
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

        container.add(Box.createRigidArea(new Dimension(0, 30)), gbcBuilder.setPosition(0, 3).result());

        gbcBuilder.reset();
        informationPanel.add(container, gbcBuilder.setPosition(0, 0)
                                                  .setWeights(1, 1)
                                                  .setInsets(0, 20, 0, 0)
                                                  .result());

        initStartDateChooser();


        content.add(informationPanel);
    }

    private void initStartDateChooser(){
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

    private void initQuestionTableContainer(){
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        var container = new PanelBackground();
        container.setAbsoluteSize(1180, 50);
        container.setLayout(new BorderLayout());

        JLabel title = new JLabel("Danh sách câu hỏi");
        title.setFont(new Font("Roboto", Font.BOLD, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);

        container.add(Box.createRigidArea(new Dimension(520, 10)), BorderLayout.WEST);
        container.add(title, BorderLayout.CENTER);

        addQuestionButton = new JButton("Thêm câu hỏi");
        addQuestionButton.setBackground(new Color(225, 99, 73));
        addQuestionButton.setFont(new Font("Roboto", 1, 16)); // NOI18N
        addQuestionButton.setForeground(new Color(255, 255, 255));
        addQuestionButton.setText("+ Thêm câu hỏi");
        addQuestionButton.setPreferredSize(new Dimension(156, 15));

        container.add(addQuestionButton, BorderLayout.EAST);

        content.add(container);
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        var tableContainer = new PanelBackground();
        tableContainer.setLayout(new BorderLayout());

        questionTableScrollPane = new JScrollPane();
        initQuestionTable();
        questionTableScrollPane.setViewportView(questionTable);

        tableContainer.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        tableContainer.add(questionTableScrollPane, BorderLayout.CENTER);
        tableContainer.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        content.add(tableContainer);
    }

    private void initQuestionTable(){
        questionTable = new JTable();

        questionTable.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        questionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Câu hỏi", "Chủ đề", "Độ khó", "Hành động"
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

    private PanelBackground main;
    private PanelBackground content;
    private PanelBackground informationPanel;
    private PanelBackground examTitleContainer;
    private JTextField examTitle;
    private JComboBox<String> topic;
    private JTextField testLimit;
    private JTextField time;
    private DateChooser startDateChooser;
    private JLabel title;
    private JButton addQuestionButton;
    private JTable questionTable;
    private JScrollPane questionTableScrollPane;
}
