package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.TestDTO;
import Helper.ConnectDB;

public class TestDAO implements BaseDAO<TestDTO, Integer> {
    private ConnectDB dbHelper = ConnectDB.getInstance();

    public TestDAO(){

    }

    private TestDTO fetchData(ResultSet rs) throws SQLException {
        return TestDTO.builder()
                      .setId(rs.getInt("testID"))
                      .setTestCode(rs.getString("testCode"))
                      .setTitle(rs.getString("testTitle"))
                      .setTestLimit(rs.getShort("testLimit"))
                      .setTestTime(rs.getInt("testTime"))
                      .setTestDate(rs.getDate("testDate"))
                      .setTestStatus(rs.getBoolean("testStatus"));
    }

    public ArrayList<TestDTO> getAll(boolean isActive) {
        int isGet = isActive ? 1 : 0;
        String query = "SELECT * FROM test WHERE testStatus = " + isGet;
        ArrayList<TestDTO> result = new ArrayList<>();

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TestDTO model = fetchData(rs);
                result.add(model);
            }

            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    public TestDTO getByTestCode(String testCode) {
        String query = "SELECT * FROM test WHERE testCode = '" + testCode + "'";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
          
            return rs.next() ? fetchData(rs) : null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public TestDTO findById(Integer id) {
        String query = "SELECT * FROM test WHERE testID = ?";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            return rs.next() ? fetchData(rs) : null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public TestDTO create(TestDTO request) {
        String query = "INSERT INTO test (testCode, testTitle, testLimit, testTime, testDate, testStatus) " +
                        "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getTestCode());
            ps.setString(2, request.getTitle());
            ps.setShort(3, request.getTestLimit());
            ps.setInt(4, request.getTestTime());
            ps.setDate(5, request.getTestDate());
            ps.setBoolean(6, request.isTestStatus());

            var result = ps.executeUpdate() > 0;

            if (!result) return null;
            else return request;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(Integer id, TestDTO request) {
        String query = "UPDATE test SET testCode = ?, testTitle = ?, testLimit = ?, testTime = ?, testDate = ?, testStatus = ? WHERE testID = ?";
                        
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getTestCode()); 
            ps.setString(2, request.getTitle());
            ps.setShort(3, request.getTestLimit());
            ps.setInt(4, request.getTestTime());
            ps.setDate(5, request.getTestDate());
            ps.setBoolean(6, request.isTestStatus());
            ps.setInt(7, id);

            return ps.executeUpdate() > 0;

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM test WHERE testID = " + id;

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
