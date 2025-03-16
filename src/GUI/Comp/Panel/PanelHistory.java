package GUI.Comp.Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ResultBUS;
import BUS.TestBUS;
import DTO.ResultDTO;
import DTO.TestDTO;
import DTO.UserDTO;
import GUI.Comp.DateChooser.DateChooserPopup;
import GUI.Comp.Panel.Result.PanelAfterExam;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.HistoryItemPanel;
import GUI.Utils.Debounce;
import GUI.Utils.UserSession;
import style.ColorConfig;
import style.MyFont;

public class PanelHistory extends PanelBackground {
    private final int WIDTH = 1160;
    private final int HEIGHT = 745;

    private Consumer<JPanel> showFormCallback;
    private HashMap<String, TestDTO> tests = new HashMap<>();
    private ArrayList<ResultDTO> results = new ArrayList<>();

    private HashMap<Short, Boolean> isUserPass = new HashMap<>();

    private ArrayList<ResultDTO> displayList = new ArrayList<>();

    private UserDTO user;
    private ResultBUS resultBUS = new ResultBUS();
    private TestBUS testBUS = new TestBUS();

    public PanelHistory(Consumer<JPanel> showFormCallback) {
        user = UserSession.getInstance().getCurrentUser();
        this.showFormCallback = showFormCallback;

        prepareResource();
        initComponents();
    }

    private void prepareResource() {
        var testList = testBUS.getAll();
        var resultList = resultBUS.getAllByUser(user);

        for (TestDTO test : testList) {
            tests.put(test.getTestCode(), test);
        }

        for (ResultDTO result : resultList) {
            var test = getTest(result.getExCode());

            int maxScore = testBUS.getMaxScore(test.getTestCode());
            boolean isPass = result.getRsMark() >= maxScore * 0.5;

            results.add(result);
            isUserPass.put(result.getResNum(), isPass);
        }
    }

    private void initComponents() {
        setAbsoluteSize(1200, 765);
        setLayout(new GridBagLayout());
        setBackground(ColorConfig.GREY_COLOR_BG);

        content = new PanelBackground();
        content.setAbsoluteSize(WIDTH, HEIGHT);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        addSearchAndFilterContainer();
        setupHistory();

        add(content);
    }

    private void addSearchAndFilterContainer() {
        PanelBackground container = new PanelBackground();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAbsoluteSize(WIDTH, 100);
        container.setBorder(new EmptyBorder(10, 10, 10, 10));


        container.add(getSearchContainer());
        container.add(getFilterByDateContainer());

        content.add(container);
        content.add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private PanelBackground getSearchContainer() {
        PanelBackground container = new PanelBackground();
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        container.setAbsoluteSize(WIDTH, 50);

        JLabel searchLabel = new JLabel("Tìm kiếm: ");
        searchLabel.setFont(MyFont.fontHeader);

        searchField = new JTextField();
        searchField.setFont(MyFont.fontText);
        searchField.setPreferredSize(new Dimension(400, 30));
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        searchField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        container.add(searchLabel);
        container.add(searchField);

        container.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel searchByLabel = new JLabel("Tìm kiếm theo: ");
        searchByLabel.setFont(MyFont.fontText);

        searchByCb = new JComboBox<>(new String[] {"Tên đề thi", "Mã đề thi"});
        searchByCb.setFont(MyFont.fontText);
        searchByCb.setPreferredSize(new Dimension(150, 30));
        
        container.add(searchByLabel);
        container.add(searchByCb);
        container.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel resultLabel = new JLabel("Kết quả: ");
        resultLabel.setFont(MyFont.fontText);

        resultFilterCb = new JComboBox<>(new String[] {"Tất cả", "Đạt", "Không đạt"});
        resultFilterCb.setFont(MyFont.fontText);
        resultFilterCb.setPreferredSize(new Dimension(150, 30));

        container.add(resultLabel);
        container.add(resultFilterCb);

        setupFilterEvent();

        return container;
    }

    private PanelBackground getFilterByDateContainer() {
        PanelBackground container = new PanelBackground();
        container.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        container.setAbsoluteSize(WIDTH, 50);

        JLabel startDateLabel = new JLabel("Ngày thi: ");
        startDateLabel.setFont(MyFont.fontText);

        Debounce onFilter = new Debounce(() -> onSearch(), 200);

        startDateChooser = new DateChooserPopup();
        startDateChooser.setPreferredSize(new Dimension(150, 30));
        startDateChooser.addOnDateChangedCallback(date -> onFilter.execute());

        container.add(startDateLabel);
        container.add(startDateChooser);
        container.add(new JLabel("-"));

        endDateChooser = new DateChooserPopup();
        endDateChooser.setPreferredSize(new Dimension(150, 30));
        endDateChooser.addOnDateChangedCallback(date -> onFilter.execute());

        container.add(endDateChooser);
        return container;
    }

    private void setupFilterEvent() {
        Debounce onSearch = new Debounce(() -> onSearch(), 200);

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

        searchByCb.addActionListener(e -> onSearch.execute());
        resultFilterCb.addActionListener(e -> onSearch.execute());
    }

    private void setupHistory() {
        historyContainer = new PanelBackground();
        historyContainer.setLayout(new BoxLayout(historyContainer, BoxLayout.Y_AXIS));

        addStudentHistory();
        addScroll();
    }

    private void addScroll() {
        scrollableHistory = new JScrollPane(historyContainer);
        scrollableHistory.setMinimumSize(new Dimension(WIDTH - 20, 600));
        scrollableHistory.setMaximumSize(new Dimension(WIDTH - 20, 600));
        scrollableHistory.getVerticalScrollBar().setUnitIncrement(10);
        scrollableHistory.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        content.add(scrollableHistory);
    }

    private TestDTO getTest(String exCode) {
        int length = exCode.length();
        String testCode = exCode.substring(0, length - 1);
        return tests.get(testCode);
    }

    private void addStudentHistory() { 
        displayList = new ArrayList<>(results);
        renderStudentHistory(displayList);
    }

    private void renderStudentHistory(List<ResultDTO> list) {
        historyContainer.removeAll();

        for (ResultDTO result : list) {
            var test = getTest(result.getExCode());
            boolean isPass = isUserPass.get(result.getResNum());

            HistoryItemPanel historyItem = new HistoryItemPanel(result, test, isPass);
            historyItem.addActionCallback(this::handleToDetailExam);
            
            historyContainer.add(historyItem);
            historyContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        historyContainer.validate();
        historyContainer.repaint();
    }

    private void handleToDetailExam(ResultDTO result) {
        var afterExam = new PanelAfterExam(result, false);
        afterExam.addOnBackToPreviousClickCallback(() -> showFormCallback.accept(this));

        showFormCallback.accept(afterExam);
    }

    private void onSearch() {
        String query = searchField.getText();
        displayList.clear();

        for (var result : results) {
            if (!isContainQuery(query, result)) continue;
            if (!isMatchResult(result)) continue;
            if (!isMatchesDate(result)) continue;

            displayList.add(result);
        }

        renderStudentHistory(displayList);
    }

    private boolean isContainQuery(String query, ResultDTO result) {
        String searchBy = (String) searchByCb.getSelectedItem();
        var test = getTest(result.getExCode());
        query = query.toLowerCase();

        switch (searchBy) {
            case "Tên đề thi":
                return test.getTitle().toLowerCase().contains(query);

            case "Mã đề thi":
                return result.getExCode().toLowerCase().contains(query);
        
            default:
                return false;
        }
    }

    private boolean isMatchResult(ResultDTO result) {
        String selectedResult = (String) resultFilterCb.getSelectedItem();
        boolean isPass = isUserPass.get(result.getResNum());

        switch (selectedResult) {
            case "Tất cả":
                return true;
                
            case "Đạt":
                return isPass;

            case "Không đạt":
                return !isPass;

            default:
                return false;
        }
    }

    private boolean isMatchesDate(ResultDTO result) {
        Date date = result.getRsDate();
        Date startDate = startDateChooser.getDate();
        Date endDate = endDateChooser.getDate();

        boolean isMatchesStart = startDate != null ? date.equals(startDate) || date.after(startDate) : true;
        boolean isMatchesEnd = endDate != null ? date.equals(endDate) || date.before(endDate) : true;

        return isMatchesStart && isMatchesEnd;
    }

    private PanelBackground content;
    private PanelBackground historyContainer;
    private JTextField searchField;
    private JComboBox<String> searchByCb;
    private DateChooserPopup startDateChooser;
    private DateChooserPopup endDateChooser;
    private JComboBox<String> resultFilterCb;
    private JScrollPane scrollableHistory;
}
