package DAO;
import DTO.UserDTO;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @Override
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
    @Override
    public boolean create(UserDTO userDTO){
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
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
}
