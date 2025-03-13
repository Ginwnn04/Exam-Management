package GUI.Comp.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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

import BUS.TestBUS;
import BUS.TopicBUS;
import DTO.TestDTO;
import DTO.TopicDTO;
import GUI.Comp.Dialog.DialogTest;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.TableActionCellEditor;
import GUI.Custom.TableActionCellRenderer;
import GUI.Custom.TableActionEvent;
import GUI.Utils.Debounce;
import GUI.Utils.GridBagConstraintsBuilder;
import style.ColorConfig;
import style.MyFont;
public class PanelTest extends JPanel {
    private final int WIDTH = 1160;

    private GridBagConstraintsBuilder gbcBuilder = new GridBagConstraintsBuilder();
    private TestBUS BUS;
    private TopicBUS topicBUS;

    private ArrayList<TestDTO> testExams;
    private ArrayList<TestDTO> filterTestExams;

    public PanelTest() {
        BUS = new TestBUS();
        topicBUS = new TopicBUS();
        testExams = BUS.getAll(true);

        initComponents();
    }

    private void resetTableItems(boolean isFetch) {
        if (isFetch) testExams = BUS.getAll(true);
        filterTestExams = testExams;
    }

    private void setTableItems(ArrayList<TestDTO> list) {
        filterTestExams = list;
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200, 765));

        main = new PanelBackground();
        main.setPreferredSize(new Dimension(WIDTH, 725));
        main.setLayout(new FlowLayout());

        content = new PanelBackground();
        content.setPreferredSize(new Dimension(WIDTH, 725));
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
        container.setAbsoluteSize(WIDTH, 50);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        searchAndFilterContainer = new PanelBackground();
        searchAndFilterContainer.setAbsoluteSize(964, 50);
        searchAndFilterContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        JLabel searchLabel = new JLabel("Tìm kiếm: ");
        searchLabel.setFont(MyFont.fontHeader);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(350, 30));
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        setupSearchFieldEvent();

        searchAndFilterContainer.add(searchLabel);
        searchAndFilterContainer.add(searchField);
        searchAndFilterContainer.add(Box.createRigidArea(new Dimension(20, 0)));

        JLabel searchByCbLabel = new JLabel("Tìm kiếm theo:");
        searchByCb = new JComboBox<>();
        searchByCb.setPreferredSize(new Dimension(150, 30));
        searchByCb.setModel(new DefaultComboBoxModel<>(new String[] { "ID", "Mã đề", "Tiêu đề" }));
        searchByCb.addActionListener(e -> filterTableItems());

        searchAndFilterContainer.add(searchByCbLabel);
        searchAndFilterContainer.add(searchByCb);
    
        container.add(searchAndFilterContainer);
        container.add(buildCreateButtonContainer());
        content.add(container);
    }

    private void setupSearchFieldEvent() {
        Debounce onSearch = new Debounce(() -> filterTableItems(), 500);

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

    private void filterTableItems() {
        resetTableItems(false);
        String query = searchField.getText().toLowerCase();

        var searchBy = searchByCb.getSelectedItem().toString();
        List<TestDTO> filterList;

        Predicate<TestDTO> filter = getSearchFilter(searchBy, query);
        filterList = filterTestExams.stream().filter(filter).toList();
    
        setTableItems(new ArrayList<>(filterList));
        renderTable();
    }

    private Predicate<TestDTO> getSearchFilter(String searchBy, String query) {
        if (query.isEmpty()) return testExam -> true;

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
        createButton.setBackground(ColorConfig.BLUE);
        createButton.setFont(MyFont.fontHeader);
        createButton.setForeground(Color.WHITE);
        createButton.setPreferredSize(new Dimension(116, 30));

        assignCreateElement();

        PanelBackground temp = new PanelBackground();
        temp.setAbsoluteSize(166, 50);
        temp.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        temp.add(createButton);

        return temp;
    }

    private void assignCreateElement() {
        createButton.addActionListener(e -> {
            DialogTest dialogTestExam = new DialogTest(BUS, null);
            dialogTestExam.setVisible(true);
            resetTableItems(true);
            renderTable();
        });
    }

    private void initTable() {
        table = new JTable();

        table.setFont(MyFont.fontText);
        table.setModel(new DefaultTableModel(
            new Object [][]{},
            new String [] {
                "ID", "Mã đề", "Tiêu đề", "Thời gian thi", "Số lượt thi", "Ngày thi", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
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
                onDeleteTestExam(row);
            }

            @Override
            public void onUpdate(int row) {
                showUpdateDialog(row);
            }

            @Override
            public void onView(int row) {
                showUpdateDialog(row);
            }
            
        };

        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        table.setRowHeight(30);

        resetTableItems(false);
        renderTable();

        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
    }

    private void showUpdateDialog(int row) {
        int id = (int) table.getValueAt(row, 0);
        DialogTest dialogTestExam = new DialogTest(id, BUS, null);

        dialogTestExam.setVisible(true);
        resetTableItems(true);
        renderTable();
    }


    private void onDeleteTestExam(int row) {
        int id = (int) table.getValueAt(row, 0);
        String testCode = (String) table.getValueAt(row, 1);
        String message = "Bạn có muốn xóa cấu trúc đề thi với Id " + id + " không";
        
        int response = JOptionPane.showConfirmDialog(this, message, "Xóa cấu trúc đề thi", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION) return;
        
        BUS.delete(id, testCode);
        resetTableItems(true);
        renderTable();
    }

    private void renderTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (filterTestExams == null) return;

        filterTestExams.forEach(testExam -> {
            model.addRow(new Object[] {
                testExam.getId(),
                testExam.getTestCode(),
                testExam.getTitle(),
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
    private JButton exportExamButton;
    private JTextField searchField;
    private JComboBox<String> searchByCb;
    private JTable table;
}