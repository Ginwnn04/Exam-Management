package BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DAO.TestDAO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import DTO.TestDTO;
import DTO.TestStructureDTO;
import DTO.TopicDTO;

public class TestBUS {
    private TestDAO DAO;
    private QuestionBUS questionBUS;
    private TopicBUS topicBUS;
    private ExamBUS examBUS;
    private TestStructureBUS testStructureBUS;


    public TestBUS() {
        DAO = new TestDAO();
        questionBUS = new QuestionBUS();
        topicBUS = new TopicBUS();
        examBUS = new ExamBUS();
        testStructureBUS = new TestStructureBUS();
    }

    public ArrayList<TestDTO> getAll(boolean isActive) {       
        return DAO.getAll(isActive);
    }

    public ArrayList<TestDTO> getAll() {
        return DAO.getAll();
    }

    public TestDTO getTestByTestCode(String testCode) {
        return DAO.getByTestCode(testCode);
    }

    // delete later when questionBus have method getByTopicId
    public List<QuestionDTO> getByTopicId(int topicId) {
        var listQuestion = questionBUS.getAllQuestion(true);

        return listQuestion.stream()
                           .filter(question -> question.getTopicId() == topicId)
                           .toList();
    }

    public TestDTO findById(Integer id) {
        return DAO.findById(id);
    }

    public TestDTO findByTestCode(String testCode) {
        return DAO.getByTestCode(testCode);
    }

    public TestDTO findByExam(ExamDTO exam) {
        int length = exam.getExCode().length();
        String testCode = exam.getExCode().substring(0, length - 1);
        return DAO.getByTestCode(testCode);
    }

    public TestDTO create(TestDTO request, int examCount, List<TestStructureDTO> listTestStructure) {
        var result = DAO.create(request);

        if (!testStructureBUS.createMultiple(listTestStructure)) return null;
        if (!examBUS.generateExam(request, examCount, listTestStructure)) return null;

        return result;
    }

    public boolean update(Integer id, TestDTO request) {
        return DAO.update(id, request);
    }

    public boolean delete(Integer id, String testCode) {
        examBUS.deleteExam(testCode);
        testStructureBUS.delete(testCode);
        return DAO.delete(id);
    }

    public int getMaxScore(String testCode) {
        var list = testStructureBUS.getAllByTestCode(testCode);
        int maxScore = 0;

        for (TestStructureDTO testStructure : list) {
            int easyCount = testStructure.getNumEasy();
            int mediumCount = testStructure.getNumMedium();
            int diffCount = testStructure.getNumDiff();

            maxScore += easyCount * getScore("easy");
            maxScore += mediumCount * getScore("medium");
            maxScore += diffCount * getScore("diff");
        }

        return maxScore;
    }

    private int getScore(String level) {
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