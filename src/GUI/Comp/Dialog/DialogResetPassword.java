package GUI.Comp.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BUS.UserBus;
public class DialogResetPassword extends JDialog {

    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton updatePasswordButton;
    private UserBus BUS;
    private int user_id;

    public DialogResetPassword(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public DialogResetPassword(Frame parent, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        this.user_id = id;
    }

    private void initComponents() {
        setTitle("Reset Password");
        setSize(400, 200);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // New password label
        JLabel newPasswordLabel = new JLabel("Nhập mật khẩu mới:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(newPasswordLabel, gbc);

        // New password field
        newPasswordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(newPasswordField, gbc);

        // Confirm password label
        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu mới:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(confirmPasswordLabel, gbc);

        // Confirm password field
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(confirmPasswordField, gbc);

        // Update password button
        updatePasswordButton = new JButton("Cập nhật mật khẩu");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        add(updatePasswordButton, gbc);

        // Action listener for update password button
        updatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePasswordButtonActionPerformed(e);
            }
        });
    }

    private void updatePasswordButtonActionPerformed(ActionEvent e) {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if(validatePassword(newPassword, confirmPassword))
            updatePassword(user_id, newPassword);
        else{
            newPasswordField.setText("");
            confirmPasswordField.setText("");
        }
        
        dispose();
    }

    private void updatePassword(int user_id, String newPassword) {
        BUS = new UserBus();
        if (BUS.changePassword(user_id, newPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu đã được cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thất bại!");
        }
    }

    private boolean validatePassword(String newPassword, String confirmPassword) {
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        else if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        DialogResetPassword dialog = new DialogResetPassword(null, true);
        dialog.setVisible(true);
    }
}