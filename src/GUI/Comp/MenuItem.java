package GUI.Comp;

import javax.swing.ImageIcon;


public class MenuItem extends javax.swing.JPanel {
    private int index;
    private String title;
    private ImageIcon icon;
    public MenuItem() {
        initComponents();
        
    }

    public void insert(int index, String title, ImageIcon icon) {
        this.index = index;
        this.title = title;
        this.icon = icon;
        render();
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        render();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        render();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        render();
    }
    
    
    
    public void render() {
        btnTitle.setIcon(icon);
        btnTitle.setText(title);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTitle = new javax.swing.JButton();

        setBackground(new java.awt.Color(20, 20, 21));

        btnTitle.setBackground(new java.awt.Color(20, 20, 21));
        btnTitle.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        btnTitle.setForeground(new java.awt.Color(255, 255, 255));
        btnTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Comp/Icon/2_s.png"))); // NOI18N
        btnTitle.setText("Trang chủ");
        btnTitle.setBorder(null);
        btnTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnTitle)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTitle)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTitle;
    // End of variables declaration//GEN-END:variables
}
