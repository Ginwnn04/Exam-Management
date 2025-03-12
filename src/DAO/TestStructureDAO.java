package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.TestStructureDTO;
import Helper.ConnectDB;

public class TestStructureDAO implements BaseDAO<TestStructureDTO, String> {
    private ConnectDB dbHelper = ConnectDB.getInstance();

    private TestStructureDTO fetchData(ResultSet rs) throws SQLException {
        return TestStructureDTO.builder()
                               .setTestCode(rs.getString("testCode"))
                               .setTopicID(rs.getInt("tpID"))
                               .setNumEasy(rs.getInt("num_easy"))
                               .setNumMedium(rs.getInt("num_medium"))
                               .setNumDiff(rs.getInt("num_diff"));
    }

    @Override
    public TestStructureDTO create(TestStructureDTO request) {
        String query = "INSERT INTO test_structure (testCode, tpID, numEasy, numMedium, numDiff) " +
                       "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getTestCode());
            ps.setInt(2, request.getTopicID());
            ps.setInt(3, request.getNumEasy());
            ps.setInt(4, request.getNumMedium());
            ps.setInt(5, request.getNumDiff());

            var result = ps.executeUpdate() > 0;

            if (!result)
                return null;
            else
                return request;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean createMultiple(List<TestStructureDTO> requests) {
        String query = "INSERT INTO test_structure (testCode, tpID, num_easy, num_medium, num_diff) VALUES ";

        for (int i = 0; i < requests.size(); i++) {
            var request = requests.get(i);

            String format;
            if (i < requests.size() - 1) format = "('%s', %d, %d, %d, %d), ";
            else format = "('%s', %d, %d, %d, %d);";

            query += String.format(format, 
                                   request.getTestCode(), 
                                   request.getTopicID(),
                                   request.getNumEasy(),
                                   request.getNumMedium(),
                                   request.getNumDiff());
        }

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            return ps.executeUpdate() > 0;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(String id, TestStructureDTO request) {
        String query = "UPDATE test_structure SET testCode = ?, tpID = ?, numEasy = ?, " +
                       "numMedium = ?, numDiff = ? WHERE testCode = ?;";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getTestCode());
            ps.setInt(2, request.getTopicID());
            ps.setInt(3, request.getNumEasy());
            ps.setInt(4, request.getNumMedium());
            ps.setInt(5, request.getNumDiff());

            return ps.executeUpdate() > 0;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM test_structure WHERE testCode = " + id;

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public List<TestStructureDTO> getAll(boolean active) {
        String query = "SELECT * FROM test_structure";
        ArrayList<TestStructureDTO> result = new ArrayList<>();
        
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(fetchData(rs));
            }

            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<TestStructureDTO> getAllByTestCode(String testCode) {
        String query = "SELECT * FROM test_structure WHERE testCode = " + testCode;
        ArrayList<TestStructureDTO> result = new ArrayList<>();
        
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(fetchData(rs));
            }

            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
