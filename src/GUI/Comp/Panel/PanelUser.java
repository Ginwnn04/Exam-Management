package GUI.Comp.Panel;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.Custom.TableActionCellRenderer;
import GUI.Custom.TableActionEvent;
import GUI.Utils.Debounce;
import GUI.Custom.ButtonImportUsers;
import GUI.Custom.TableActionCellEditor;
import BUS.UserBus;
import DTO.UserDTO;
import GUI.Comp.Dialog.DialogUsers;
import javax.swing.event.DocumentEvent;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author nguye
 */
public class PanelUser extends javax.swing.JPanel {
    private ArrayList<UserDTO> listUser = new ArrayList<>();
    private ArrayList<UserDTO> listUserTemp;
    private UserBus userBus = new UserBus();

    public PanelUser() {
        initComponents();
        setupSearchEvent();
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbNguoidung.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onDelete(int row) {
                var a = tbNguoidung.getModel().getValueAt(row, 4);
                int id = ((Number) a).intValue();
                confirmAndDeleteUser(id);
                
            }

            @Override
            public void onUpdate(int row) {
                System.out.println("update on row number: "+row);
                var a =tbNguoidung.getModel().getValueAt(row, 4);
                int id = ((Number) a).intValue();
                DialogUsers d = new DialogUsers(null,userBus,id);
                d.setVisible(true);
                updateTableItems();
                d.dispose();
            }

            @Override
            public void onView(int row) {
                var a = tbNguoidung.getModel().getValueAt(row, 4);
            }
            
        };
        tbNguoidung.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRenderer());
        tbNguoidung.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
        tbNguoidung.setRowHeight(30);
        updateTableItems();
    }

    public void updateTableItems(){
        listUser = userBus.getAllUsers();
        listUserTemp = listUser;
        render();
    }

    private void setTableItems(ArrayList<UserDTO> list){
        this.listUser = list;
        render();
    }

    public void render(){
        // listUser = userBus.getAllUsers();
        System.out.println("size of listUser: "+listUser.size());
        DefaultTableModel model = (DefaultTableModel) tbNguoidung.getModel();
        model.setRowCount(0);
        for (UserDTO user : listUser) {
            model.addRow(new Object[]{
                user.getFullName(),
                user.getEmail(),
                user.getIsAdmin() == 1 ? "Admin" : "Người dùng",
                "Hành động",
                user.getId()
            });
        }


        model.fireTableDataChanged();
        tbNguoidung.setModel(model);
    }

    private void confirmAndDeleteUser(int id) {
    int confirm = JOptionPane.showConfirmDialog(
        null,
        "Bạn có chắc chắn muốn xóa người dùng này?",
        "Xác nhận xóa",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        if (userBus.deleteUser(id)) {
            updateTableItems();
            JOptionPane.showMessageDialog(null, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    private void setupSearchEvent() {
        Debounce onSearch = new Debounce(() -> searchUsers(), 500);
    
        txtNguoiDung.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearch.execute();
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearch.execute();
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearch.execute();
            }
        });
    }

    private void searchUsers(){
        String query = txtNguoiDung.getText().trim().toLowerCase();
        ArrayList<UserDTO> result = userBus.searchUser(query);
        setTableItems(result);
    }
    
    private void filterUsers1() {
        ArrayList<UserDTO> usersList = userBus.getAllUsers();
        String query = txtNguoiDung.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            updateTableItems(); // Hiển thị lại danh sách gốc nếu không nhập gì
            return;
        }
        // Lọc danh sách 
        ArrayList<UserDTO> filteredUsers = new ArrayList<>();
        for (UserDTO user : usersList) {
            filteredUsers.addAll(setUpFilter(user, query));
        }
        setTableItems(filteredUsers);
    }
    
    private ArrayList<UserDTO> setUpFilter(UserDTO user,String query){
        ArrayList<UserDTO> listUserTemp = new ArrayList<>();
        boolean matchName = user.getFullName().toLowerCase().contains(query);
        boolean matchEmail = user.getEmail().toLowerCase().contains(query);
        boolean matchRole = user.getIsAdmin() == 1 ? "Admin".toLowerCase().contains(query) 
        : "Người dùng".toLowerCase().contains(query);
        if (matchName || matchEmail || matchRole) {
            listUserTemp.add(user);
        }
        return listUserTemp;
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground12 = new GUI.Comp.Swing.PanelBackground();
        jPanel5 = new javax.swing.JPanel();
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
        panelBackground11 = new GUI.Comp.Swing.PanelBackground();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnImport = new ButtonImportUsers(this);
        jPanel7 = new javax.swing.JPanel();
        panelBackground14 = new GUI.Comp.Swing.PanelBackground();
        jLabel1 = new javax.swing.JLabel();
        txtNguoiDung = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNguoidung = new javax.swing.JTable();

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 188, Short.MAX_VALUE)
        );

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

        pnCenter.setLayout(new java.awt.BorderLayout(0, 15));

        pnTop.setPreferredSize(new java.awt.Dimension(1140, 80));
        pnTop.setLayout(new java.awt.BorderLayout(100, 0));

        panelBackground11.setPreferredSize(new java.awt.Dimension(993, 30));
        panelBackground11.setLayout(new java.awt.BorderLayout(400, 0));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btnAdd.setText("Thêm người dùng");
        btnAdd.setBackground(new java.awt.Color(53, 80, 154));
        btnAdd.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255,255,255));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DialogUsers d = new DialogUsers(null,userBus);
                d.setVisible(true);
                updateTableItems();
                d.dispose();
            }
        });
        jPanel6.add(btnAdd);

        //update table items after import
        
        jPanel6.add(btnImport);

        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 40));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        panelBackground11.add(jPanel2, java.awt.BorderLayout.LINE_END);

        panelBackground14.setMaximumSize(new java.awt.Dimension(300, 32767));
        panelBackground14.setMinimumSize(new java.awt.Dimension(300, 50));
        panelBackground14.setPreferredSize(new java.awt.Dimension(300, 80));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Tìm kiếm");
        panelBackground14.add(jLabel1);

        txtNguoiDung.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtNguoiDung.setPreferredSize(new java.awt.Dimension(300, 30));
        txtNguoiDung.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên người dùng, email hoặc quyền .....");
        panelBackground14.add(txtNguoiDung);

        panelBackground11.add(panelBackground14, java.awt.BorderLayout.CENTER);

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
                "Họ và Tên", "Email", "Phân Quyền", "Hành động","id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        tbNguoidung.removeColumn(tbNguoidung.getColumnModel().getColumn(4));

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private ButtonImportUsers btnImport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.Comp.Swing.PanelBackground main;
    private GUI.Comp.Swing.PanelBackground panelBackground1;
    private GUI.Comp.Swing.PanelBackground panelBackground11;
    private GUI.Comp.Swing.PanelBackground panelBackground12;
    private GUI.Comp.Swing.PanelBackground panelBackground14;
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
