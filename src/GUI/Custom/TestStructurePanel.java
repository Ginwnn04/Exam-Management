package GUI.Custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import BUS.QuestionBUS;
import BUS.TopicBUS;
import DTO.QuestionDTO;
import DTO.TestStructureDTO;
import DTO.TopicDTO;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.RoundBorder;
import style.ColorConfig;

public class TestStructurePanel extends PanelBackground {
    private RoundBorder border = new RoundBorder(Color.BLACK, 10);
    private final int WIDTH = 1180, HEIGHT = 200;
    
    private List<TopicDTO> topics;
    private String testCode;
    private ArrayList<Consumer<TestStructurePanel>> onDeleteListeners = new ArrayList<>();
    private QuestionBUS questionBUS;
    private List<QuestionDTO> questions;
    
    private boolean isEditable = false;

    public TestStructurePanel(List<TopicDTO> topics, String testCode, QuestionBUS questionBUS) {
        this.topics = topics;
        this.testCode = testCode;
        this.questionBUS = questionBUS;

        initComponents();
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;

        disableForm();
    }

    public void setModel(TestStructureDTO testStructure) {
        int topicId = testStructure.getTopicID();
        var topicOpt = topics.stream().filter(item -> item.getId() == topicId).findFirst();
        var topic = topicOpt.get();
        
        topicCb.setSelectedItem(topic);
        numEasyCb.setSelectedItem(testStructure.getNumEasy());
        numMediumCb.setSelectedItem(testStructure.getNumMedium());
        numDiffCb.setSelectedItem(testStructure.getNumDiff());
    }

    private void disableForm() {
        topicCb.setEnabled(false);
        numEasyCb.setEnabled(false);
        numMediumCb.setEnabled(false);
        numDiffCb.setEnabled(false);

        deleteButton.setEnabled(false);
    }

    private void initComponents() {
        setBorder(border);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createRigidArea(new Dimension(60, getHeight())));

        addInformationContainer();
        addDeleteButton();
    }

    private void initDifficultComboBox() {
        numEasyCb = new JComboBox<>();
        numMediumCb = new JComboBox<>();
        numDiffCb = new JComboBox<>();
    }

    private void addInformationContainer() {
        int containerHeight = HEIGHT - 50;
        int containerWidth = WIDTH - 200;

        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, containerHeight);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        initDifficultComboBox();
        container.add(getTopicContainer());
        container.add(getQuestionSettingContainer());

        add(container);
    }

    private PanelBackground getTopicContainer() {
        int containerWidth = WIDTH - 200;

        PanelBackground topicContainer = new PanelBackground();
        topicContainer.setAbsoluteSize(containerWidth, 66);
        topicContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        topicContainer.add(Box.createRigidArea(new Dimension(10, 0)));
        topicContainer.add(new JLabel("Chủ đề: "));
                
        topicCb = new JComboBox<>();
        topicCb.setPreferredSize(new Dimension(200, (int)topicCb.getPreferredSize().getHeight()));
        topicContainer.add(topicCb);
        setTopicCbItems();

        return topicContainer;
    }

    private PanelBackground getQuestionSettingContainer() {
        int containerWidth = WIDTH - 200;
        
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, 66);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));

        container.add(getQuestionItem(180, "Số câu dễ:", numEasyCb));
        container.add(getQuestionItem(220, "Số câu trung bình:", numMediumCb));
        container.add(getQuestionItem(180, "Số câu khó:", numDiffCb));

        return container;
    }

    private PanelBackground getQuestionItem(int width, String labelText, JComboBox<Integer> cbBox) {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(width, 50);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        container.add(new JLabel(labelText));
        container.add(cbBox);

        return container;
    }

    private void setTopicCbItems() {
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

        topicCb.addActionListener(this::onTopicChange);
        onTopicChange(null);
    }

    private void addDeleteButton() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(80, HEIGHT - 40);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            onDelete();
        });

        container.add(deleteButton);

        add(container);
    }

    private void onTopicChange(ActionEvent e) {
        TopicDTO topic = (TopicDTO) topicCb.getSelectedItem();
        questions = questionBUS.getQuestionByTopicAndLevel(topic.getId(), "");
        setDifficultComboBoxItems();
    }

    private void setDifficultComboBoxItems() { 
        int[] difficult = getQuestionDiffCount();

        var easyItems = IntStream.rangeClosed(0, difficult[0])
                                 .boxed()
                                 .toArray(Integer[]::new);

        var mediumItems = IntStream.rangeClosed(0, difficult[1])
                                 .boxed()
                                 .toArray(Integer[]::new);

        var diffItems = IntStream.rangeClosed(0, difficult[2])
                                 .boxed()
                                 .toArray(Integer[]::new);


        numEasyCb.setModel(new DefaultComboBoxModel<>(easyItems));
        numMediumCb.setModel(new DefaultComboBoxModel<>(mediumItems));
        numDiffCb.setModel(new DefaultComboBoxModel<>(diffItems));
    }

    private int[] getQuestionDiffCount() {
        int[] difficult = {0, 0, 0};

        for (var question : questions) {
            String level = question.getLevel();

            switch (level) {
                case "easy":
                    difficult[0]++;
                    break;

                case "medium":
                    difficult[1]++;
                    break;

                case "diff":
                    difficult[2]++;
                    break;
            
                default:
                    break;
            }
        }

        return difficult;
    }

    public TestStructureDTO result() {
        TopicDTO topic = (TopicDTO) topicCb.getSelectedItem();

        int easyCount = (Integer) numEasyCb.getSelectedItem();
        int mediumCount = (Integer) numMediumCb.getSelectedItem();
        int diffCount = (Integer) numDiffCb.getSelectedItem();

        return TestStructureDTO.builder()
                               .setTestCode(testCode)
                               .setTopicID(topic.getId())
                               .setNumEasy(easyCount)
                               .setNumMedium(mediumCount)
                               .setNumDiff(diffCount);
    }

    public void addOnDeleteListener(Consumer<TestStructurePanel> callback) {
        onDeleteListeners.add(callback);
    }

    private void onDelete() {
        for (var callback : onDeleteListeners) {
            callback.accept(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = 50;
        int height = getHeight();
        int radius = border.getRadius();
        
        int x = 0, y = 0;

        g.setColor(ColorConfig.BLUE);
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

        g2d.setColor(ColorConfig.BLUE);
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

        g2d.setColor(ColorConfig.BLUE);
        Path2D path = new Path2D.Double();
        path.moveTo(x + radius, height - radius);
        path.lineTo(x + radius * 2, height - radius);
        path.append(new Arc2D.Double(x, y + height - 2 * radius - 1, 2 * radius, 2 * radius, 180, 90, Arc2D.OPEN), true); // Append the arc
        path.closePath();

        g2d.fill(path);
    }
    
    private JComboBox<TopicDTO> topicCb;
    private JComboBox<Integer> numEasyCb;
    private JComboBox<Integer> numMediumCb;
    private JComboBox<Integer> numDiffCb;
    private JButton deleteButton;
}
