package GUI.Comp.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import BUS.AnswerBUS;
import BUS.ExamBUS;
import BUS.QuestionBUS;
import BUS.TestBUS;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import DTO.TestDTO;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.ExportDocx;
import java.text.SimpleDateFormat;

public class PanelDetailExam extends javax.swing.JPanel {
    private ArrayList<String> testList = new ArrayList<>();
    private TestBUS testExamBUS = new TestBUS();
    private ExamBUS BUS = new ExamBUS();
    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();
    private String examCode;
    private String testCode;
    private List<QuestionDTO> questions;
    private GUI.Comp.Swing.PanelBackground panelBackground;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel questionPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jButton2;

    /**
     * Creates new form PanelDetailExam
     * 
     * @param examCode
     * @param testCode
     */
    public PanelDetailExam(String examCode, String testCode) {
        this.examCode = examCode;
        this.testCode = testCode;
        initComponents();
        loadTestDetails();
        updateQuestions();
    }

    private void loadTestDetails() {
        TestDTO testExam = testExamBUS.findByTestCode(testCode);
        if (testExam != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            jLabel2.setText(dateFormat.format(testExam.getTestDate()));
            jLabel3.setText(testExam.getTestTime() + " phút");
        }
    }

    private void calcHeight(List<AnswerDTO> listAnsw, JPanel pnAnsw, List<PanelAnswers> listAnswComponent) {
        int cntHeightNotImg = 0;
        int cntHeightHaveImg = 0;
        int space = (listAnsw.size() - 1) * 15;
        for (PanelAnswers x : listAnswComponent) {
            if (x.getPath() == null || x.getPath().isEmpty())
                cntHeightNotImg++;
            else
                cntHeightHaveImg++;
        }
        int totalSpace = space + (cntHeightNotImg * 60) + (cntHeightHaveImg * 200) + 50; // +50 sai so
        // System.out.println(totalSpace);
        pnAnsw.setPreferredSize(new Dimension(500, totalSpace));
    }

    private void updateQuestions() {
        label1.setText("Xem chi tiết đề thi");
        label1.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 18));
        jButton2.setText("Xuất DOCX");
        questions = questionBUS.getQuestionByExamCode(examCode);

        questionPanel.removeAll();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

        int index = 1;
        for (QuestionDTO question : questions) {
            JPanel questionContainer = new JPanel();
            questionContainer.setLayout(new BoxLayout(questionContainer, BoxLayout.Y_AXIS));
            questionContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
            questionContainer.setBackground(Color.WHITE);
            // questionContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
            // questionContainer.setPreferredSize(new
            // Dimension(jScrollPane1.getViewport().getWidth() - 20, 150));

            JLabel questionLabel = new JLabel("Câu " + index + ": " + question.getContent() + " ("
                    + getDifficultyPoints(question.getLevel()) + " điểm)");
            PanelAnswers questionLabelPanel = new PanelAnswers();
            questionLabelPanel.setData('\0', "Câu " + index + ": " + question.getContent() + " ("
                    + getDifficultyPoints(question.getLevel()) + " điểm)", null);
            questionContainer.add(questionLabelPanel);

            List<AnswerDTO> answers = answerBUS.getAnswerByQuestionId(question.getId());
            List<PanelAnswers> answerPanels = new ArrayList<>();
            for (int i = 0; i < answers.size(); i++) {
                AnswerDTO answer = answers.get(i);
                PanelAnswers answerPanel = new PanelAnswers();
                answerPanel.setData((char) ('a' + i), answer.getContent(), answer.getPicture());
                answerPanels.add(answerPanel);
                if (answer.isIsRight()) {
                    answerPanel.selected(true, false); // Assuming single choice for simplicity
                }
                questionContainer.add(answerPanel);
            }
            calcHeight(answers, questionContainer, answerPanels);
            questionPanel.add(questionContainer);

            index++;
        }

        questionPanel.revalidate();
        questionPanel.repaint();
    }

    private String getDifficultyPoints(String level) {
        switch (level) {
            case "easy":
                return "1";
            case "medium":
                return "2";
            case "diff":
                return "3";
            default:
                return "";
        }
    }

    // Sự kiện xuất đề thi
    // private void exportExam(List<QuestionDTO> dataList, String examCode) {
    // // Gọi hàm xuất đề thi
    // ExportDocx.exportExamToDocx(examCode, dataList, answerBUS);
    // JOptionPane.showMessageDialog(this, "Xuất đề thi thành công!");
    // }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents


    private void initComponents() {

        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionPanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        label1.setFont(new java.awt.Font("Roboto", 1, 18));
        label1.setText("Xem chi tiết đề thi");

        label2.setFont(new java.awt.Font("Roboto", 0, 14));
        label2.setText("Tên tổ hợp:");

        label3.setFont(new java.awt.Font("Roboto", 0, 14));
        label3.setText("Ngày thi:");

        label4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        label4.setText("Thời gian làm bài : ");

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel1.setText(examCode);

        questionPanel.setBackground(Color.WHITE);
        jScrollPane1.setViewportView(questionPanel);

        jButton2.setBackground(new java.awt.Color(53, 80, 154));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xuất DOCX");

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // exportExam(questions, examCode);
                ExportDocx.exportExamToDocx(examCode, questions, answerBUS);
            }
        });

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel2.setText("");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabel3.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 1100,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(27, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addContainerGap(27, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents
}