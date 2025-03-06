package GUI.Comp.Dialog.Statistics;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;

import BUS.ResultBUS;
import DTO.ExamDTO;
import GUI.Comp.Swing.PanelBackground;
import DTO.ResultDTO;
import DTO.UserDTO;

public class DialogStatistics extends JDialog {
    private final Dimension DIALOG_SIZE = new Dimension(1200, 768);
    private ExamDTO exam;
    private ResultBUS resultBUS = new ResultBUS();

    private List<ResultDTO> listResult;
    // private List<UserDTO> listStudents;

    public DialogStatistics() {
        initComponents();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    public DialogStatistics(ExamDTO exam) {
        this.exam = exam;
        listResult = resultBUS.getAllByExam(exam);

        initComponents();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setMinimumSize(DIALOG_SIZE);
        setPreferredSize(DIALOG_SIZE);

        chart = new PanelChart(exam, listResult);
        chart.addOnChangeTabListener(this::changeTab);
        
        studentStatistics = new PanelStudentsStatistics(listResult, exam);
        studentStatistics.addOnChangeTabListener(this::changeTab);

        main = new PanelBackground();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(chart);

        add(main);
    }

    private void changeTab(PanelBackground oldContent) {
        main.remove(oldContent);

        if (oldContent instanceof PanelChart) {
            main.add(studentStatistics);
        }
        else main.add(chart);

        main.revalidate();
        main.repaint();
    }

    private PanelBackground main;
    private PanelChart chart;
    private PanelStudentsStatistics studentStatistics;
}
