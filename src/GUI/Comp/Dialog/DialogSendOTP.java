package GUI.Comp.Dialog;

import javax.swing.*;

import BUS.UserBus;
import DTO.UserDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.Utils.Email;
import java.sql.Date;

public class DialogSendOTP extends JDialog {

    private JTextField emailField;
    private JTextField otpField;
    private JButton sendOtpButton;
    private JButton confirmButton;
    private UserBus BUS;
    private String username;
    private Date expired_time;

    private static int timeLeft ; // Thời gian đếm ngược (giây)
    private static Timer timer;

    public DialogSendOTP(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public DialogSendOTP(Frame parent, boolean modal, String username) {
        super(parent, modal);
        initComponents();
        setEmailField(username);
        this.username = username;
    }

    private void setEmailField(String username){
        BUS = new UserBus();
        String email = BUS.findByUsername(username).getEmail();
        emailField.setText(email);
    }

    private void initComponents() {
        setTitle("Send OTP");
        setSize(400, 200);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        // Email field
        emailField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        add(emailField, gbc);
        // Send OTP button
        sendOtpButton = new JButton("Gửi OTP");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(sendOtpButton, gbc);
        // OTP field
        otpField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        add(otpField, gbc);
        // Confirm button
        confirmButton = new JButton("Xác nhận");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(confirmButton, gbc);
        sendOtpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendOtpButtonActionPerformed(e);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmButtonActionPerformed(e);
            }
        });
    }

    private void sendOtpButtonActionPerformed(ActionEvent e) {
        String emailText = emailField.getText();
        String otp = Email.generateCodeOtp();
        long time = Email.getExpiredTime();
        expired_time = new Date(time);
        BUS = new UserBus();
        if(BUS.update_OTP_expiredTime(otp, expired_time, emailText)){
            Email.sendEmail(emailText, "OTP", "Mã OTP của bạn là: " + otp);
            JOptionPane.showMessageDialog(this, "Đã gửi OTP đến email của bạn");
            startCountdown(sendOtpButton);
        } else {
            JOptionPane.showMessageDialog(this, "Gửi OTP thất bại");
        }
    }

    public static void startCountdown(JButton btnOTP) {
        btnOTP.setEnabled(false); // Vô hiệu hóa nút
        timeLeft = 60; // Reset thời gian
        timer = new Timer(1000, new ActionListener() { // Cập nhật mỗi giây
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                btnOTP.setText("Gửi lại OTP (" + timeLeft + "s)");

                if (timeLeft <= 0) {
                    timer.stop(); // Dừng timer
                    btnOTP.setText("Gửi OTP"); // Hiển thị lại "Gửi OTP"
                    btnOTP.setEnabled(true); // Bật lại nút
                }
            }
        });
        timer.start(); // Bắt đầu đếm ngược
    }

    private void confirmButtonActionPerformed(ActionEvent e) {
        long time = expired_time.getTime();
        boolean isExpired = Email.isExpired(time);
        String otpText = otpField.getText();
        BUS = new UserBus();
        UserDTO user = BUS.findByUsername(username);
        String otp = user.getOtp();
        int user_id = user.getId();
        if(isExpired && otp.equals(otpText)){
            Dialog resetPassDialog = new DialogResetPassword(null, true, user_id);
            resetPassDialog.setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "OTP đã hết hạn hoặc không chính xác");
        }
    }

    public static void main(String[] args) {
        DialogSendOTP dialog = new DialogSendOTP(null, true);
        dialog.setVisible(true);
    }
}