/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Comp.Dialog;

import BUS.AnswerBUS;
import BUS.LogBUS;
import BUS.QuestionBUS;
import BUS.ResultBUS;
import DTO.AnswerDTO;
import DTO.LogDTO;
import DTO.QuestionDTO;
import DTO.ResultDTO;
import DTO.UserDTO;
import GUI.Comp.Panel.PanelAnswers;
import GUI.Utils.DoExamLogger;
import GUI.Utils.Pair;
import GUI.Utils.UserSession;
import Helper.Format;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.jfree.data.json.impl.JSONObject;

import style.ColorConfig;
import style.MyFont;

/**
 *
 * @author pc
 */
public class DialogDoExam extends javax.swing.JDialog {
    private List<PanelAnswers> listAnswComponent = new ArrayList<>();
    private Timer timer;
    private String exCode;
    private long testTime;
    private int nbQuestionCurrent = 1;

    private List<QuestionDTO> listQuestion = new ArrayList<>();
    private List<AnswerDTO> listAnsw = new ArrayList<>();
    private List<JButton> listBtnQuestion = new ArrayList<>();
    private QuestionBUS questionBUS = new QuestionBUS();
    private AnswerBUS answerBUS = new AnswerBUS();
    private ResultBUS resultBUS = new ResultBUS();
    private HashMap<Integer, Set<Character>> trackingQuestion = new HashMap<>();
    private HashMap<Integer, Set<Integer>> userAnswers = new HashMap<>();
    private Boolean isMultiChoice = false; // Lưu những câu nào đang là only choice hoặc multi choice
    private DoExamLogger logger;

    /**
     * Creates new form DialogDoExam
     */
    public DialogDoExam(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();
        // setLocationRelativeTo(null);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Set full màn hình
        startCountdown();
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
        enableLog();
        initDataQuestion(exCode);
        renderQuestion();
    }

    //#region Log
    public void enableLog() {
        logger = new DoExamLogger(() -> trackingQuestion, exCode);
    }

    
    //#endregion

    public void setTime(long testTime) {
        this.testTime = testTime * 60 * 1000;
    }

    public void setTitleExam(String title, String exCode) {
        lbTitle.setText("Bài kiếm tra " + title + " - Mã đề: " + exCode);
    }

    private void initDataQuestion(String exCode) {
        listQuestion = questionBUS.getQuestionByExamCode(exCode);
        initDataButtonQuestion();
    }

    private void initDataButtonQuestion() {
        for (int i = 0; i < listQuestion.size(); i++) {
            JButton btn = new JButton((i + 1) + "");
            btn.setMargin(new Insets(5, 5, 5, 5));
            btn.setPreferredSize(new Dimension(35, 35));
            btn.setFont(MyFont.fontText_14);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int numberQuestion = Integer.parseInt(btn.getText());
                    logger.writeQuestionChoice(numberQuestion, nbQuestionCurrent);
                    nbQuestionCurrent = numberQuestion;
                    lbNbQuestion.setText("Câu " + nbQuestionCurrent);

                    renderQuestion();
                }
            });
            listBtnQuestion.add(btn);
        }
        renderMapQuestion();
    }

    private void renderMapQuestion() {
        for (JButton btn : listBtnQuestion) {
            pnMap.add(btn);
        }
    }

    private void renderQuestion() {
        QuestionDTO questionDTO = listQuestion.get(nbQuestionCurrent - 1);
        lbQuestion.setText("<html>" + questionDTO.getContent() + "</html>");
        // System.out.println(questionDTO.getId() + " " + questionDTO.getContent());
        if (!questionDTO.getPicture().isEmpty()) {
            String pathImage = Paths.get(System.getProperty("user.dir") + "/src/GUI/Image/" + questionDTO.getPicture())
                    .toString();
            Image img = new ImageIcon(pathImage).getImage().getScaledInstance(300, 207, Image.SCALE_SMOOTH);
            lbQuestion.setIcon(new ImageIcon(img));
        }
        revalidate();
        repaint();
        renderMultiChoice();
        renderAnsw(trackingQuestion.get(nbQuestionCurrent));
    }

    private void renderMultiChoice() {
        listAnsw = answerBUS.getAnswerByQuestionId(listQuestion.get(nbQuestionCurrent - 1).getId());
        int cnt = 0;
        for (AnswerDTO answ : listAnsw) {
            if (answ.isIsRight()) {
                cnt++;
            }
        }

        if (cnt >= 2) {
            isMultiChoice = true;
        } else {
            isMultiChoice = false;
        }
    }

    private void renderAnsw(Set<Character> trackingAnsw) {
        listAnswComponent.clear();
        pnAnsw.removeAll();
        var question = listQuestion.get(nbQuestionCurrent - 1);
        listAnsw = answerBUS.getAnswerByQuestionId(question.getId());

        char order = 'A';
        for (AnswerDTO answ : listAnsw) {
            if (order > 'D') order = 'A';

            String pathImg = null;
            if (!answ.getPicture().isEmpty()) {
                pathImg = Paths.get(System.getProperty("user.dir") + "/src/GUI/Image/" + answ.getPicture()).toString();
            }
            PanelAnswers pnAnswItem = new PanelAnswers();
            pnAnswItem.setData(order, "<html>" + answ.getContent() + "</html>", pathImg);
            // System.out.println(order + " " + answ.getContent());
            if (pnAnswItem.getPath() == null || pnAnswItem.getPath().isEmpty()) {
                pnAnswItem.setPreferredSize(new Dimension(1020, 60));
            } else {
                pnAnswItem.setPreferredSize(new Dimension(1020, 200));

            }
            if (trackingAnsw == null) {
                pnAnswItem.selected(false, isMultiChoice);
            } else {
                trackingAnsw.forEach(value -> {
                    System.out.println(value + " " + pnAnswItem.getOrder());
                    if (value == pnAnswItem.getOrder())
                        pnAnswItem.selected(true, isMultiChoice);
                });
            }

            pnAnswItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onAnswerClick(pnAnswItem, answ, question);
                }

            });

            listAnswComponent.add(pnAnswItem);
            pnAnsw.add(pnAnswItem);

            order++;
        }
        calcHeight();
        revalidate();
        repaint();
    }

    private void onAnswerClick(PanelAnswers pnAnswItem, AnswerDTO answ, QuestionDTO question) {
        if (!isMultiChoice) {
            removeAllSelect();
        }

        boolean isSelected = pnAnswItem.isSelected();
        pnAnswItem.selected(!isSelected, isMultiChoice);
        
        listBtnQuestion.get(nbQuestionCurrent - 1).setBackground(ColorConfig.BLUE);
        listBtnQuestion.get(nbQuestionCurrent - 1).setForeground(ColorConfig.WHITE_COLOR_BG);
        Set<Character> listChoice;
        Set<Integer> answers;

        if (!trackingQuestion.containsKey(nbQuestionCurrent)) {
            Pair<Set<Character>, Set<Integer>> pr = addNewTrackingAnswer(pnAnswItem.getOrder(), answ.getId(), question.getId());
            listChoice = pr.getFirst();
            answers = pr.getLast();
            logTrackingQuestion();

            return;
        } 
        
        listChoice = trackingQuestion.get(nbQuestionCurrent);
        answers = userAnswers.get(question.getId());

        if (isMultiChoice) {
            if (!isSelected) {
                listChoice.add(pnAnswItem.getOrder());
                answers.add(answ.getId());
                logger.writeUserChoice(pnAnswItem.getOrder(), nbQuestionCurrent, true);
            }
            else {
                listChoice.remove(pnAnswItem.getOrder());
                answers.remove(answ.getId());
                logger.writeRemoveUserChoice(nbQuestionCurrent, pnAnswItem.getOrder());
            }
        }
        else {
            logger.writeUserChoice(pnAnswItem.getOrder(), nbQuestionCurrent, false);
            listChoice.clear();
            answers.clear();

            listChoice.add(pnAnswItem.getOrder());
            answers.add(answ.getId());
        }

        logTrackingQuestion();
        trackingQuestion.put(nbQuestionCurrent, listChoice);
        userAnswers.put(question.getId(), answers);
    }

    private void logTrackingQuestion() {
        trackingQuestion.forEach((key, value) -> {
            System.out.println("======================");
            System.out.println("Key: " + key);
            value.forEach(System.out::println);
        });
    }

    private Pair<Set<Character>, Set<Integer>> addNewTrackingAnswer(char order, int answerId, int questionId) {
        logger.writeUserChoice(order, nbQuestionCurrent, false);

        Set<Character> listChoice = new HashSet<>();
        Set<Integer> answers = new HashSet<>();

        listChoice.add(order);
        answers.add(answerId);

        userAnswers.put(questionId, answers);
        trackingQuestion.put(nbQuestionCurrent, listChoice);

        return new Pair<>(listChoice, answers);
    }

    private void removeAllSelect() {
        for (PanelAnswers x : listAnswComponent) {
            x.selected(false, false);
        }
    }

    private void calcHeight() {
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

    // miliseconds
    private void startCountdown() {
        if (testTime == 0) {
            lbTime.setText("");
        } else {
            lbTime.setText(Format.formatTime.format(testTime));
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testTime -= 1000;

                lbTime.setText(Format.formatTime.format(testTime));

                if (testTime <= 0) {
                    timer.stop();
                    lbTime.setText("Hết giờ!");
                }
            }
        });
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new GUI.Comp.Swing.PanelBackground();
        pnQuestion = new GUI.Comp.Swing.PanelBackground();
        lbNbQuestion = new javax.swing.JLabel();
        lbQuestion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnAnsw = new GUI.Comp.Swing.PanelBackground();
        lbNbQuestion1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelBackground2 = new GUI.Comp.Swing.PanelBackground();
        panelBackground3 = new GUI.Comp.Swing.PanelBackground();
        lbTitle = new javax.swing.JLabel();
        panelBackground1 = new GUI.Comp.Swing.PanelBackground();
        lbTime = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnMap = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        main.setBackground(new java.awt.Color(247, 247, 247));

        lbNbQuestion.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lbNbQuestion.setText("Câu 1");

        lbQuestion.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lbQuestion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbQuestion.setText(
                "<html>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</html>");
        lbQuestion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbQuestion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lbQuestion.setMaximumSize(new java.awt.Dimension(200, 19));
        lbQuestion.setPreferredSize(new java.awt.Dimension(200, 19));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(345, 100));

        pnAnsw.setPreferredSize(new java.awt.Dimension(500, 340));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 15);
        flowLayout2.setAlignOnBaseline(true);
        pnAnsw.setLayout(flowLayout2);
        jScrollPane1.setViewportView(pnAnsw);

        lbNbQuestion1.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lbNbQuestion1.setText("Chú thích");

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel1.setText("Nhiều đáp án");

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel2.setText("Một đáp án");

        panelBackground2.setBackground(new java.awt.Color(215, 220, 235));
        panelBackground2.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout panelBackground2Layout = new javax.swing.GroupLayout(panelBackground2);
        panelBackground2.setLayout(panelBackground2Layout);
        panelBackground2Layout.setHorizontalGroup(
                panelBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 70, Short.MAX_VALUE));
        panelBackground2Layout.setVerticalGroup(
                panelBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));

        panelBackground3.setBackground(new java.awt.Color(192, 232, 213));
        panelBackground3.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout panelBackground3Layout = new javax.swing.GroupLayout(panelBackground3);
        panelBackground3.setLayout(panelBackground3Layout);
        panelBackground3Layout.setHorizontalGroup(
                panelBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 70, Short.MAX_VALUE));
        panelBackground3Layout.setVerticalGroup(
                panelBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));

        javax.swing.GroupLayout pnQuestionLayout = new javax.swing.GroupLayout(pnQuestion);
        pnQuestion.setLayout(pnQuestionLayout);
        pnQuestionLayout.setHorizontalGroup(
                pnQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnQuestionLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(pnQuestionLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1074,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbNbQuestion)
                                        .addComponent(lbQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 1104,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnQuestionLayout.createSequentialGroup()
                                                .addComponent(lbNbQuestion1)
                                                .addGap(18, 18, 18)
                                                .addGroup(pnQuestionLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnQuestionLayout.createSequentialGroup()
                                                                .addComponent(panelBackground3,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel1))
                                                        .addGroup(pnQuestionLayout.createSequentialGroup()
                                                                .addComponent(panelBackground2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel2)))))
                                .addGap(63, 63, 63)));
        pnQuestionLayout.setVerticalGroup(
                pnQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnQuestionLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbNbQuestion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnQuestionLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbNbQuestion1)
                                        .addGroup(pnQuestionLayout.createSequentialGroup()
                                                .addGroup(pnQuestionLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(panelBackground2,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(pnQuestionLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(panelBackground3,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        lbTitle.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbTitle.setText("BÀI THI KIỂM TRA TOÁN LỚP 1");

        lbTime.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbTime.setText(" ");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel4.setText("Thời gian làm bài:");

        jButton1.setBackground(new java.awt.Color(53, 80, 154));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("NỘP BÀI");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pnMap.setBackground(new java.awt.Color(255, 255, 255));
        pnMap.setMaximumSize(new java.awt.Dimension(200, 32767));
        pnMap.setMinimumSize(new java.awt.Dimension(200, 36));
        pnMap.setPreferredSize(new java.awt.Dimension(200, 506));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10);
        flowLayout1.setAlignOnBaseline(true);
        pnMap.setLayout(flowLayout1);

        javax.swing.GroupLayout panelBackground1Layout = new javax.swing.GroupLayout(panelBackground1);
        panelBackground1.setLayout(panelBackground1Layout);
        panelBackground1Layout.setHorizontalGroup(
                panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBackground1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(panelBackground1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panelBackground1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(15, 15, 15)
                                                .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnMap, javax.swing.GroupLayout.PREFERRED_SIZE, 226,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)));
        panelBackground1Layout.setVerticalGroup(
                panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBackground1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(panelBackground1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbTime)
                                        .addComponent(jLabel4))
                                .addGap(28, 28, 28)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(pnMap, javax.swing.GroupLayout.PREFERRED_SIZE, 491,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
                mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(pnQuestion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50,
                                        Short.MAX_VALUE)
                                .addComponent(panelBackground1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbTitle)
                                .addGap(611, 611, 611)));
        mainLayout.setVerticalGroup(
                mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lbTitle)
                                .addGap(27, 27, 27)
                                .addGroup(
                                        mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(pnQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 764,
                                                        Short.MAX_VALUE))
                                .addContainerGap(42, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 548, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        if (logger != null) logger.save();
        var result = getUserAnswersAndMark();
        var user = UserSession.getInstance().getCurrentUser();

        ResultDTO model = ResultDTO.builder()
                                    .setExCode(exCode)
                                    .setUserId(user.getId())
                                    .setRsDate(new Date(System.currentTimeMillis()))
                                    .setRsMark(result.getLast())
                                    .setRsAnswer(result.getFirst());

        resultBUS.create(model);
        dispose();
    }// GEN-LAST:event_jButton1ActionPerformed

    
    @SuppressWarnings("unchecked")
    private Pair<String, Integer> getUserAnswersAndMark() {
        JSONObject json = new JSONObject();
        int mark = 0;

        for (int questionId : userAnswers.keySet()) {
            var answers = userAnswers.get(questionId);
            var answerIds = gatherAnswer(answers);

            mark += resultBUS.getScore(questionId, answerIds);

            if (answerIds.size() == 1) json.put(questionId, answerIds.peek());
            else json.put(questionId, answerIds);
        }

        System.out.println(json.toString());

        return new Pair<>(json.toString(), mark);
    }

    private Queue<Integer> gatherAnswer(Set<Integer> answers) {
        // ArrayList<Integer> result = new ArrayList<>();
        Queue<Integer> result = new LinkedList<>();

        for (var answerId : answers) {
            result.add(answerId);
        }

        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogDoExam dialog = new DialogDoExam(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbNbQuestion;
    private javax.swing.JLabel lbNbQuestion1;
    private javax.swing.JLabel lbQuestion;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbTitle;
    private GUI.Comp.Swing.PanelBackground main;
    private GUI.Comp.Swing.PanelBackground panelBackground1;
    private GUI.Comp.Swing.PanelBackground panelBackground2;
    private GUI.Comp.Swing.PanelBackground panelBackground3;
    private GUI.Comp.Swing.PanelBackground pnAnsw;
    private javax.swing.JPanel pnMap;
    private GUI.Comp.Swing.PanelBackground pnQuestion;
    // End of variables declaration//GEN-END:variables
}
