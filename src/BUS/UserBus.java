
package BUS;

import DAO.UserDao;
import DTO.ExamDTO;
import DTO.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class UserBus {
    private UserDao userDao = new UserDao();

    public ArrayList<UserDTO> getAllUsers() {
        return userDao.getAll(true);
    }

    public UserDTO findByUsername(String username) {
        return userDao.findByUsername(username);
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

    public UserDTO login(String name,String password){
        return userDao.loginUser(name, password);
    }

    public boolean update_OTP_expiredTime(String otp , Date expiredTime, String username) {
        return userDao.update_OTP_expiredTime(otp, expiredTime, username);
    }

    public boolean changePassword(int id, String newPassword) {
        return userDao.updatePassword(id, newPassword);
    }

    // public boolean changePassword(int id, String newPassword) {
       
    //     return userDao.updatePassword(id, newPassword); 
    // }
}
