
package BUS;

import DAO.UserDao;
import DTO.ExamDTO;
import DTO.UserDTO;
import GUI.Utils.Encryptor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;

public class UserBus {
    private UserDao userDao = new UserDao();

    public ArrayList<UserDTO> getAllUsers() {
        return userDao.getAll(true);
    }

    public boolean login(String username, String password) {
        UserDTO user = userDao.findByUsername(username);
        if (user == null) return false;
        String passwordHash = Encryptor.encrypt(password);
        if (user.getPassword().equals(passwordHash)) return true;
        else return false;
    }
    
    public UserDTO findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    public UserDTO findByEmail(String email) {
        return userDao.findByEmail(email);
    }
    public UserDTO findByID(Integer id) {
        return userDao.findByID(id);
    }

    public boolean isExist(String fullName) {
        return userDao.isExist(fullName);
    }

    public UserDTO addUser(UserDTO user) {
        return userDao.create(user);
    }

    public boolean updateUser(UserDTO user,Integer id) {
        return userDao.update(id,user);
    }

    public boolean deleteUser(Integer id) {
        return userDao.delete(id);
    }


    public boolean update_OTP_expiredTime(String otp , Date expiredTime, String username) {
        return userDao.update_OTP_expiredTime(otp, expiredTime, username);
    }

    public boolean changePassword(int id, String newPassword) {
        String passwordHash = Encryptor.encrypt(newPassword);
        return userDao.updatePassword(id, passwordHash);
    }


    public ArrayList<UserDTO> searchUser(String query){
        ArrayList<UserDTO> usersList = userDao.getAll(true);
        ArrayList<UserDTO> result = new ArrayList<>();
        for(UserDTO user : usersList){
            result.addAll(setUpFilter(user,query));
        }
        return result;
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
    // public boolean changePassword(int id, String newPassword) {
       
    //     return userDao.updatePassword(id, newPassword); 
    // }


    public ArrayList<UserDTO> ImportUsers(String filePath){
        ArrayList<UserDTO> userList = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if(row.getRowNum() == 0) continue; 
                setValueByCellRow(row, userList);
            }
            workbook.close();
            return addManyUsers(userList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private ArrayList<UserDTO> addManyUsers(ArrayList<UserDTO> userList) {
        ArrayList<UserDTO> result = new ArrayList<>();
        for (UserDTO user : userList) {
            if (userDao.create(user)!=null) {
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
}
