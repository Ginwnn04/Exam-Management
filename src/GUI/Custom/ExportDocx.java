package GUI.Custom;
import org.apache.poi.xwpf.usermodel.*;
// import org.w3c.dom.events.MouseEvent;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import BUS.AnswerBUS;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import BUS.TestBUS;
import DTO.TestDTO;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class ExportDocx {
    public static void exportExamToDocx(String examCode, List<QuestionDTO> questions, AnswerBUS answerBUS) {
        //101A -> 101
        String testCode = examCode.substring(0, examCode.length() - 1);
        TestBUS testExamBUS = new TestBUS();
        TestDTO testExam = testExamBUS.getTestByTestCode(testCode);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File(examCode + ".docx")); // Tên file mặc định

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Xuất đề thi bị hủy.");
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(fileToSave)) {

            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runTitle = title.createRun();
            runTitle.setBold(true);
            runTitle.setFontSize(16);
            runTitle.setText("ĐỀ THI: " + examCode);
            runTitle.addBreak();
            
            // Tạo tiêu đề bài kiểm tra
            XWPFParagraph titlePara = document.createParagraph();
            XWPFRun titleRun = titlePara.createRun();
            titleRun.setBold(true);
            titleRun.setFontSize(14);
            titleRun.setText("Tên bài kiểm tra: " + testExam.getTitle());
            titleRun.addBreak();

            // Thêm thông tin thời gian làm bài
            XWPFParagraph timePara = document.createParagraph();
            XWPFRun timeRun = timePara.createRun();
            timeRun.setText("Thời gian làm bài: " + testExam.getTestTime() + " phút");
            timeRun.addBreak();

            // Thêm thông tin ngày thi
            XWPFParagraph datePara = document.createParagraph();
            XWPFRun dateRun = datePara.createRun();
            dateRun.setText("Ngày thi: " + testExam.getTestDate());
            dateRun.addBreak();
            dateRun.addBreak(); 




            for (int i = 0; i < questions.size(); i++) {
                QuestionDTO question = questions.get(i);

                // Thêm câu hỏi
                XWPFParagraph questionPara = document.createParagraph();
                XWPFRun questionRun = questionPara.createRun();
                questionRun.setBold(true);
                questionRun.setText((i + 1) + ". " + question.getContent());
                // questionRun.addBreak();

                // Lấy danh sách câu trả lời
                char[] answerChar = {'a', 'b', 'c', 'd'};
                List<AnswerDTO> answers = answerBUS.getAnswerByQuestionId(question.getId());
                int z = 0;
                for (AnswerDTO answer : answers) {
                    XWPFParagraph answerPara = document.createParagraph();
                    XWPFRun answerRun = answerPara.createRun();
                    answerRun.setText(answerChar[z] +". "+ answer.getContent());
                    if (z == answers.size() - 1) {
                        answerRun.addBreak();
                    }
                    z++;
                }

                
            }

            document.write(out);
           
            // Hiển thị thông báo với đường link
            JLabel messageLabel = new JLabel("<html>Xuất đề thi thành công!<br>Nhấn <a href=''>vào đây</a> để mở file.</html>");
            messageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            messageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().open(fileToSave);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi mở file: " + ex.getMessage());
                    }
                }
            }
            );

// Hiển thị JOptionPane với JLabel (có thể nhấn)
JOptionPane.showMessageDialog(null, messageLabel, "Xuất file", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất đề thi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
