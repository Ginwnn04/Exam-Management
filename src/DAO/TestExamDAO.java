package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.TestExamDTO;
import Helper.ConnectDB;

public class TestExamDAO implements BaseDAO<TestExamDTO, Integer> {
    private ConnectDB dbHelper = ConnectDB.getInstance();

    public TestExamDAO(){

    }

    public ArrayList<TestExamDTO> getAll(boolean isActive) {
        int isGet = isActive ? 1 : 0;
        String query = "SELECT * FROM test WHERE testStatus = " + isGet;
        ArrayList<TestExamDTO> result = new ArrayList<>();

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TestExamDTO model = TestExamDTO.builder()
                                               .setId(rs.getInt("testID"))
                                               .setTestCode(rs.getString("testCode"))
                                               .setTitle(rs.getString("testTitle"))
                                               .setTopicId(rs.getInt("tpID"))
                                               .setEasyQuestionCount(rs.getInt("num_easy"))
                                               .setMediumQuestionCount(rs.getInt("num_medium"))
                                               .setDiffQuestionCount(rs.getInt("num_diff"))
                                               .setTestLimit(rs.getShort("testLimit"))
                                               .setTestTime(rs.getInt("testTime"))
                                               .setTestDate(rs.getDate("testDate"))
                                               .setTestStatus(rs.getBoolean("testStatus"));

                result.add(model);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return result;
    }

    public TestExamDTO findById(Integer id) {
        String query = "SELECT * FROM test WHERE testID = ?";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return TestExamDTO.builder()
                              .setId(rs.getInt("testID"))
                              .setTestCode(rs.getString("testCode"))
                              .setTitle(rs.getString("testTitle"))
                              .setTopicId(rs.getInt("tpID"))
                              .setEasyQuestionCount(rs.getInt("num_easy"))
                              .setMediumQuestionCount(rs.getInt("num_medium"))
                              .setDiffQuestionCount(rs.getInt("num_diff"))
                              .setTestLimit(rs.getShort("testLimit"))
                              .setTestTime(rs.getInt("testTime"))
                              .setTestDate(rs.getDate("testDate"))
                              .setTestStatus(rs.getBoolean("testStatus"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean create(TestExamDTO request) {
        String query = "INSERT INTO test (testCode, testTitle, tpID, num_easy, num_medium, num_diff, testLimit, testTime, testDate, testStatus) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getTestCode());
            ps.setString(2, request.getTitle());
            ps.setInt(3, request.getTopicId());
            ps.setInt(4, request.getEasyQuestionCount());
            ps.setInt(5, request.getMediumQuestionCount());
            ps.setInt(6, request.getDiffQuestionCount());
            ps.setShort(7, request.getTestLimit());
            ps.setInt(8, request.getTestTime());
            ps.setDate(9, request.getTestDate());
            ps.setBoolean(10, request.isTestStatus());

            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Integer id, TestExamDTO request) {
        String query = "UPDATE test SET testCode = ?, testTitle = ?, tpID = ?, num_easy = ?, num_medium = ?," +
                        " num_diff = ?, testLimit = ?, testTime = ?, testDate = ?, testStatus = ? WHERE testID = ?";
                        
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getTestCode()); 
            ps.setString(2, request.getTitle());
            ps.setInt(3, request.getTopicId());
            ps.setInt(4, request.getEasyQuestionCount());
            ps.setInt(5, request.getMediumQuestionCount());
            ps.setInt(6, request.getDiffQuestionCount());
            ps.setShort(7, request.getTestLimit());
            ps.setInt(8, request.getTestTime());
            ps.setDate(9, request.getTestDate());
            ps.setBoolean(10, request.isTestStatus());
            ps.setInt(11, id);

            return ps.executeUpdate() > 0;

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM test WHERE testID = ?";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
