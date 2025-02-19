package BUS;
import DAO.UserDao;
import DTO.UserDTO;
import java.util.ArrayList;

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

    public boolean addUser(UserDTO user) {
        return userDao.create(user);
    }

    public boolean updateUser(UserDTO user,Integer id) {
        return userDao.update(id,user);
    }

    public boolean deleteUser(Integer id) {
        return userDao.delete(id);
    }

}
