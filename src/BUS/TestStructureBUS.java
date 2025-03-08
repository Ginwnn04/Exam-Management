package BUS;

import java.util.List;

import DAO.TestStructureDAO;
import DTO.TestStructureDTO;

public class TestStructureBUS {
    private TestStructureDAO DAO = new TestStructureDAO();

    public TestStructureBUS() {

    }

    public List<TestStructureDTO> getAll() {
        return DAO.getAll(true);
    }

    public List<TestStructureDTO> getAllByTestCode(String testCode) {
        return DAO.getAllByTestCode(testCode);
    }

    public TestStructureDTO create(TestStructureDTO request) {
        return DAO.create(request);
    }

    public boolean createMultiple(List<TestStructureDTO> requests) {
        return DAO.createMultiple(requests);
    }

    public boolean update(String testCode, TestStructureDTO request) {
        return DAO.update(testCode, request);
    }

    public boolean delete(String testCode) {
        return DAO.delete(testCode);
    }
}
