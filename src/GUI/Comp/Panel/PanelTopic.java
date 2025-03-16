package GUI.Comp.Panel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;

import DTO.TopicDTO;
// import DTO.UserDTO;
import BUS.TopicBUS;
import DAO.TopicDAO;

import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.event.DocumentListener;
import GUI.Comp.Dialog.DialogTopic11;
import GUI.Comp.Dialog.DialogTopic1;
import GUI.Custom.TableActionCellEditor;
import GUI.Custom.TableActionCellRenderer;
import GUI.Custom.TableActionEvent;
import GUI.Utils.Debounce;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author nguye
 */
public class PanelTopic extends javax.swing.JPanel {
     private List<TopicDTO> listTopic = new ArrayList<>();
    private TopicBUS topicBUS = new TopicBUS();
  
    // private List<TopicDTO> listTopicTemp;
   
    

    /**
     * Creates new form PanelUser
     */
    public PanelTopic() {
        initComponents();
        txtchude.putClientProperty("JTextField.placeholderText", "Nhập chủ đề...");
        initTable();
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbChude.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        tbChude.setRowHeight(30);
        render();
        setupSearchEvent();
        addComboBoxListeners();
    }
    
    private void initTable(){
    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbChude.getTableHeader().getDefaultRenderer();
    renderer.setHorizontalAlignment(JLabel.LEFT);
    TableActionEvent event = new TableActionEvent() {
        @Override
        public void onDelete(int row){
            DefaultTableModel model = (DefaultTableModel) tbChude.getModel();
            var a = tbChude.getModel().getValueAt(row, 0); 

    
            int id = ((Number) a).intValue();
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean isDeleted = new TopicDAO().delete(id);
                if (isDeleted) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadTableData(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại! Vui lòng thử lại.");
                }
            } 
            
        }
        @Override
        public void onUpdate(int row){
            var a = tbChude.getModel().getValueAt(row, 0);
            if (a == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu để cập nhật!");
                return;
            }       
            int id = ((Number) a).intValue(); 
            TopicDTO topic = new TopicDAO().findByID(id);
            if (topic == null) {
                JOptionPane.showMessageDialog(null, "Chủ đề không tồn tại trong CSDL!");
                return;
            }
            DialogTopic1 dialog = new DialogTopic1(null, true);
            dialog.setTopic(topic); 
            dialog.setVisible(true);
        
            
            if (dialog.isUpdated()) {  
                loadTableData(); 
                ((DefaultTableModel) tbChude.getModel()).fireTableDataChanged();
            }
              
           
           
        }
        @Override
        public void onView(int row){
            var a = tbChude.getModel().getValueAt(row, 0);    
            int id = ((Number) a).intValue();         
            TopicDTO topic = new TopicDAO().findByID(id);       
            if (topic == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy chủ đề trong CSDL!");
                return;
            }
            DialogTopic1 dialog = new DialogTopic1(null, true);
            dialog.setTopic(topic);
            dialog.setVisible(true);
        }

        
    };
    tbChude.getColumnModel().getColumn(2).setCellRenderer(new TableActionCellRenderer() );
    tbChude.getColumnModel().getColumn(2).setCellEditor(new TableActionCellEditor(event));
    renderTopic();
    }
    public void renderTopic(){
        listTopic = topicBUS.getAllTopic();
       cbxchude.removeAllItems();
       cbxchude.addItem("Tất cả");
       for (TopicDTO topic : listTopic) {
           cbxchude.addItem(String.valueOf(topic.getId()));
       }
    }
    public void render(){
        
         listTopic = topicBUS.getAllTopic();
        //  System.out.println(listTopic);
        DefaultTableModel model = (DefaultTableModel) tbChude.getModel();
        model.setRowCount(0);
        System.out.println("Số lượng topic: " + listTopic.size());
        for (TopicDTO topic : listTopic) {
            model.addRow(new Object[]{
                topic.getId(),
                topic.getTitle(),
            });
        }
        // model.fireTableDataChanged();
        // tbChude.setModel(model);
    }
    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tbChude.getModel();
        model.setRowCount(0); 
    
        List<TopicDTO> topics = new TopicDAO().getAll(true);
        System.out.println("Số lượng chủ đề sau khi cập nhật: " + topics.size());
        for (TopicDTO topic : topics) {
            model.addRow(new Object[]{topic.getId(), topic.getTitle(), topic.getParent()});
        }
        model.fireTableDataChanged();
    }
    private void filterTable(){
        String query =txtchude.getText().toLowerCase();
        String selectedchude = (String) cbxchude.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) tbChude.getModel();
        model.setRowCount(0);
        for (TopicDTO topic : listTopic){
            boolean chude = selectedchude.equals("Tất cả")|| topic.getId() == Integer.parseInt(selectedchude);       
            boolean searchchude =   
                                  topic.getTitle().toLowerCase().contains(query);
//                                  topic.getParent().contains(query);
            
        if(chude && searchchude){
            model.addRow(new Object[]{
                topic.getId(),
                topic.getTitle(),
                topic.getParent(),
                
            });
        }
        }
        model.fireTableDataChanged();
        tbChude.setModel(model);
    }
        
    private void addComboBoxListeners(){
        cbxchude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt){
                filterTable();
            }
            
        });
        
    }
    private void setupSearchEvent(){
        Debounce onSearch = new Debounce(() -> filterTable(),500);
        txtchude.getDocument().addDocumentListener(new DocumentListener() {
            
         
            @Override
            public void insertUpdate(DocumentEvent e){
                onSearch.execute();
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                onSearch.execute();
            }
            @Override
            public void changedUpdate(DocumentEvent e){
                onSearch.execute();
            }
        });
        
    }
   
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        panelBackground11 = new GUI.Comp.Swing.PanelBackground();
        jLabel2 = new javax.swing.JLabel();
        panelBackground12 = new GUI.Comp.Swing.PanelBackground();
        txtchude = new javax.swing.JTextField();
        panelBackground13 = new GUI.Comp.Swing.PanelBackground();
        jLabel3 = new javax.swing.JLabel();
        panelBackground14 = new GUI.Comp.Swing.PanelBackground();
        cbxchude = new javax.swing.JComboBox<>();
        panelBackground15 = new GUI.Comp.Swing.PanelBackground();
        panelBackground16 = new GUI.Comp.Swing.PanelBackground();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbChude = new javax.swing.JTable();

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

        panelBackground11.setPreferredSize(new java.awt.Dimension(993, 30));
        panelBackground11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel2.setText("Tên chủ đề");
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

        txtchude.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtchude.setPreferredSize(new java.awt.Dimension(400, 30));
        panelBackground11.add(txtchude);

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

//        jLabel3.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
//        jLabel3.setText("ID chủ đề");
//        panelBackground11.add(jLabel3);

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

//        cbxchude.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
//        cbxchude.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "0"}));
//        cbxchude.setToolTipText("");
//        cbxchude.setPreferredSize(new java.awt.Dimension(200, 30));
//        panelBackground11.add(cbxchude);

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

        jButton1.setBackground(new java.awt.Color(53,80,154));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("+ Thêm Chủ đề");
        jButton1.setActionCommand("Thêm chủ đề");
        jButton1.setMaximumSize(new java.awt.Dimension(150, 28));
        jButton1.setMinimumSize(new java.awt.Dimension(150, 28));
        jButton1.setPreferredSize(new java.awt.Dimension(180, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelBackground11.add(jButton1);
         panelBackground11.setLayout(new BorderLayout());


        JPanel panelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelLeft.setOpaque(false);
        panelLeft.add(jLabel2);
        panelLeft.add(txtchude);

        // Panel chứa JButton để tránh bị kéo giãn
        JPanel panelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelRight.setOpaque(false);
        panelRight.add(jButton1);

        panelBackground11.add(panelLeft, BorderLayout.WEST);
        panelBackground11.add(panelRight, BorderLayout.EAST);

        pnTop.add(panelBackground11, java.awt.BorderLayout.CENTER);

        pnCenter.add(pnTop, java.awt.BorderLayout.PAGE_START);

        tbChude.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tbChude.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Chủ đề", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbChude.setPreferredSize(new java.awt.Dimension(225, 1300));
        jScrollPane1.setViewportView(tbChude);

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
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        DialogTopic11 d3 = new DialogTopic11(null,true);      System.out.println("them nguoi dung");
        d3.setVisible(true);
      
        
    }                                        
    
  
    
      


    // Variables declaration - do not modify                     
    private javax.swing.JComboBox<String> cbxchude;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.Comp.Swing.PanelBackground main;
    private GUI.Comp.Swing.PanelBackground panelBackground1;
    private GUI.Comp.Swing.PanelBackground panelBackground11;
    private GUI.Comp.Swing.PanelBackground panelBackground12;
    private GUI.Comp.Swing.PanelBackground panelBackground13;
    private GUI.Comp.Swing.PanelBackground panelBackground14;
    private GUI.Comp.Swing.PanelBackground panelBackground15;
    private GUI.Comp.Swing.PanelBackground panelBackground16;
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
    private javax.swing.JTable tbChude;
    private javax.swing.JTextField txtchude;
    // End of variables declaration                   
}
