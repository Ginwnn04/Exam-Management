package BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import DAO.TestExamDAO;
import DTO.QuestionDTO;
import DTO.TestExamDTO;
import DTO.TopicDTO;

public class TestExamBUS {
    private TestExamDAO DAO;
    private QuestionBUS questionBUS;
    private TopicBUS topicBUS;
    private ExamBUS examBUS;

    private HashMap<Integer, TopicDTO> topics = new HashMap<>();

    public TestExamBUS() {
        DAO = new TestExamDAO();
        questionBUS = new QuestionBUS();
        topicBUS = new TopicBUS();
        examBUS = new ExamBUS();
    }

    private void updateMap(List<TopicDTO> list) {
        for (var topic : list) {
            topics.put(topic.getId(), topic);
        }
    }

    public ArrayList<TestExamDTO> getAll(boolean isActive) {       
        updateMap(topicBUS.getAllTopic());
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

    public TestExamDTO findByTestCode(String testCode) {
        return DAO.findByTestCode(testCode);
    }

    public TestExamDTO create(TestExamDTO request, int examCount) {
        var result = DAO.create(request);
        examBUS.generateExam(request, examCount);

        return result;
    }

    public boolean update(Integer id, TestExamDTO request) {
        return DAO.update(id, request);
    }

    public boolean delete(Integer id) {
        return DAO.delete(id);
    }

    private boolean isTopicSelectChild(TopicDTO topic, int selectedTopicId) {
        int parentId = topic.getParent();

        if (parentId == 0) return false;
        if (parentId == selectedTopicId) return true;

        return isTopicSelectChild(topics.get(parentId), selectedTopicId);
    }

    public ArrayList<TestExamDTO> filtByTopic(TopicDTO topic, ArrayList<TestExamDTO> list) {
        ArrayList<TestExamDTO> result = new ArrayList<>();
        int selectedTopicId = topic.getId();

        for (var test : list) {
            if (test.getTopicId() == selectedTopicId) {
                result.add(test);
                continue;
            }

            var testTopic = topics.get(test.getTopicId());
            if (isTopicSelectChild(testTopic, selectedTopicId)) result.add(test);
        }

        return result;
    }

    public ArrayList<QuestionDTO> shuffleQuestions(ArrayList<QuestionDTO> questions) {
        int n = questions.size();
        Random rand = new Random();

        while (n > 1) {
            n--;
            int index = rand.nextInt(n);

            QuestionDTO temp = questions.get(n);
            questions.set(n, questions.get(index));
            questions.set(index, temp);
        }

        return questions;
    }
}