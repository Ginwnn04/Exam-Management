package GUI.Comp.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.concurrent.Flow;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.Comp.Dialog.DialogTestExam;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.TableActionCellEditor;
import GUI.Custom.TableActionCellRenderer;
import GUI.Custom.TableActionEvent;
import GUI.Utils.GridBagConstraintsBuilder;

public class PanelTestExam extends JPanel {
    private GridBagConstraintsBuilder gbcBuilder = new GridBagConstraintsBuilder();

    public PanelTestExam() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200, 765));

        main = new PanelBackground();
        main.setPreferredSize(new Dimension(1160, 725));
        main.setLayout(new FlowLayout());

        content = new PanelBackground();
        content.setPreferredSize(new Dimension(1160, 725));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        initSearchAndFilter();
        initTable();
        
        main.add(content);

        gbcBuilder.reset();
        GridBagConstraints gbc = gbcBuilder.setPosition(0, 0)
                                           .setAnchor(GridBagConstraints.CENTER)
                                           .result();

        add(main, gbc);
    }

    private void initSearchAndFilter() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(1160, 50);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        searchAndFilterContainer = new PanelBackground();
        searchAndFilterContainer.setAbsoluteSize(964, 50);
        searchAndFilterContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(350, 30));
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");

        searchAndFilterContainer.add(searchField);
        searchAndFilterContainer.add(Box.createRigidArea(new Dimension(20, 0)));

        JLabel searchByCbLabel = new JLabel("Tìm kiếm theo:");
        searchByCb = new JComboBox<>();
        searchByCb.setPreferredSize(new Dimension(150, 30));
        searchByCb.setModel(new DefaultComboBoxModel<>(new String[] { "ID", "Mã đề", "Tiêu đề" }));

        JLabel topicFilterLabel = new JLabel("Chủ đề: ");
        topicFilter = new JComboBox<>();
        topicFilter.setPreferredSize(new Dimension(150, 30));
        topicFilter.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả", "Toán", "Văn", "Anh" }));

        searchAndFilterContainer.add(searchByCbLabel);
        searchAndFilterContainer.add(searchByCb);

        searchAndFilterContainer.add(Box.createRigidArea(new Dimension(20, 0)));

        searchAndFilterContainer.add(topicFilterLabel);
        searchAndFilterContainer.add(topicFilter);

        createButton = new JButton("+ Thêm");
        createButton.setBackground(new Color(225, 99, 73));
        createButton.setFont(new Font("Roboto", 1, 16));
        createButton.setForeground(new Color(255, 255, 255));
        createButton.setPreferredSize(new Dimension(116, 30));

        assignCreateElement();

        PanelBackground temp = new PanelBackground();
        temp.setAbsoluteSize(166, 50);
        temp.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        temp.add(createButton);
    
        container.add(searchAndFilterContainer);
        container.add(temp);

        content.add(container);
    }

    private void initTable() {
        table = new JTable();

        table.setFont(new Font("Roboto", 0, 16)); // NOI18N
        table.setModel(new DefaultTableModel(
            new Object [][] {
                {"1", "Akms24", "Shiba Lmao", "Toan", 15, 2, new Date()}
            },
            new String [] {
                "ID", "Mã đề", "Tiêu đề", "Chủ đề", "Thời gian thi", "Số lượt thi", "Ngày tạo", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(cellRenderer);
        }

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onDelete(int row) {
                
            }

            @Override
            public void onUpdate(int row) {
                
            }

            @Override
            public void onView(int row) {
                
            }
            
        };

        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
    }

    private void assignCreateElement() {
        DialogTestExam dialogTestExam = new DialogTestExam(null);

        createButton.addActionListener(e -> {
            dialogTestExam.setVisible(true);
        });
    }

    private PanelBackground main;
    private PanelBackground content;
    private PanelBackground searchAndFilterContainer;
    private JButton createButton;
    private JTextField searchField;
    private JComboBox<String> searchByCb;
    private JComboBox<String> topicFilter;
    private JTable table;
}
