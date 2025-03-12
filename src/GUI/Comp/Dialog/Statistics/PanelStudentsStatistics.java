package GUI.Comp.Dialog.Statistics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
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

import BUS.ExamBUS;
import BUS.TestBUS;
import BUS.UserBus;
import DTO.ExamDTO;
import DTO.ResultDTO;
import DTO.TestDTO;
import DTO.UserDTO;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.Debounce;
import style.ColorConfig;
import style.MyFont;

public class PanelStudentsStatistics extends PanelBackground {
    private final int WIDTH = 1180;
    private ArrayList<Consumer<PanelBackground>> onChangeTabListener = new ArrayList<>();

    private List<ResultDTO> listResult;
    private List<ResultDTO> filterListResult;
    private TestDTO testExam;

    private HashMap<Integer, UserDTO> users = new HashMap<>();

    private UserBus userBUS = new UserBus();
    private ExamBUS examBUS = new ExamBUS();

    public PanelStudentsStatistics(TestDTO testExam, List<ResultDTO> listResult) {
        this.listResult = listResult;
        this.testExam = testExam;
        
        fetchUsers();
        initComponents();
        setBorder(new EmptyBorder(0, 10, 0, 10));
    }

     private void resetTableItems() {
        filterListResult = listResult;
    }

    private void setTableItems(ArrayList<ResultDTO> list) {
        filterListResult = list;
    }

    private void fetchUsers() {
        var listUsers = userBUS.getAllUsers();

        for (var user : listUsers) {
            users.put((int)user.getId(), user);
        }
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addToChartButtonContainer();
        addTitle();
        addSearchAndFilter();
        addTable();
    }

    private void addToChartButtonContainer() {
        toChartButton = new JButton("Biểu đồ");
        toChartButton.setBackground(ColorConfig.BLUE);
        toChartButton.setForeground(Color.WHITE);
        toChartButton.setPreferredSize(new Dimension(136, 44));

        toChartButton.addActionListener(this::OnChangeTab);

        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 50);
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(toChartButton);

        add(container);
    }

    private void addTitle() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 40);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("Danh sách học sinh tham gia");
        title.setFont(MyFont.fontHeader.deriveFont(24f));
        
        container.add(title);
        add(container);
    }
     private void addSearchAndFilter() {
        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(WIDTH, 50);
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        searchAndFilterContainer = new PanelBackground();
        searchAndFilterContainer.setAbsoluteSize(WIDTH, 50);
        searchAndFilterContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        JLabel searchTitle = new JLabel("Tìm kiếm: ");
        searchTitle.setFont(MyFont.fontHeader);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(450, 30));
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        setupSearchFieldEvent();

        searchAndFilterContainer.add(searchTitle);
        searchAndFilterContainer.add(searchField);
        searchAndFilterContainer.add(Box.createRigidArea(new Dimension(20, 0)));

        JLabel searchByCbLabel = new JLabel("Tìm kiếm theo:");
        searchByCb = new JComboBox<>();
        searchByCb.setPreferredSize(new Dimension(150, 30));
        searchByCb.setModel(new DefaultComboBoxModel<>(new String[] { "ID", "ID học sinh", "Họ tên" }));
        searchByCb.addActionListener(e -> filterTableItems());

        JLabel orderFilterLabel = new JLabel("Thứ tự: ");
        orderFilter = new JComboBox<>();
        orderFilter.setPreferredSize(new Dimension(150, 30));
        setOrderFilterItems();
        orderFilter.addActionListener(e -> filterTableItems());

        searchAndFilterContainer.add(searchByCbLabel);
        searchAndFilterContainer.add(searchByCb);

        searchAndFilterContainer.add(Box.createRigidArea(new Dimension(20, 0)));

        searchAndFilterContainer.add(orderFilterLabel);
        searchAndFilterContainer.add(orderFilter);
    
        container.add(searchAndFilterContainer);

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(container);
    }

    private void setOrderFilterItems() {
        char order = 65;
        int examCount = examBUS.getExamCount(testExam.getTestCode());

        orderFilter.addItem(' ');
        for (int i = 0; i < examCount; i++) {
            orderFilter.addItem(order);
            order++;
        }

        orderFilter.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Character) {
                    Character order = (Character) value;

                    if (order == ' ') setText("Tất cả");
                    else setText(String.valueOf(order));
                }

                return this;
            }
        });
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
        resetTableItems();
        String query = searchField.getText().toLowerCase();

        char order = (char) orderFilter.getSelectedItem();
        if (order != ' ') applyOrderFilter();

        var searchBy = searchByCb.getSelectedItem().toString();
        List<ResultDTO> filterList;

        Predicate<ResultDTO> filter = getSearchFilter(searchBy, query);
        filterList = filterListResult.stream().filter(filter).toList();
    
        setTableItems(new ArrayList<>(filterList));
        renderTable();
    }

    private Predicate<ResultDTO> getSearchFilter(String searchBy, String query) {
        if (query.isEmpty()) return result -> true;

        switch (searchBy) {
            case "ID":
                return result -> {
                    try {
                        return result.getResNum() == Integer.parseInt(query);
                    }
                    catch (Exception ignore) {
                        return false;
                    }
                };

            case "ID học sinh":
                return result -> {
                    int id = Integer.parseInt(query);
                    return result.getUserId() == id;
                };

            case "Họ tên":
                return result -> {
                    UserDTO user = users.get(result.getUserId());
                    return user.getFullName().contains(query);
                };
        
            default:
                return null;
        }
    }

    private void applyOrderFilter() {
        char selectedOrder = (char) orderFilter.getSelectedItem();

        var list = filterListResult.stream()
                                   .filter(result -> {
                                        String exCode = result.getExCode();
                                        char order = exCode.charAt(exCode.length() - 1);
                                        return selectedOrder == order;
                                    })
                                    .toList();

        setTableItems(new ArrayList<>(list));
    }

    private void addTable() {
        studentTable = new JTable();

        studentTable.setFont(MyFont.fontText); // NOI18N
        studentTable.setModel(new DefaultTableModel(
            new Object [][]{},
            new String [] {
                "ID", "ID học sinh", "Họ tên", "Điểm", "Thứ tự", "Ngày thi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        studentTable.setAutoCreateRowSorter(true);

        styleTable();
        resetTableItems();
        renderTable();

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane);
    }

    private void styleTable() {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) studentTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        TableColumnModel columnModel = studentTable.getColumnModel();
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    private void renderTable() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.setRowCount(0);

        filterListResult.forEach(result -> {
            var user = users.get(result.getUserId());
            String exCode = result.getExCode();
            char order = exCode.charAt(exCode.length() - 1);

            model.addRow(new Object[] {
                result.getResNum(),
                user.getId(),
                user.getFullName(),
                result.getRsMark(),
                order,
                result.getRsDate()
            });
        });

        model.fireTableDataChanged();
        studentTable.setModel(model);
    }

    private void OnChangeTab(ActionEvent e) {
        for (var callback : onChangeTabListener) {
            callback.accept(this);
        }
    }

    public void addOnChangeTabListener(Consumer<PanelBackground> callback) {
        onChangeTabListener.add(callback);
    }

    private JTable studentTable;
    private PanelBackground searchAndFilterContainer;
    private JTextField searchField;
    private JButton toChartButton;
    private JComboBox<String> searchByCb;
    private JComboBox<Character> orderFilter;
}
