package GUI.Custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import BUS.TopicBUS;
import DTO.TestStructureDTO;
import DTO.TopicDTO;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.RoundBorder;
import style.ColorConfig;

public class TestStructurePanel extends PanelBackground {
    private RoundBorder border = new RoundBorder(Color.BLACK, 10);
    private int width, height;
    private List<TopicDTO> topics;
    private String testCode;
    private ArrayList<Consumer<TestStructurePanel>> onDeleteListeners = new ArrayList<>();

    public TestStructurePanel(int width, int height, List<TopicDTO> topics, String testCode) {
        this.topics = topics;
        this.width = width;
        this.height = height;
        this.testCode = testCode;

        initComponents();
    }

    private void initComponents() {
        setBorder(border);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createRigidArea(new Dimension(60, getHeight())));

        addInformationContainer();
        addDeleteButton();
    }

    private void addInformationContainer() {
        int containerHeight = height - 50;
        int containerWidth = width - 200;

        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, containerHeight);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(getTopicContainer());
        container.add(getQuestionSettingContainer());

        add(container);
    }

    private PanelBackground getTopicContainer() {
        int containerWidth = width - 200;

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
        int containerWidth = width - 200;
        
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(containerWidth, 66);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));

        initTextField();

        container.add(getQuestionItem(180, "Số câu dễ:", numEasyField));
        container.add(getQuestionItem(220, "Số câu trung bình:", numMediumField));
        container.add(getQuestionItem(180, "Số câu khó:", numDiffField));

        return container;
    }

    private void initTextField() {
        numEasyField = new JTextField();
        numMediumField = new JTextField();
        numDiffField = new JTextField();
    }

    private PanelBackground getQuestionItem(int width, String labelText, JTextField textField) {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(width, 50);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        container.add(new JLabel(labelText));
        container.add(textField);

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
    }

    private void addDeleteButton() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(80, height - 40);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton button = new JButton("Xóa");
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> {
            onDelete();
        });

        container.add(button);

        add(container);
    }

    public TestStructureDTO result() {
        TopicDTO topic = (TopicDTO) topicCb.getSelectedItem();
        return TestStructureDTO.builder()
                               .setTestCode(testCode)
                               .setTopicID(topic.getId())
                               .setNumEasy(Integer.parseInt(numEasyField.getText()))
                               .setNumMedium(Integer.parseInt(numMediumField.getText()))
                               .setNumDiff(Integer.parseInt(numDiffField.getText()));
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
    private JTextField numEasyField;
    private JTextField numMediumField;
    private JTextField numDiffField;
}
