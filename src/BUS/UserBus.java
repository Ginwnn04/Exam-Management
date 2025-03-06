
package BUS;

import DAO.UserDao;
import DTO.ExamDTO;
import DTO.UserDTO;
import java.util.ArrayList;
import java.util.List;

public class UserBus {
    private UserDao userDao = new UserDao();

    public ArrayList<UserDTO> getAllUsers() {
        return userDao.getAll(true);
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
    // public boolean changePassword(int id, String newPassword) {
       
    //     return userDao.updatePassword(id, newPassword); 
    // }
}
