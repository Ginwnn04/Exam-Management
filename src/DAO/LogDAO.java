package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.LogDTO;
import Helper.ConnectDB;

public class LogDAO implements BaseDAO<LogDTO, Integer> {
    private final ConnectDB dbHelper = ConnectDB.getInstance();

    private LogDTO fetchData(ResultSet rs) throws SQLException {
        return LogDTO.builder()
                     .setLogId(rs.getInt("logID"))
                     .setLogUserID(rs.getInt("logUserID"))
                     .setLogExCode(rs.getString("logExCode"))
                     .setLogContent(rs.getString("logContent"))
                     .setLogDate(rs.getDate("logDate"));
    }

    @Override
    public List<LogDTO> getAll(boolean active) {
        String query = "SELECT * FROM logs";
        ArrayList<LogDTO> result = new ArrayList<>();

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

    public LogDTO findByUserCodeAndExCode(int userId, String exCode) {
        String query = "SELECT * FROM logs WHERE logUserID = ? AND logExCode = ?";

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, exCode);

            ResultSet rs = ps.executeQuery();
            return rs.next() ? fetchData(rs) : null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public LogDTO create(LogDTO request) {
        String query = "INSERT INTO logs (logID, logContent, logUserID, logExCode, logDate) VALUES (?, ?, ?, ?, ?)";
        System.out.println(query);

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setInt(1, request.getLogId());
            ps.setString(2, request.getLogContent());
            ps.setInt(3, request.getLogUserID());
            ps.setString(4, request.getLogExCode());
            ps.setDate(5, request.getLogDate());

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
    public boolean update(Integer id, LogDTO request) {
        String query = "UPDATE logs SET logContent = ?, logUserID = ?, logExCode = ?, logDate = ? WHERE logID = " + id;
        System.out.println(query);

        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(query);
            ps.setString(1, request.getLogContent());
            ps.setInt(2, request.getLogId());
            ps.setString(3, request.getLogExCode());
            ps.setDate(4, request.getLogDate());

            return ps.executeUpdate() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM logs WHERE logID = " + id;

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
