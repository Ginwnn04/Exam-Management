package GUI.Custom;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import BUS.QuestionBUS;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import DTO.QuestionDTO;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class ButtonImportQuestion extends javax.swing.JPanel{
    public static QuestionBUS BUS;

    public ButtonImportQuestion() {

        initComponents();
        btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void initComponents(){
        btnImport = new GUI.Comp.DateChooser.Button();
        btnImport.setBackground(new java.awt.Color(225, 99, 33)); // Màu đỏ đậm hơn
        btnImport.setFont(new java.awt.Font("Roboto", Font.BOLD, 16));
        btnImport.setForeground(Color.WHITE); // Chữ trắng
        btnImport.setPreferredSize(new Dimension(150, 40)); // Kích thước chữ nhật rõ hơn
        btnImport.setFocusPainted(false); // Bỏ viền khi focus
        btnImport.setBorderPainted(false); // Không vẽ viền
        btnImport.setOpaque(true); // Không trong suốt
        btnImport.setContentAreaFilled(true); // Giữ màu nền
        btnImport.setText("Import");
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
        ArrayList<QuestionDTO> questionList = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                
            String content = getCellValueAsString(row.getCell(0));
            String picture = getCellValueAsString(row.getCell(1));
            int topicId = parseTopicId(row.getCell(2));
            String level = getCellValueAsString(row.getCell(3));
            boolean status = parseTopicId(row.getCell(4)) == 1;
            
                // Tạo đối tượng QuestionDTO
                QuestionDTO question = QuestionDTO.builder()
                        .setContent(content)
                        .setPicture(picture)
                        .setTopicId(topicId)
                        .setLevel(level)
                        .setStatus(status)
                        .build();
                System.out.println(
                    "content : "+question.getContent()
                    +"\n img: "+question.getPicture()
                    +"\n tpID: "+question.getTopicId()
                    +"\n level: "+question.getLevel()
                    +"\n status: "+question.isStatus()
                    );
                questionList.add(question);
            }
            workbook.close();
            
            ArrayList<QuestionDTO> result = importQuestions(questionList);
            if(result.size() > 0) {
                JOptionPane.showMessageDialog(null, "Import thành công " + result.size() + " câu hỏi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Import thất bại! Vui lòng kiểm tra lại file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Import thất bại! Vui lòng kiểm tra lại file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<QuestionDTO> importQuestions(ArrayList<QuestionDTO> questionList) {
        ArrayList<QuestionDTO> result = new ArrayList<>();
        BUS = new QuestionBUS();
       for(var question : questionList) {
           QuestionDTO item = BUS.createQuestion(question);
              if (item != null) {
                result.add(item);
              }
       }
       return result;
    }

    private int parseTopicId(Cell topicCell) {
        if (topicCell == null) return 0; // Trả về 0 nếu ô trống
        if (topicCell.getCellType() == CellType.NUMERIC) {
            return (int) topicCell.getNumericCellValue();
        } else if (topicCell.getCellType() == CellType.STRING) {
            String value = topicCell.getStringCellValue().trim();
            if (value.matches("\\d+")) { // Kiểm tra nếu chuỗi chứa toàn số
                return Integer.parseInt(value);
            }
        }
        return 0; // Trả về 0 nếu không hợp lệ
    }
    
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()); // Chuyển số thành chuỗi
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private GUI.Comp.DateChooser.Button btnImport;
}
