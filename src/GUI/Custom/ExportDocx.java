package GUI.Custom;

import org.apache.poi.xwpf.usermodel.*;
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
import java.sql.Date;
import java.time.LocalDate;
public class ExportDocx {

    private static String ExamDifftoPoint(String examDiff) {
        switch (examDiff) {
            case "easy":
                return "1 điểm";
            case "medium":
                return "2 điểm";
            case "diff":
                return "3 điểm";
            default:
                return "0 điểm";
        }
    }

    private static String setDateFormat(Date date) {
        LocalDate localDate = date.toLocalDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return "Tp.HCM , Ngày " + day+" , tháng "+month+" , năm "+year+" .";
    }

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
            //Tiêu đề trường đại học
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setBold(true);
            titleRun.setFontSize(14);
            titleRun.setText("TRƯỜNG ĐẠI HỌC TÀI NGUYÊN VÀ MÔI TRƯỜNG TP. HỒ CHÍ MINH");
            titleRun.addBreak();
            titleRun.setText("KHOA: Công nghệ thông tin");
            titleRun.addBreak();

            XWPFParagraph dateInfo = document.createParagraph();
            dateInfo.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun dateRun = dateInfo.createRun();
            dateRun.setText(setDateFormat(testExam.getTestDate()));
            
                        // Đề thi kết thúc học phần
            XWPFParagraph examTitle = document.createParagraph();
            examTitle.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun examRun = examTitle.createRun();
            examRun.setBold(true);
            examRun.setFontSize(20);
            examRun.setText("ĐỀ THI "+testExam.getTitle().toUpperCase());
            examRun.addBreak();

            XWPFParagraph info = document.createParagraph();
            info.setAlignment(ParagraphAlignment.CENTER); // Căn lề trái

            XWPFRun run5 = info.createRun();
            run5.setBold(true);
            run5.setText("Hình thức thi: ");
            run5.setFontSize(12);
            XWPFRun run6 = info.createRun();
            run6.setItalic(true);
            run6.setText("TRẮC NGHIỆM");
            run6.addTab();
            XWPFRun run7 = info.createRun();
            run7.setBold(true);
            run7.setText("Thời gian: "+ testExam.getTestTime()+" phút");
            XWPFRun run10 = info.createRun();
            run10.setItalic(true);
            run10.setText("(không kể thời gian phát đề)");
            run10.addBreak();

            XWPFParagraph examCodeInfo = document.createParagraph();
            examCodeInfo.setAlignment(ParagraphAlignment.CENTER); 
            XWPFRun run8 = examCodeInfo.createRun();
            run8.setBold(true);
            run8.setFontSize(20);
            run8.setText("Mã đề: "+examCode);
            run8.addBreak();
            // Thông tin thí sinh
            XWPFParagraph studentInfo = document.createParagraph();
            studentInfo.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun studentRun = studentInfo.createRun();
            studentRun.setText("Họ và tên: ................................................");
            studentRun.addTab();
            studentRun.setText("MSSV: ................................................");
            studentRun.addBreak();
            studentRun.addBreak();
            


            // Create a table with 2 columns
            XWPFTable table = document.createTable(2, 2);

            // Set table width to 100%
            table.setWidth("100%");

            // Set cell size
            XWPFTableCell cell1 = table.getRow(0).getCell(0);
            cell1.setWidth("25%"); // Set cell width
            // cell1.setVerticalAlignment(XWPFVertAlign.TOP);

            XWPFTableCell cell2 = table.getRow(0).getCell(1);
            // cell2.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.TOP);

            XWPFParagraph para1 = cell1.addParagraph();
            para1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run1 = para1.createRun();
            run1.setBold(true);
            run1.setText("ĐIỂM");

            XWPFParagraph para2 = cell2.addParagraph();
            para2.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run2 = para2.createRun();
            run2.setBold(true);
            run2.setText("NHẬN XÉT CỦA GIÁO VIÊN:");

            // Định dạng lại kích thước hàng
            table.getRow(0).setHeight(300);
            table.getRow(1).setHeight(1000);
            
            XWPFParagraph h1 = document.createParagraph();
            h1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun h1Run = h1.createRun();
            h1Run.setBold(true);
            h1Run.setFontSize(14);
            h1Run.addBreak();
            h1Run.addBreak();
            h1Run.setText("NỘI DUNG ĐỀ THI : ");
            h1Run.addBreak();
            h1Run.addBreak();

            for (int i = 0; i < questions.size(); i++) {
                QuestionDTO question = questions.get(i);

                // Thêm câu hỏi
                XWPFParagraph questionPara = document.createParagraph();
                XWPFRun questionRun = questionPara.createRun();
                questionRun.setBold(true);
                String questionPoint = ExamDifftoPoint(question.getLevel());
                questionRun.setText((i + 1) + ". " + question.getContent()+ " (" + questionPoint + ")");
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

            XWPFParagraph endingInfo = document.createParagraph();
            endingInfo.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun endingRun = endingInfo.createRun();
            endingRun.setItalic(true);
            endingRun.setText("-CHÚC CÁC BẠN THI TỐT-");
            endingRun.addBreak();


            document.write(out);
            out.close();
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