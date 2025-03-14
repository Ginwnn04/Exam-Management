package BUS;

import java.sql.Date;
import java.util.List;

import DAO.LogDAO;
import DTO.ExamDTO;
import DTO.LogDTO;
import DTO.UserDTO;
import GUI.Utils.UserSession;

public class LogBUS {
    private LogDAO DAO = new LogDAO();

    public LogBUS() {

    }

    public List<LogDTO> getAll() {
        return DAO.getAll(true);
    }

    public LogDTO findByUserAndExam(UserDTO user, ExamDTO exam) {
        return DAO.findByUserCodeAndExCode(user.getId(), exam.getExCode());
    }

    public LogDTO create(LogDTO request) {
        return DAO.create(request);
    }

    public boolean update(int id, LogDTO request) {
        return DAO.update(id, request);
    }

    public boolean delete(int id) {
        return DAO.delete(id);
    }
}
