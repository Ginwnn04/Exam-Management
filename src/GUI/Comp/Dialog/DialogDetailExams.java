package GUI.Comp.Dialog;
import javax.swing.JDialog;

import GUI.Comp.Panel.PanelDetailExam;

public class DialogDetailExams extends JDialog{
    public DialogDetailExams(java.awt.Frame parent, boolean modal,String examCode, String testCode) {
        super(parent, modal);
        PanelDetailExam panel = new PanelDetailExam(examCode,testCode);
        this.add(panel);
        this.setSize(1200, 700);
        this.setModal(true);
        this.setLocationRelativeTo(null);

    }
}
