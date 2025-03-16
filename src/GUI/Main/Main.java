/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Main;


import BUS.ExamBUS;
import BUS.QuestionBUS;
import GUI.Comp.Panel.PanelChinhSua;
import GUI.Comp.Panel.PanelDetailExam;
import GUI.Comp.Panel.PanelQuestion;
import GUI.Comp.Panel.PanelTopic;
import BUS.ResultBUS;
import BUS.TestBUS;
import DTO.ResultDTO;
import GUI.Comp.Panel.PanelQuestion;
import GUI.Comp.Dialog.DialogTest;
import GUI.Comp.Dialog.Statistics.DialogStatistics;
import GUI.Comp.Panel.PanelQuestion;
import GUI.Comp.Panel.PanelEnterExams;
import GUI.Comp.Panel.PanelTest;

import GUI.Comp.Panel.PanelUser;
import GUI.Comp.Panel.Result.PanelAfterExam;
import GUI.Comp.Panel.PanelExams;
import GUI.Comp.Panel.PanelHistory;
import Helper.MyListener;


import DTO.UserDTO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import style.ColorConfig;
import GUI.Comp.Panel.PanelAnalyze;
import GUI.Comp.Panel.PanelTopic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author quang
 */
public class Main extends javax.swing.JFrame implements PropertyChangeListener {
    private UserDTO user =new UserDTO();
    private PanelChinhSua panelChinhSua;
    private UserDTO currentUser;
    public Main(UserDTO user) {
        this.currentUser = user;
        
        initComponents();
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorConfig.WHITE_COLOR_BG);
       
//        navBar.setInformation(fullName, role);
        MyListener.getInstance().addPropertyChangeListener(this);

        setTitle("PHẦN MỀM QUẢN LÍ THI TRẮC NGHIỆM");
        showForm(new PanelEnterExams(this::showForm));
//        FlatMacLightLaf.registerCustomDefaultsSource("style");
//        UIManager.put("TextField.font", style.MyFont.fontText);
//        UIManager.put("Label.font", style.MyFont.fontText);
//        UIManager.put("Button.font", style.MyFont.fontText);
//        UIManager.put("Table.font", style.MyFont.fontText);
//       
//
//        FlatMacLightLaf.setup();
        logout();
        setVisible(true);
        thongtin();
        setResizable(false);
    }
    public void updateNavBar() {
        navBar.updateUserInfo(); 
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("ItemMenu")) {
            System.out.println(evt.getOldValue() + " " + evt.getNewValue());
//            Map<String, List<JButton>> hashMap = (Map<String, List<JButton>>)evt.getOldValue();
            int index = (int) evt.getNewValue();
            boolean isAdmin = (boolean) evt.getOldValue();
            if (isAdmin) {
                switch (index) {
                    case 0:
                        showForm(new PanelEnterExams(this::showForm));
                        break;
                    case 1:
                        showForm(new PanelQuestion());
                        break;
                    case 2:
                        showForm(new PanelExams());
                        break;
                    case 3:
                        showForm(new PanelTest());
                        break;
                    case 4:
                       showForm(new PanelTopic());
                        break;
                    case 5:
                        showForm(new PanelUser());
                    break;
                    case 6:
                        showForm(new PanelAnalyze());
                        break;
                    case 7:
                        showForm(new PanelHistory(this::showForm));
                        break;
                    default:
                        break;
                }
            }
            else {
                switch (index) {
                    case 0:
                        showForm(new PanelEnterExams(this::showForm));
                        break;
                    case 1:
                        showForm(new PanelHistory(this::showForm));
                        break;
                    default:
                        break;
                }
            }
            
        }
    }

    public void showForm(JPanel com) {
        body.removeAll();
        body.add(com);
        body.revalidate();
        body.repaint();
    }

    public void logout() {
        navBar.btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login2().setVisible(true);
                
            }

        });
    }
    public void thongtin() {
        navBar.btnThongTin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            PanelChinhSua panel =new PanelChinhSua();
            panel.setUserData(currentUser);
            showForm(panel);
            
            }

        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground = new GUI.Comp.Swing.PanelBackground();
        navBar = new GUI.Comp.NavBar();
        body = new GUI.Comp.Swing.PanelBackground();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        panelBackground.setForeground(new java.awt.Color(153, 255, 0));
        panelBackground.setLayout(new java.awt.BorderLayout());

        navBar.setBackground(new java.awt.Color(255, 255, 255));
        navBar.setPreferredSize(new java.awt.Dimension(245, 765));
        panelBackground.add(navBar, java.awt.BorderLayout.LINE_START);

        body.setBackground(new java.awt.Color(247, 247, 247));
        body.setForeground(new java.awt.Color(247, 247, 247));
        body.setLayout(new java.awt.BorderLayout());
        panelBackground.add(body, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1445, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private GUI.Comp.Swing.PanelBackground body;
    private GUI.Comp.NavBar navBar;
    private GUI.Comp.Swing.PanelBackground panelBackground;
    // End of variables declaration//GEN-END:variables

}
