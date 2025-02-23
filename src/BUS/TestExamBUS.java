package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.TestExamDAO;
import DTO.QuestionDTO;
import DTO.TestExamDTO;

public class TestExamBUS {
    private TestExamDAO DAO;
    private QuestionBUS questionBUS;

    public TestExamBUS() {
        DAO = new TestExamDAO();
        questionBUS = new QuestionBUS();
    }

    public ArrayList<TestExamDTO> getAll(boolean isActive) {
        return DAO.getAll(isActive);
    }

    // delete later when questionBus have method getByTopicId
    public List<QuestionDTO> getByTopicId(int topicId) {
        var listQuestion = questionBUS.getAllQuestion(true);

        return listQuestion.stream()
                           .filter(question -> question.getTopicId() == topicId)
                           .toList();
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
