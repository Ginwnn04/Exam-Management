package GUI.Custom;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import DTO.UserDTO;
import GUI.Comp.Panel.PanelUser;
import BUS.UserBus;
import javax.swing.BorderFactory;

public class ButtonImportUsers extends javax.swing.JPanel {
    private PanelUser panelUser;
    private UserBus BUS;
    public ButtonImportUsers(PanelUser panelUser) {
        this.BUS = new UserBus();
        this.panelUser = panelUser;
        initComponents();
    }

    private void initComponents() {
        btnImport = new GUI.Comp.DateChooser.Button();
        btnImport.setBackground(new java.awt.Color(225, 99, 33)); 
        btnImport.setFont(new java.awt.Font("Roboto", Font.BOLD, 16));
        btnImport.setForeground(Color.WHITE); 
        btnImport.setMinimumSize(new Dimension(150, 30));
        btnImport.setPreferredSize(new Dimension(150, 30));
        btnImport.setFocusPainted(true); 
        btnImport.setOpaque(true); 
        btnImport.setContentAreaFilled(true);
        btnImport.setBorder(BorderFactory.createLineBorder(new Color(225, 99, 33),1));
        
        btnImport.setText("Nhập danh sách");
        //set round border 
        
        // btnImport.setBorderPainted(false); 
        add(btnImport);
        initEvent();
    }

    public void initEvent() {
        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    onImport(filePath);
                }
            }
        });
    }

    private void onImport(String filePath) {
        try{
            ArrayList<UserDTO> rs = BUS.ImportUsers(filePath);
            showOptionPane(rs);
            panelUser.updateTableItems();
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Import thất bại! Vui lòng kiểm tra lại file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showOptionPane(ArrayList<UserDTO> rs) {
        if(rs.size() > 0) {
            JOptionPane.showMessageDialog(null, "Import thành công " + rs.size() + " danh sách dự thi ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Import thất bại! Vui lòng kiểm tra lại file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



    private GUI.Comp.DateChooser.Button btnImport;
    
}
