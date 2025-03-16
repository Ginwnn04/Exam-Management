package GUI.Comp.Panel;

import GUI.Comp.ExamComp;
import GUI.Comp.DateChooser.DateChooserPopup;
import GUI.Comp.Swing.PanelBackground;
import GUI.Utils.Debounce;
import style.ColorConfig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

import BUS.ResultBUS;
import BUS.TestBUS;
import DTO.ResultDTO;
import DTO.TestDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Panel for entering exams.
 * 
 * Author: Minh Phuc
 */
public class PanelEnterExams extends javax.swing.JPanel {
    private DateChooserPopup startDateChooser;
    private DateChooserPopup endDateChooser;
    private TestBUS testExamBUS = new TestBUS();
    private ResultBUS resultBUS = new ResultBUS();
    private List<TestDTO> examList;
    private List<ExamComp> examComponents;
    private JTextField searchField;
    private javax.swing.JScrollPane scrollPane;

    private Consumer<JPanel> showFormCallbacck;

    /**
     * Creates new form PanelEnterExams
     */
    public PanelEnterExams(Consumer<JPanel> showFormCallback) {
        this.showFormCallbacck = showFormCallback;

        initComponents();
        initSearchField();
        loadExamComponents();
    }

    private void loadExamComponents() {
        examList = testExamBUS.getAll(true);
        examComponents = new ArrayList<>(); // Initialize examComponents as an empty list
        jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment

        for (TestDTO exam : examList) {
            ExamComp examComp = new ExamComp(this, exam);
            examComp.setPreferredSize(new Dimension(jPanel5.getWidth(), 100)); // Adjust height as needed
            examComp.setShowFormCallback(showFormCallbacck);
            jPanel5.add(examComp);
            examComponents.add(examComp); // Add examComp to examComponents list
        }

        jPanel5.revalidate(); // Revalidate to ensure the layout is updated
        jPanel5.repaint(); // Repaint to ensure the components are rendered
    }

    private void initSearchField() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding top and bottom
        searchPanel.setBackground(ColorConfig.TRANSPARENT);

        JLabel searchLabel = new JLabel("Tìm kiếm :");
        searchLabel.setPreferredSize(new Dimension(100, 30));

        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên đề thi...");
        searchField.setPreferredSize(new Dimension(300, 30));

        Debounce onSearch = new Debounce(() -> filterExamComponents(), 200);
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

        JLabel startDateLabel = new JLabel("Ngày bắt đầu:");
        startDateLabel.setPreferredSize(new Dimension(100, 30));
        startDateLabel.setFont(new java.awt.Font("Roboto", 0, 14));

        startDateChooser = new DateChooserPopup();
        startDateChooser.setPreferredSize(new Dimension(150, 30));
        startDateChooser.addOnDateChangedCallback(date -> filterExamComponents());

        JLabel endDateLabel = new JLabel("Ngày kết thúc:");
        endDateLabel.setPreferredSize(new Dimension(100, 30));
        endDateLabel.setFont(new java.awt.Font("Roboto", 0, 14));

        endDateChooser = new DateChooserPopup();
        endDateChooser.setPreferredSize(new Dimension(150, 30));
        endDateChooser.addOnDateChangedCallback(date -> filterExamComponents());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Add gap before start date
        searchPanel.add(startDateLabel);
        searchPanel.add(startDateChooser);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Add gap before end date
        searchPanel.add(endDateLabel);
        searchPanel.add(endDateChooser);

        content.add(searchPanel, java.awt.BorderLayout.PAGE_START);
    }

    private void filterExamComponents() {
        String query = searchField.getText().toLowerCase();
        Date startDate = startDateChooser.getDate();
        Date endDate = endDateChooser.getDate();

        jPanel5.removeAll();
        for (ExamComp exComp : examComponents) {
            boolean matchesQuery = query == null || exComp.getExamTitle().toLowerCase().contains(query);
            boolean matchesDate = true;

            if (startDate != null && endDate != null) {
                Date examDate = exComp.getTestDate(); // Assuming ExamComp has a method getExamDate()
                matchesDate = (examDate.equals(startDate) || examDate.after(startDate)) &&
                        (examDate.equals(endDate) || examDate.before(endDate));
            }

            if (matchesQuery && matchesDate) {
                jPanel5.add(exComp);
            }
        }
        jPanel5.revalidate();
        jPanel5.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();

        setMaximumSize(new java.awt.Dimension(1200, 765));
        setMinimumSize(new java.awt.Dimension(1200, 765));
        setPreferredSize(new java.awt.Dimension(1200, 765));
        setVerifyInputWhenFocusTarget(false);
        setLayout(new java.awt.BorderLayout());
        setBackground(ColorConfig.GREY_COLOR_BG);

        content = new PanelBackground();
        content.setAbsoluteSize(1160, 745);
        content.setLayout(new BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 10));
        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(10, 745));
        add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setPreferredSize(new java.awt.Dimension(1200, 10));
        add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setPreferredSize(new java.awt.Dimension(10, 745));
        add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(java.awt.Color.WHITE);
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jPanel5.setPreferredSize(new java.awt.Dimension(1180, 745));
        scrollPane.setViewportView(jPanel5);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new java.awt.Dimension(1180, 745));

        content.add(scrollPane, java.awt.BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private PanelBackground content;
    // End of variables declaration//GEN-END:variables
}