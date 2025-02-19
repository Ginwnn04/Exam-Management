package BUS;

import java.util.ArrayList;

import DAO.TestExamDAO;
import DTO.TestExamDTO;

public class TestExamBUS {
    private TestExamDAO DAO = new TestExamDAO();

    public TestExamBUS() {

    }

    public ArrayList<TestExamDTO> getAll(boolean isActive) {
        return DAO.getAll(isActive);
    }

    public TestExamDTO findById(Integer id) {
        return DAO.findById(id);
    }

    public boolean create(TestExamDTO request) {
        return DAO.create(request);
    }

    public boolean update(Integer id, TestExamDTO request) {
        return DAO.update(id, request);
    }

    public boolean delete(Integer id) {
        return DAO.delete(id);
    }
}
