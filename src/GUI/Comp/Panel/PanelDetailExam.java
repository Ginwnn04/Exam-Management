package GUI.Comp.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import BUS.AnswerBUS;
import BUS.ExamBUS;
import BUS.QuestionBUS;
import BUS.ResultBUS;
import BUS.TestBUS;
import BUS.UserBus;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import DTO.ResultDTO;
import DTO.TestDTO;
import GUI.Comp.Swing.PanelBackground;
import GUI.Custom.ExportDocx;
import style.ColorConfig;
import style.MyFont;

import java.text.SimpleDateFormat;

public class PanelDetailExam extends javax.swing.JPanel {
    private ArrayList<String> testList = new ArrayList<>();
    private TestBUS testExamBUS = new TestBUS();
    private ExamBUS BUS = new ExamBUS();
    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();
    private ResultBUS resultBUS = new ResultBUS();
    private UserBus userBus = new UserBus();
    private ArrayList<Consumer<JPanel>> onChangeTabCallback = new ArrayList<>();

    private String examCode;
    private String testCode;
    private ResultDTO result;
    private List<QuestionDTO> questions;
    private HashMap<Integer, AnswerDTO> examAnswers;
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
    private JButton toScoreView;

    // questionId: { answerId: panel }
    private HashMap<Integer, HashMap<Integer, PanelAnswers>> answerPanels = new HashMap<>();

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

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
    }

    public PanelDetailExam(ResultDTO result) {
        this.result = result;
        
        this.examCode = result.getExCode();
        var exam = BUS.findByExCode(result.getExCode());  
        this.testCode = testExamBUS.findByExam(exam).getTestCode();

        initComponents();
        loadTestDetails();
        updateQuestions();
        setupResultDetail();

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
    }

    //#region User Exam View

    private void setupResultDetail() {
        jButton2.setVisible(false);
        var user = userBus.findByID(result.getUserId());
        label1.setText("Bài làm của thí sinh: " + user.getFullName());


        PanelBackground container = new PanelBackground();
        container.setAbsoluteSize(1200, 200);
        container.setLayout(new GridBagLayout());

        toScoreView = new JButton("Xem điểm");
        toScoreView.setFont(MyFont.fontText);
        toScoreView.setBackground(ColorConfig.BLUE);
        toScoreView.addActionListener(this::onChangeTab);

        container.add(toScoreView);
    }

    public void addOnChangeTabCallback(Consumer<JPanel> callback) {
        if (result == null) throw new NullPointerException("Not accepted when result is null");
        onChangeTabCallback.add(callback);
    }

    private void onChangeTab(ActionEvent e) {
        for (var callback : onChangeTabCallback) {
            callback.accept(this);
        }
    }

    //#endregion

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

            addAnswer(question.getId(), questionContainer);
            questionPanel.add(questionContainer);

            index++;
        }

        if (result != null) {
            try {
                checkResult();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        questionPanel.revalidate();
        questionPanel.repaint();
    }

    private void addAnswer(int questionId, JPanel questionContainer) {
        List<AnswerDTO> answers = answerBUS.getAnswerByQuestionId(questionId);
        
        HashMap<Integer, PanelAnswers> list = new HashMap<>();
        ArrayList<PanelAnswers> multiAnswers = new ArrayList<>();

        for (int i = 0; i < answers.size(); i++) {
            AnswerDTO answer = answers.get(i);
            PanelAnswers answerPanel = new PanelAnswers();
            answerPanel.setData((char) ('a' + i), answer.getContent(), answer.getPicture());
            list.put(answer.getId(), answerPanel);
            
            if (answer.isIsRight()) {
                fillAnswer(AnswerResult.RIGHT, answerPanel);
                if (result != null) multiAnswers.add(answerPanel);
            }

            questionContainer.add(answerPanel);
        }

        // if question is multi-answer in user exam detail
        if (multiAnswers.size() > 1) {
            fillMultiAnswer(multiAnswers);
            multiAnswers.clear();
        }
        
        answerPanels.put(questionId, list);
        calcHeight(answers, questionContainer, new ArrayList<>(list.values()));
    }

    private void fillMultiAnswer(List<PanelAnswers> multiAnswers) {
         for (var panel : multiAnswers) {
            fillAnswer(AnswerResult.MISS, panel);
         }
    }

    private void fetchData() {
        examAnswers = new HashMap<>();
        ArrayList<Integer> questionIds = new ArrayList<>(questions.stream().map(question -> question.getId()).toList());
        var list = answerBUS.findAnswersByListQuestionIds(questionIds);

        for (AnswerDTO answer : list) {
            examAnswers.put(answer.getId(), answer);
        }
    }

    /**
     * Call when detail exam is view exam result
     */
    private void checkResult() throws NullPointerException, JSONException, NumberFormatException {
        if (examAnswers == null) fetchData();

        String answerJSON = result.getRsAnswer();     
        JSONObject json = new JSONObject(answerJSON);
        
        for (var questionId : json.keySet()) {
            var pr = answerPanels.get(Integer.parseInt(questionId));

            try {
                JSONArray answerIds = (JSONArray) json.get(questionId);
                for (var item : answerIds) {
                    int id = (int) item;         
                    fillUserChoice(id, pr);
                }
            }
            catch (ClassCastException ex) {
                int id = (int) json.get(questionId);
                fillUserChoice(id, pr);
            }
        }
    }

    private void fillUserChoice(int userChoiceAnswerId, HashMap<Integer, PanelAnswers> pr) {
        var panel = pr.get(userChoiceAnswerId);
        var answerResult = examAnswers.get(userChoiceAnswerId);

        if (answerResult.isIsRight()) fillAnswer(AnswerResult.RIGHT, panel);
        else fillAnswer(AnswerResult.WRONG, panel);
    }

    private void fillAnswer(AnswerResult answerResult, PanelAnswers answerPanel) {
        switch (answerResult) {
            case RIGHT:
                answerPanel.selected(true, false); // Assuming single choice for simplicity
                answerPanel.setContentBackground(ColorConfig.RIGHT_ANSWER_COLOR);
                answerPanel.setContentForeGround(Color.GREEN.darker());
                break;

            case WRONG:
                answerPanel.selected(true, false);
                answerPanel.setContentBackground(ColorConfig.WRONG_ANSWER_COLOR);
                answerPanel.setContentForeGround(Color.RED);
                break;

            case MISS:
                answerPanel.selected(true, false);
                break;

            default:
                break;
        }
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

    private enum AnswerResult {
        RIGHT,
        WRONG,
        MISS
    }
}