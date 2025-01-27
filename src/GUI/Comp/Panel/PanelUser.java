package GUI.Comp.Panel;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import GUI.Comp.Dialog.DialogQuestion;
import GUI.Comp.Dialog.DialogUser;
import GUI.Comp.Dialog.DialogUsers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author nguye
 */
public class PanelUser extends javax.swing.JPanel {

    /**
     * Creates new form PanelUser
     */
    public PanelUser() {
        initComponents();
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbNguoidung.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        tbNguoidung.setRowHeight(30);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelBackground1 = new GUI.Comp.Swing.PanelBackground();
        panelBackground2 = new GUI.Comp.Swing.PanelBackground();
        panelBackground3 = new GUI.Comp.Swing.PanelBackground();
        panelBackground4 = new GUI.Comp.Swing.PanelBackground();
        panelBackground5 = new GUI.Comp.Swing.PanelBackground();
        main = new GUI.Comp.Swing.PanelBackground();
        panelBackground6 = new GUI.Comp.Swing.PanelBackground();
        panelBackground7 = new GUI.Comp.Swing.PanelBackground();
        panelBackground8 = new GUI.Comp.Swing.PanelBackground();
        panelBackground9 = new GUI.Comp.Swing.PanelBackground();
        pnCenter = new GUI.Comp.Swing.PanelBackground();
        pnTop = new GUI.Comp.Swing.PanelBackground();
        jLabel1 = new javax.swing.JLabel();
        panelBackground10 = new GUI.Comp.Swing.PanelBackground();
        panelBackground17 = new GUI.Comp.Swing.PanelBackground();
        panelBackground18 = new GUI.Comp.Swing.PanelBackground();
        panelBackground11 = new GUI.Comp.Swing.PanelBackground();
        jLabel2 = new javax.swing.JLabel();
        panelBackground12 = new GUI.Comp.Swing.PanelBackground();
        txtNguoiDung = new javax.swing.JTextField();
        panelBackground13 = new GUI.Comp.Swing.PanelBackground();
        jLabel3 = new javax.swing.JLabel();
        panelBackground14 = new GUI.Comp.Swing.PanelBackground();
        cbxDiemso = new javax.swing.JComboBox<>();
        panelBackground15 = new GUI.Comp.Swing.PanelBackground();
        panelBackground16 = new GUI.Comp.Swing.PanelBackground();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNguoidung = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 765));

        panelBackground1.setBackground(new java.awt.Color(247, 247, 247));
        panelBackground1.setLayout(new java.awt.BorderLayout());

        panelBackground2.setBackground(new java.awt.Color(247, 247, 247));
        panelBackground2.setPreferredSize(new java.awt.Dimension(20, 725));

        javax.swing.GroupLayout panelBackground2Layout = new javax.swing.GroupLayout(panelBackground2);
        panelBackground2.setLayout(panelBackground2Layout);
        panelBackground2Layout.setHorizontalGroup(
            panelBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground2Layout.setVerticalGroup(
            panelBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );

        panelBackground1.add(panelBackground2, java.awt.BorderLayout.LINE_START);

        panelBackground3.setBackground(new java.awt.Color(247, 247, 247));
        panelBackground3.setPreferredSize(new java.awt.Dimension(1200, 20));

        javax.swing.GroupLayout panelBackground3Layout = new javax.swing.GroupLayout(panelBackground3);
        panelBackground3.setLayout(panelBackground3Layout);
        panelBackground3Layout.setHorizontalGroup(
            panelBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        panelBackground3Layout.setVerticalGroup(
            panelBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground1.add(panelBackground3, java.awt.BorderLayout.PAGE_START);

        panelBackground4.setBackground(new java.awt.Color(247, 247, 247));
        panelBackground4.setPreferredSize(new java.awt.Dimension(20, 725));

        javax.swing.GroupLayout panelBackground4Layout = new javax.swing.GroupLayout(panelBackground4);
        panelBackground4.setLayout(panelBackground4Layout);
        panelBackground4Layout.setHorizontalGroup(
            panelBackground4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground4Layout.setVerticalGroup(
            panelBackground4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );

        panelBackground1.add(panelBackground4, java.awt.BorderLayout.LINE_END);

        panelBackground5.setBackground(new java.awt.Color(247, 247, 247));
        panelBackground5.setPreferredSize(new java.awt.Dimension(1200, 20));

        javax.swing.GroupLayout panelBackground5Layout = new javax.swing.GroupLayout(panelBackground5);
        panelBackground5.setLayout(panelBackground5Layout);
        panelBackground5Layout.setHorizontalGroup(
            panelBackground5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        panelBackground5Layout.setVerticalGroup(
            panelBackground5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground1.add(panelBackground5, java.awt.BorderLayout.PAGE_END);

        main.setLayout(new java.awt.BorderLayout());

        panelBackground6.setPreferredSize(new java.awt.Dimension(10, 705));

        javax.swing.GroupLayout panelBackground6Layout = new javax.swing.GroupLayout(panelBackground6);
        panelBackground6.setLayout(panelBackground6Layout);
        panelBackground6Layout.setHorizontalGroup(
            panelBackground6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panelBackground6Layout.setVerticalGroup(
            panelBackground6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );

        main.add(panelBackground6, java.awt.BorderLayout.LINE_START);

        panelBackground7.setPreferredSize(new java.awt.Dimension(1160, 10));

        javax.swing.GroupLayout panelBackground7Layout = new javax.swing.GroupLayout(panelBackground7);
        panelBackground7.setLayout(panelBackground7Layout);
        panelBackground7Layout.setHorizontalGroup(
            panelBackground7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1160, Short.MAX_VALUE)
        );
        panelBackground7Layout.setVerticalGroup(
            panelBackground7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        main.add(panelBackground7, java.awt.BorderLayout.PAGE_START);

        panelBackground8.setPreferredSize(new java.awt.Dimension(10, 705));

        javax.swing.GroupLayout panelBackground8Layout = new javax.swing.GroupLayout(panelBackground8);
        panelBackground8.setLayout(panelBackground8Layout);
        panelBackground8Layout.setHorizontalGroup(
            panelBackground8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panelBackground8Layout.setVerticalGroup(
            panelBackground8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );

        main.add(panelBackground8, java.awt.BorderLayout.LINE_END);

        panelBackground9.setPreferredSize(new java.awt.Dimension(1160, 10));

        javax.swing.GroupLayout panelBackground9Layout = new javax.swing.GroupLayout(panelBackground9);
        panelBackground9.setLayout(panelBackground9Layout);
        panelBackground9Layout.setHorizontalGroup(
            panelBackground9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1160, Short.MAX_VALUE)
        );
        panelBackground9Layout.setVerticalGroup(
            panelBackground9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        main.add(panelBackground9, java.awt.BorderLayout.PAGE_END);

        pnCenter.setLayout(new java.awt.BorderLayout());

        pnTop.setPreferredSize(new java.awt.Dimension(1140, 80));
        pnTop.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Tìm kiếm");
        pnTop.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        panelBackground10.setPreferredSize(new java.awt.Dimension(150, 30));
        panelBackground10.setLayout(new java.awt.BorderLayout());

        panelBackground17.setPreferredSize(new java.awt.Dimension(150, 22));

        javax.swing.GroupLayout panelBackground17Layout = new javax.swing.GroupLayout(panelBackground17);
        panelBackground17.setLayout(panelBackground17Layout);
        panelBackground17Layout.setHorizontalGroup(
            panelBackground17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        panelBackground17Layout.setVerticalGroup(
            panelBackground17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelBackground10.add(panelBackground17, java.awt.BorderLayout.PAGE_END);

        panelBackground18.setPreferredSize(new java.awt.Dimension(150, 5));

        javax.swing.GroupLayout panelBackground18Layout = new javax.swing.GroupLayout(panelBackground18);
        panelBackground18.setLayout(panelBackground18Layout);
        panelBackground18Layout.setHorizontalGroup(
            panelBackground18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        panelBackground18Layout.setVerticalGroup(
            panelBackground18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        panelBackground10.add(panelBackground18, java.awt.BorderLayout.PAGE_START);

        pnTop.add(panelBackground10, java.awt.BorderLayout.LINE_END);

        panelBackground11.setPreferredSize(new java.awt.Dimension(993, 30));
        panelBackground11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel2.setText("Người dùng");
        panelBackground11.add(jLabel2);

        panelBackground12.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout panelBackground12Layout = new javax.swing.GroupLayout(panelBackground12);
        panelBackground12.setLayout(panelBackground12Layout);
        panelBackground12Layout.setHorizontalGroup(
            panelBackground12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground12Layout.setVerticalGroup(
            panelBackground12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground11.add(panelBackground12);

        txtNguoiDung.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtNguoiDung.setPreferredSize(new java.awt.Dimension(300, 30));
        panelBackground11.add(txtNguoiDung);

        panelBackground13.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout panelBackground13Layout = new javax.swing.GroupLayout(panelBackground13);
        panelBackground13.setLayout(panelBackground13Layout);
        panelBackground13Layout.setHorizontalGroup(
            panelBackground13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground13Layout.setVerticalGroup(
            panelBackground13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground11.add(panelBackground13);

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel3.setText("Điểm số");
        panelBackground11.add(jLabel3);

        panelBackground14.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout panelBackground14Layout = new javax.swing.GroupLayout(panelBackground14);
        panelBackground14.setLayout(panelBackground14Layout);
        panelBackground14Layout.setHorizontalGroup(
            panelBackground14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground14Layout.setVerticalGroup(
            panelBackground14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground11.add(panelBackground14);

        cbxDiemso.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        cbxDiemso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "Item 2", "Item 3", "Item 4" }));
        cbxDiemso.setToolTipText("");
        cbxDiemso.setPreferredSize(new java.awt.Dimension(200, 30));
        panelBackground11.add(cbxDiemso);

        panelBackground15.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout panelBackground15Layout = new javax.swing.GroupLayout(panelBackground15);
        panelBackground15.setLayout(panelBackground15Layout);
        panelBackground15Layout.setHorizontalGroup(
            panelBackground15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground15Layout.setVerticalGroup(
            panelBackground15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground11.add(panelBackground15);

        panelBackground16.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout panelBackground16Layout = new javax.swing.GroupLayout(panelBackground16);
        panelBackground16.setLayout(panelBackground16Layout);
        panelBackground16Layout.setHorizontalGroup(
            panelBackground16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        panelBackground16Layout.setVerticalGroup(
            panelBackground16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelBackground11.add(panelBackground16);

        jButton1.setBackground(new java.awt.Color(225, 99, 73));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setActionCommand("Thêm người dùng");
        jButton1.setLabel("+ Thêm người dùng");
        jButton1.setMaximumSize(new java.awt.Dimension(250, 28));
        jButton1.setMinimumSize(new java.awt.Dimension(250, 28));
        jButton1.setPreferredSize(new java.awt.Dimension(250, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelBackground11.add(jButton1);

        pnTop.add(panelBackground11, java.awt.BorderLayout.CENTER);

        pnCenter.add(pnTop, java.awt.BorderLayout.PAGE_START);

        tbNguoidung.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tbNguoidung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Họ và Tên", "Email", "Phân Quyền", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbNguoidung);

        pnCenter.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        main.add(pnCenter, java.awt.BorderLayout.CENTER);

        panelBackground1.add(main, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 765, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DialogUser d1 =new DialogUser(null, true);
        DialogQuestion d2=new DialogQuestion(null, true);
        DialogUsers d = new DialogUsers(null,true);
        System.out.println("them nguoi dung");
        d.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxDiemso;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.Comp.Swing.PanelBackground main;
    private GUI.Comp.Swing.PanelBackground panelBackground1;
    private GUI.Comp.Swing.PanelBackground panelBackground10;
    private GUI.Comp.Swing.PanelBackground panelBackground11;
    private GUI.Comp.Swing.PanelBackground panelBackground12;
    private GUI.Comp.Swing.PanelBackground panelBackground13;
    private GUI.Comp.Swing.PanelBackground panelBackground14;
    private GUI.Comp.Swing.PanelBackground panelBackground15;
    private GUI.Comp.Swing.PanelBackground panelBackground16;
    private GUI.Comp.Swing.PanelBackground panelBackground17;
    private GUI.Comp.Swing.PanelBackground panelBackground18;
    private GUI.Comp.Swing.PanelBackground panelBackground2;
    private GUI.Comp.Swing.PanelBackground panelBackground3;
    private GUI.Comp.Swing.PanelBackground panelBackground4;
    private GUI.Comp.Swing.PanelBackground panelBackground5;
    private GUI.Comp.Swing.PanelBackground panelBackground6;
    private GUI.Comp.Swing.PanelBackground panelBackground7;
    private GUI.Comp.Swing.PanelBackground panelBackground8;
    private GUI.Comp.Swing.PanelBackground panelBackground9;
    private GUI.Comp.Swing.PanelBackground pnCenter;
    private GUI.Comp.Swing.PanelBackground pnTop;
    private javax.swing.JTable tbNguoidung;
    private javax.swing.JTextField txtNguoiDung;
    // End of variables declaration//GEN-END:variables
}
