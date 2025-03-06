/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Utils;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author quang
 */
public class Email {
    static final String from = "quangdeeptry1911@gmail.com";
    static final String password = "hmayehbweofjrkzu";
    
    public static String generateCodeOtp() {
        String patern = "0123456789";
        String result = "";
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            result += patern.charAt(random.nextInt(10));
        }
        return result;
    }
    
    public static long getExpiredTime() {
        return new Date().getTime() + (5 * 60 * 1000); // 5 mins
    }
    
    public static boolean isExpired(long time) {
        return new Date().before(new Date(time));
    }
    
    public static boolean sendEmail(String to, String tieuDe, String noiDung) {
        // Properties : khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        // TODO Auto-generated method stub
                        return new PasswordAuthentication(from, password);
                }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
                // Kiểu nội dung
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

                // Người gửi
                msg.setFrom(from);

                // Người nhận
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

                // Tiêu đề email
                msg.setSubject(tieuDe);

                // Quy đinh ngày gửi
                msg.setSentDate(new Date());

                // Quy định email nhận phản hồi
                // msg.setReplyTo(InternetAddress.parse(from, false))

                // Nội dung
                msg.setContent(noiDung, "text/HTML; charset=UTF-8");

                // Gửi email
                Transport.send(msg);
                System.out.println("Gửi email thành công");
                return true;
        } catch (Exception e) {
                System.out.println("Gặp lỗi trong quá trình gửi email");
                e.printStackTrace();
                return false;
        }
    }
}
