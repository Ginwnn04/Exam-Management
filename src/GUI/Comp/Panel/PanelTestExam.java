package GUI.Comp.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.TestExamBUS;
import DTO.TestExamDTO;
import GUI.Comp.Dialog.DialogTestExam;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.TableActionCellEditor;
import GUI.Custom.TableActionCellRenderer;
import GUI.Custom.TableActionEvent;
import GUI.Utils.Debounce;
import GUI.Utils.GridBagConstraintsBuilder;

public class PanelTestExam extends JPanel {
    private GridBagConstraintsBuilder gbcBuilder = new GridBagConstraintsBuilder();
    private TestExamBUS BUS;
    private ArrayList<TestExamDTO> testExams;

    public PanelTestExam() {
        BUS = new TestExamBUS();
        initComponents();
    }

    private void updateTableItems() {
        testExams = BUS.getAll(true);
        renderTable();
    }

    private void setTableItems(ArrayList<TestExamDTO> testExams) {
        this.testExams = testExams;
        renderTable();
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
        setupSearchFieldEvent();

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
    
        container.add(searchAndFilterContainer);
        container.add(buildCreateButtonContainer());

        content.add(container);
    }

    private void setupSearchFieldEvent() {
        Debounce onSearch = new Debounce(() -> filtTableItems(), 500);

        searchField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearch.execute();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearch.execute();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearch.execute();
            }
        });
    }

    private void filtTableItems() {
        var list = BUS.getAll(true);
        String query = searchField.getText().toLowerCase();

        var searchBy = searchByCb.getSelectedItem().toString();
        List<TestExamDTO> filtList;

        if (query.isEmpty()){
            updateTableItems();
            return;
        }

        Predicate<TestExamDTO> filter = getSearchFilter(searchBy, query);
        filtList = list.stream().filter(filter).toList();

        setTableItems(new ArrayList<>(filtList));
    }

    private Predicate<TestExamDTO> getSearchFilter(String searchBy, String query) {
        switch (searchBy) {
            case "ID":
                return testExam -> {
                    try {
                        return testExam.getId() == Integer.parseInt(query);
                    }
                    catch (Exception ignore) {
                        return false;
                    }
                };

            case "Tiêu đề":
                return testExam -> testExam.getTitle().toLowerCase().contains(query);

            case "Mã đề":
                return testExam -> testExam.getTestCode().toLowerCase().contains(query);
        
            default:
                return null;
        }
    }

    private PanelBackground buildCreateButtonContainer() {
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

        return temp;
    }

    private void assignCreateElement() {
        DialogTestExam dialogTestExam = new DialogTestExam(BUS, null);

        createButton.addActionListener(e -> {
            dialogTestExam.setVisible(true);
            updateTableItems();
        });
    }

    private void initTable() {
        table = new JTable();

        table.setFont(new Font("Roboto", 0, 16)); // NOI18N
        table.setModel(new DefaultTableModel(
            new Object [][]{},
            new String [] {
                "ID", "Mã đề", "Tiêu đề", "Chủ đề", "Thời gian thi", "Số lượt thi", "Ngày thi", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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
                int id = (int) table.getValueAt(row, 0);
                BUS.delete(id);
                updateTableItems();
            }

            @Override
            public void onUpdate(int row) {
                showUpdateDialog(row);
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

        setTableItems(BUS.getAll(true));

        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
    }

    private void showUpdateDialog(int row) {
        int id = (int) table.getValueAt(row, 0);
        DialogTestExam dialogTestExam = new DialogTestExam(id, BUS, null);

        dialogTestExam.setVisible(true);
        updateTableItems();
    }

    private void renderTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (testExams == null) return;

        testExams.forEach(testExam -> {
            model.addRow(new Object[] {
                testExam.getId(),
                testExam.getTestCode(),
                testExam.getTitle(),
                testExam.getTopicId(),
                testExam.getTestTime(),
                testExam.getTestLimit(),
                testExam.getTestDate(),
                ""
            });
        });

        model.fireTableDataChanged();
        table.setModel(model);
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