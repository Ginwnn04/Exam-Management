/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Comp.Dialog;

import GUI.Comp.Panel.PanelAnswers;
import Helper.Format;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author pc
 */
public class DialogDoExam extends javax.swing.JDialog {
    private List<PanelAnswers> listAnsw = new ArrayList<>();
    private Timer timer;
     long startTime = 100000;

    /**
     * Creates new form DialogDoExam
     */
    public DialogDoExam(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
//        setLocationRelativeTo(null);
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Set full màn hình
        initAnsw();
        startCountdown();
    }

    private void initAnsw() {
        final int size = 5;
        char order = 'E';
        for (int i = 0; i < size; i++) {
            PanelAnswers answ = new PanelAnswers();
            if (order == 'A') order = 'B';
            else if (order == 'B') order = 'C';
            else if (order == 'C') order = 'D';
            else if (order == 'D') order = 'E';
            else if (order == 'E') order = 'A';
            String pathImg = Paths.get(System.getProperty("user.dir") + "/src/GUI/Image/123.jpg").toString();
            answ.setData(order, "Tôi đẹp trai vl", null);
            if (answ.getPath() == null || answ.getPath().isEmpty()) {
                answ.setPreferredSize(new Dimension(1020, 60));
          
            }
            else {
                answ.setPreferredSize(new Dimension(1020, 200));
            }
      
            answ.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeAllSelect();
                    boolean isSelected = answ.isSelected();
                    answ.selected(!isSelected);
                }
                
            });
            listAnsw.add(answ);
            pnAnsw.add(answ);
        }
        calcHeight();
    }
    
    private void removeAllSelect() {
        for (PanelAnswers x : listAnsw) {
            x.selected(false);
        }
    }
    
    private void calcHeight() {
        int cntHeightNotImg = 0;
        int cntHeightHaveImg = 0;
        int space = (listAnsw.size() - 1) * 15;
        for (PanelAnswers x : listAnsw) {
            if (x.getPath() == null || x.getPath().isEmpty()) cntHeightNotImg++;
            else cntHeightHaveImg++;
        }
        int totalSpace = space + (cntHeightNotImg * 60) + (cntHeightHaveImg * 200) + 50; // +50 sai so
        System.out.println(totalSpace);
        pnAnsw.setPreferredSize(new Dimension(500, totalSpace));
    }
    // miliseconds
    private void startCountdown() {
        lbTime.setText(Format.formatTime.format(startTime));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime -= 1000;
                
                lbTime.setText(Format.formatTime.format(startTime));
                
                if (startTime <= 0) {
                    timer.stop();
                    lbTime.setText("Hết giờ!");
                }
            }
        });
        timer.start();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new GUI.Comp.Swing.PanelBackground();
        pnQuestion = new GUI.Comp.Swing.PanelBackground();
        jLabel2 = new javax.swing.JLabel();
        lbQuestio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnAnsw = new GUI.Comp.Swing.PanelBackground();
        lbTitle = new javax.swing.JLabel();
        panelBackground1 = new GUI.Comp.Swing.PanelBackground();
        lbTime = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnMap = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        main.setBackground(new java.awt.Color(247, 247, 247));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel2.setText("Câu hỏi");

        lbQuestio.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lbQuestio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbQuestio.setText("<html>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</html>");
        lbQuestio.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lbQuestio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lbQuestio.setMaximumSize(new java.awt.Dimension(200, 19));
        lbQuestio.setPreferredSize(new java.awt.Dimension(200, 19));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(345, 100));

        pnAnsw.setPreferredSize(new java.awt.Dimension(500, 340));
        pnAnsw.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 15));
        jScrollPane1.setViewportView(pnAnsw);

        javax.swing.GroupLayout pnQuestionLayout = new javax.swing.GroupLayout(pnQuestion);
        pnQuestion.setLayout(pnQuestionLayout);
        pnQuestionLayout.setHorizontalGroup(
            pnQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnQuestionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lbQuestio, javax.swing.GroupLayout.PREFERRED_SIZE, 1104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        pnQuestionLayout.setVerticalGroup(
            pnQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnQuestionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbQuestio, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbTitle.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbTitle.setText("BÀI THI KIỂM TRA TOÁN LỚP 1");

        lbTime.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbTime.setText(" ");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel4.setText("Thời gian làm bài:");

        jButton1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
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

        jButton2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton2.setText("1");
        pnMap.add(jButton2);

        jButton3.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton3.setText("2");
        pnMap.add(jButton3);

        jButton4.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton4.setText("3");
        pnMap.add(jButton4);

        jButton5.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton5.setText("4");
        pnMap.add(jButton5);

        jButton6.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton6.setText("4");
        pnMap.add(jButton6);

        jButton7.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton7.setText("3");
        pnMap.add(jButton7);

        jButton8.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton8.setText("1");
        pnMap.add(jButton8);

        jButton9.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton9.setText("2");
        pnMap.add(jButton9);

        jButton10.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton10.setText("3");
        pnMap.add(jButton10);

        jButton11.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton11.setText("4");
        pnMap.add(jButton11);

        jButton12.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton12.setText("4");
        pnMap.add(jButton12);

        jButton13.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton13.setText("3");
        pnMap.add(jButton13);

        jButton14.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton14.setText("1");
        pnMap.add(jButton14);

        jButton15.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton15.setText("2");
        pnMap.add(jButton15);

        jButton16.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton16.setText("3");
        pnMap.add(jButton16);

        jButton17.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton17.setText("4");
        pnMap.add(jButton17);

        jButton18.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton18.setText("4");
        pnMap.add(jButton18);

        jButton19.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton19.setText("3");
        pnMap.add(jButton19);

        jButton20.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton20.setText("1");
        pnMap.add(jButton20);

        jButton21.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton21.setText("2");
        pnMap.add(jButton21);

        jButton22.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton22.setText("3");
        pnMap.add(jButton22);

        jButton23.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton23.setText("4");
        pnMap.add(jButton23);

        jButton24.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton24.setText("4");
        pnMap.add(jButton24);

        jButton25.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton25.setText("3");
        pnMap.add(jButton25);

        jButton26.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton26.setText("1");
        pnMap.add(jButton26);

        jButton27.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton27.setText("2");
        pnMap.add(jButton27);

        jButton28.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton28.setText("3");
        pnMap.add(jButton28);

        jButton29.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton29.setText("4");
        pnMap.add(jButton29);

        jButton30.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton30.setText("4");
        pnMap.add(jButton30);

        jButton31.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton31.setText("3");
        pnMap.add(jButton31);

        jButton32.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton32.setText("1");
        pnMap.add(jButton32);

        jButton33.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton33.setText("2");
        pnMap.add(jButton33);

        jButton34.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton34.setText("3");
        pnMap.add(jButton34);

        jButton35.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton35.setText("4");
        pnMap.add(jButton35);

        jButton36.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton36.setText("4");
        pnMap.add(jButton36);

        jButton37.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton37.setText("3");
        pnMap.add(jButton37);

        javax.swing.GroupLayout panelBackground1Layout = new javax.swing.GroupLayout(panelBackground1);
        panelBackground1.setLayout(panelBackground1Layout);
        panelBackground1Layout.setHorizontalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBackground1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(15, 15, 15)
                        .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnMap, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        panelBackground1Layout.setVerticalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTime)
                    .addComponent(jLabel4))
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(pnMap, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(panelBackground1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTitle)
                .addGap(611, 611, 611))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbTitle)
                .addGap(27, 27, 27)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 764, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 548, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogDoExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbQuestio;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbTitle;
    private GUI.Comp.Swing.PanelBackground main;
    private GUI.Comp.Swing.PanelBackground panelBackground1;
    private GUI.Comp.Swing.PanelBackground pnAnsw;
    private javax.swing.JPanel pnMap;
    private GUI.Comp.Swing.PanelBackground pnQuestion;
    // End of variables declaration//GEN-END:variables
}
