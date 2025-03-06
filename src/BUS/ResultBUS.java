package BUS;

import java.util.List;

import DAO.ResultDAO;
import DTO.ExamDTO;
import DTO.ResultDTO;
import DTO.TestExamDTO;
import DTO.UserDTO;

public class ResultBUS {
    private ResultDAO DAO = new ResultDAO();

    public ResultBUS() {

    }

    public List<ResultDTO> getAll() {
        return DAO.getAll(true);
    }

    public int getTakeExamTime(UserDTO user, TestExamDTO test) {
        return DAO.getTakeExamTime((int)user.getId(), test.getTestCode());
    }

    public ResultDTO findById(int id) {
        return DAO.findById(id);
    }

    public ResultDTO create(ResultDTO request) {
        return DAO.create(request);
    }

    public boolean update(int id, ResultDTO request) {
        return DAO.update(id, request);
    }

    public boolean delete(int id) {
        return DAO.delete(id);
    }
}
