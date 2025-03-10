package BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DAO.TestDAO;
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

    private HashMap<Integer, TopicDTO> topics = new HashMap<>();

    public TestBUS() {
        DAO = new TestDAO();
        questionBUS = new QuestionBUS();
        topicBUS = new TopicBUS();
        examBUS = new ExamBUS();
        testStructureBUS = new TestStructureBUS();
    }

    private void updateMap(List<TopicDTO> list) {
        for (var topic : list) {
            topics.put(topic.getId(), topic);
        }
    }

    public ArrayList<TestDTO> getAll(boolean isActive) {       
        updateMap(topicBUS.getAllTopic());
        return DAO.getAll(isActive);
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

    public TestDTO create(TestDTO request, int examCount, List<TestStructureDTO> listTestStructure) {
        var result = DAO.create(request);

        if (!testStructureBUS.createMultiple(listTestStructure)) return null;
        if (!examBUS.generateExam(request, examCount, listTestStructure)) return null;

        return result;
    }

    public boolean update(Integer id, TestDTO request) {
        return DAO.update(id, request);
    }

    public boolean delete(Integer id) {
        return DAO.delete(id);
    }
}