package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.ResultDTO;
import Helper.ConnectDB;

public class ResultDAO implements BaseDAO<ResultDTO, Integer> {
    private ConnectDB dbHelper = ConnectDB.getInstance();

    private ResultDTO fetchData(ResultSet rs) throws SQLException {
        return ResultDTO.builder()
                        .setResNum(rs.getShort("rs_num"))
                        .setUserId(rs.getInt("userID"))
                        .setExCode(rs.getString("exCode"))
                        .setRsAnswer(rs.getString("rs_anwsers"))
                        .setRsMark(rs.getInt("rs_mark"))
                        .setRsDate(rs.getDate("rs_date"));
    }

    @Override
    public List<ResultDTO> getAll(boolean isActive) {
        String query = "SELECT * FROM result";
        ArrayList<ResultDTO> result = new ArrayList<>();

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ResultDTO model = fetchData(rs);
                result.add(model);
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return result;
    }

    public ResultDTO findById(Integer id) {
        String query = "SELECT * FROM result WHERE rs_num = " + id;

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            return rs.next() ? fetchData(rs) : null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public int getTakeExamTime(int userID, String testCode) {
        String query = "SELECT testCode FROM result INNER JOIN exams " +
                       "ON result.exCode = exams.exCode AND exams.testCode = ? AND result.userID = ?";
        int count = 0;

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, testCode);
            ps.setInt(2, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return count;
    }

    @Override
    public ResultDTO create(ResultDTO request) {
        String query = "INSERT INTO result (userID, exCode, rs_answers, rs_mark, rs_date) " +
                        "VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, request.getUserId());
            ps.setString(2, request.getExCode());
            ps.setString(3, request.getRsAnswer());
            ps.setInt(4, request.getRsMark());
            ps.setDate(5, request.getRsDate());

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
    public boolean update(Integer id, ResultDTO request) {
        String query = "UPDATE result SET userID = ?, exCode = ?, rs_answers = ?, rs_mark = ?, rs_date = ? WHERE rs_num = " + id;

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, request.getUserId());
            ps.setString(2, request.getExCode());
            ps.setString(3, request.getRsAnswer());
            ps.setInt(4, request.getRsMark());
            ps.setDate(5, request.getRsDate());

            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM result WHERE res_num = " + id;
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<ResultDTO> getAllByTestCode(String testCode) {
        String query = "SELECT * FROM result WHERE INSTR(exCode, ?) > 0";
        ArrayList<ResultDTO> result = new ArrayList<>();

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, testCode);

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
