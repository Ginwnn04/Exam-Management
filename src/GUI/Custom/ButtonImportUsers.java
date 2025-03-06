package GUI.Custom;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import DTO.UserDTO;
import BUS.UserBus;

public class ButtonImportUsers extends javax.swing.JPanel {
    private static UserBus BUS;
    public ButtonImportUsers() {
        initComponents();
    }

    private void initComponents() {
        btnImport = new GUI.Comp.DateChooser.Button();
        btnImport.setBackground(new java.awt.Color(225, 99, 33)); 
        btnImport.setFont(new java.awt.Font("Roboto", Font.BOLD, 16));
        btnImport.setForeground(Color.WHITE); 
        btnImport.setPreferredSize(new Dimension(150, 40));
        btnImport.setFocusPainted(false); 
        btnImport.setBorderPainted(false); 
        btnImport.setOpaque(true); 
        btnImport.setContentAreaFilled(true);
        btnImport.setText("Nhập danh sách");
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
        ArrayList<UserDTO> userList = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if(row.getRowNum() == 0) continue; 
                setValueByCellRow(row, userList);
            }
            workbook.close();
            ArrayList<UserDTO> rs = importUsers(userList);
            showOptionPane(rs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOptionPane(ArrayList<UserDTO> rs) {
        if(rs.size() > 0) {
            JOptionPane.showMessageDialog(null, "Import thành công " + rs.size() + " danh sách dự thi ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Import thất bại! Vui lòng kiểm tra lại file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setValueByCellRow(Row row , ArrayList<UserDTO> userList) {
        String username = getCellValueAsString(row.getCell(0));
        String email = getCellValueAsString(row.getCell(1));
        String password = getCellValueAsString(row.getCell(2));
        String fullname = getCellValueAsString(row.getCell(3));
        UserDTO user = UserDTO.builder()
            .setName(username)
            .setPassword(password)
            .setFullName(fullname)
            .setEmail(email)
            .setIsAdmin(0)
            .build();
        userList.add(user);
    }

    private ArrayList<UserDTO> importUsers(ArrayList<UserDTO> userList) {
        ArrayList<UserDTO> result = new ArrayList<>();
        BUS = new UserBus();
        for (UserDTO user : userList) {
            if (BUS.addUser(user)!=null) {
                result.add(user);
            }
        }
        return result;
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
