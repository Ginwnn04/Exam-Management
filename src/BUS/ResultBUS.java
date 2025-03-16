package BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import DAO.ResultDAO;
import DTO.AnswerDTO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import DTO.ResultDTO;
import DTO.TestDTO;
import DTO.UserDTO;

public class ResultBUS {
    private ResultDAO DAO = new ResultDAO();
    private QuestionBUS questionBUS = new QuestionBUS();
    private HashMap<Integer, QuestionDTO> questions;

    public ResultBUS() {

    }

    private void prepareResource() {
        questions = new HashMap<>();
        var list = questionBUS.getAllQuestion(true);

        for (QuestionDTO question : list) {
            questions.put(question.getId(), question);
        }

    }

    public List<ResultDTO> getAll() {
        return DAO.getAll(true);
    }

    public List<ResultDTO> getAllByUser(UserDTO user) {
        int userId = user.getId();
        return DAO.getAllByUserId(userId);
    }

    public int getTakeExamTime(UserDTO user, TestDTO test) {
        return DAO.getTakeExamTime((int)user.getId(), test.getTestCode());
    }

    public ResultDTO findById(int id) {
        return DAO.findById(id);
    }

    public ResultDTO findByUserAndExam(UserDTO user, ExamDTO exam) {
        return DAO.findByUserIdAndExCode(user.getId(), exam.getExCode());
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

    public List<ResultDTO> getAllByTestExam(TestDTO testExam) {
        return DAO.getAllByTestCode(testExam.getTestCode());
    }
    
    public int getScore(int questionId) {
        if (questions == null) prepareResource();

        var question = questions.get(questionId);
        String level = question.getLevel();

        switch (level) {
            case "easy":
                return 1;

            case "medium":
                return 2;

            case "diff":
                return 3;

            default:
                return 0;
        }

    }
}
