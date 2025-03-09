package DAO;
import DTO.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements BaseDAO<UserDTO, Integer> {
    @Override
    public ArrayList<UserDTO> getAll(boolean active) {
        ArrayList<UserDTO> list_users = new ArrayList<UserDTO>();
        String query = "SELECT * FROM users";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserDTO user = UserDTO.builder()
                .setId(resultSet.getInt("userID"))
                .setName(resultSet.getString("userName"))
                .setEmail(resultSet.getString("userEmail"))
                .setPassword(resultSet.getString("userPassword"))
                .setFullName(resultSet.getString("userFullName"))
                .setIsAdmin(resultSet.getInt("isAdmin"))
                .build();
                list_users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_users;
    }

    public UserDTO findByID(Integer id){
        String query = "SELECT * FROM users WHERE userID = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserDTO user = UserDTO.builder()
                .setId(resultSet.getInt("userID"))
                .setName(resultSet.getString("userName"))
                .setEmail(resultSet.getString("userEmail"))
                .setPassword(resultSet.getString("userPassword"))
                .setFullName(resultSet.getString("userFullName"))
                .setIsAdmin(resultSet.getInt("isAdmin"))
                .build();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExist(String fullName){
        String query = "SELECT * FROM users WHERE is_deleted = FALSE AND name = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setString(1, fullName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
        public UserDTO loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE userName = ? AND userPassword = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) { 
                UserDTO user = new UserDTO();
                user.setId(rs.getInt("userID"));
                user.setName(rs.getString("userName"));
                user.setEmail(rs.getString("userEmail"));
                // user.setPassword(rs.getString("userPassword"));
                user.setFullName(rs.getString("userFullName")) ;    
               user.setIsAdmin(rs.getInt("isAdmin"));
             
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return null; 
    }

    @Override
    public UserDTO create(UserDTO userDTO){
        String query = "INSERT INTO users(  userName, userEmail, userPassword, userFullname, isAdmin) VALUES(  ?, ?, ?, ?, ?)";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            // preparedStatement.setLong(1,userDTO.createId());
            preparedStatement.setString(1, userDTO.getName());
            preparedStatement.setString(2, userDTO.getEmail());
            preparedStatement.setString(3, userDTO.getPassword());
            preparedStatement.setString(4, userDTO.getFullName());
            preparedStatement.setInt(5, userDTO.getIsAdmin());
            // preparedStatement.setBoolean(6, userDTO.getIsDeleted());
            var result = preparedStatement.executeUpdate() > 0;

            if (!result) return null;
            else return userDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean update(Integer id , UserDTO userDTO){
        String query = "UPDATE users SET userName = ?, userEmail = ?, userPassword = ?, userFullname = ?, isAdmin = ? WHERE userID = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setString(1, userDTO.getName());
            preparedStatement.setString(2, userDTO.getEmail());
            preparedStatement.setString(3, userDTO.getPassword());
            preparedStatement.setString(4, userDTO.getFullName());
            preparedStatement.setInt(5, userDTO.getIsAdmin());
            // preparedStatement.setBoolean(6, userDTO.getIsDeleted());
            preparedStatement.setLong(6, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean delete(Integer id){
        String query = "DELETE from users WHERE userID = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  boolean updatePassword(int id, String newPassword) {
        String query = "UPDATE users SET userPassword = ? WHERE userID = ?";
        try (PreparedStatement ps = Helper.ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
