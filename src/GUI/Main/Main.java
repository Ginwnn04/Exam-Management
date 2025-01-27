/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Main;


import GUI.Comp.Panel.PanelQuestion;
import Helper.MyListener;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import GUI.Comp.NavBar;
import javax.swing.UIManager;
import style.ColorConfig;

/**
 *
 * @author quang
 */
public class Main extends javax.swing.JFrame implements PropertyChangeListener {

    public Main() {
        initComponents();
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorConfig.WHITE_COLOR_BG);
        
//        navBar.setInformation(fullName, role);
        MyListener.getInstance().addPropertyChangeListener(this);

        setTitle("PHẦN MỀM QUẢN LÍ THI TRẮC NGHIỆM");
        Helper.ConnectDB.getInstance().openConnect();
        showForm(new PanelQuestion());
        
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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("ItemMenu")) {
            System.out.println(evt.getOldValue() + " " + evt.getNewValue());
//            Map<String, List<JButton>> hashMap = (Map<String, List<JButton>>)evt.getOldValue();
            int index = (int) evt.getNewValue();
//            String role = hashMap.keySet().iterator().next();
            
            
            
//            if (role.equals("admin")) {
//                System.out.println(role + " " + 1);
//                switch (index) {
//                    case 0:
//                        showForm(new PanelDashbroad());
//                        break;
//                    case 1:
//                        showForm(new QuanLiBan());
//                        break;
//                    case 2:
//                        showForm(new QuanLiDatMon());
//                        break;
//                    case 3:
//                        showForm(new QuanLiMonAn());
//                        break;
//                    case 4: 
//                        showForm(new QuanLiHoaDon());
//                        break;
//
//                    case 5: 
//                        showForm(new QuanLiGiamGia());
//                        break;
//                    case 6:
//                        showForm(new QuanLiTheLoai());
//                        break;
//                    case 7:
//                        showForm(new QuanLiNguyenLieu());
//                        break;
//                    case 8:
//                        showForm(new QuanLiNhapKho());
//                        break;
//                    case 9:
//                        showForm(new PanelStatistic());
//                        break;
//                    case 10:
//                        showForm(new QuanLiSupplier());
//                        break;
//                    case 11:
//                        showForm(new QuanLi_Staff());
//                        break;
//                }
//            }
//            else if (role.equals("manager")) {
//                System.out.println(role + " " + 2);
//                switch (index) {
//                    case 0:
//                        showForm(new PanelDashbroad());
//                        break;
//                    case 1:
//                        showForm(new QuanLiBan());
//                        break;
//                    case 2:
//                        showForm(new QuanLiDatMon());
//                        break;
//                    case 3:
//                        showForm(new QuanLiMonAn());
//                        break;
//                    case 4: 
//                        showForm(new QuanLiHoaDon());
//                        break;
//
//                    case 5: 
//                        showForm(new QuanLiGiamGia());
//                        break;
//                    case 6:
//                        showForm(new QuanLiTheLoai());
//                        break;
//                    case 7:
//                        showForm(new QuanLiNguyenLieu());
//                        break;
//                    case 8:
//                        showForm(new QuanLiNhapKho());
//                        break;
//                    case 9:
//                        showForm(new PanelStatistic());
//                        break;
//                    case 10:
//                        showForm(new QuanLiSupplier());
//                        break;
//                }
//            }
//            else {
//                System.out.println(role + " " + 3);
//                switch (index) {
//                    case 0:
//                        showForm(new PanelDashbroad());
//                        break;
//                    case 1:
//                        showForm(new QuanLiBan());
//                        break;
//                    case 2:
//                        showForm(new QuanLiDatMon());
//                        break;
//                    case 3:
//                        showForm(new QuanLiMonAn());
//                        break;
//                    case 4: 
//                        showForm(new QuanLiHoaDon());
//                        break;
//
//                    case 5: 
//                        showForm(new QuanLiGiamGia());
//                        break;
//                    case 6:
//                        showForm(new QuanLiTheLoai());
//                        break;
//                    
//                }
//            }

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
                new Login().setVisible(true);
                System.out.println("zzscsdsd");
            }

        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1445, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private GUI.Comp.Swing.PanelBackground body;
    private GUI.Comp.NavBar navBar;
    private GUI.Comp.Swing.PanelBackground panelBackground;
}
