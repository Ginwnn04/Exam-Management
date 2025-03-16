package GUI.Comp.Dialog.Statistics;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;

import BUS.ResultBUS;
import DTO.ExamDTO;
import GUI.Comp.Swing.PanelBackground;
import DTO.ResultDTO;
import DTO.TestDTO;
import DTO.UserDTO;

public class DialogStatistics extends JDialog {
    private final Dimension DIALOG_SIZE = new Dimension(1200, 768);
    private TestDTO testExam;
    private ResultBUS resultBUS = new ResultBUS();

    private List<ResultDTO> listResult;
    // private List<UserDTO> listStudents;

    public DialogStatistics() {
        initComponents();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    public DialogStatistics(TestDTO testExam, JFrame parent, boolean modal) {
        super(parent, modal);
        this.testExam = testExam;
        listResult = resultBUS.getAllByTestExam(testExam);

        initComponents();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setMinimumSize(DIALOG_SIZE);
        setPreferredSize(DIALOG_SIZE);
        setLayout(new GridBagLayout());

        chart = new PanelChart(testExam, listResult);
        chart.addOnChangeTabCallback(this::handleChangeTab);
        
        studentStatistics = new PanelStudentsStatistics(testExam, listResult);
        studentStatistics.addOnChangeTabCallback(this::handleChangeTab);

        main = new PanelBackground();
        main.setAbsoluteSize(1180, 720);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(chart);

        add(main);
    }

    private void handleChangeTab(PanelBackground oldContent, PanelBackground nextContent) {
        main.remove(oldContent);

        if (nextContent != null) main.add(nextContent);
        else if (oldContent instanceof PanelChart) {
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
