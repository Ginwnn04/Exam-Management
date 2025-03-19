package BUS;

import DAO.ExamDAO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import DTO.TestDTO;
import DTO.TestStructureDTO;
import Enum.LevelEnum;
import GUI.Utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ExamBUS {
    private ExamDAO ExamDAO = new ExamDAO();
    private QuestionBUS questionBUS = new QuestionBUS();

    public ArrayList<ExamDTO> filterTable(String search, String selectedMaDe, String selectedThuTu) {
        ArrayList<ExamDTO> listExam = ExamDAO.getAll(true);
        ArrayList<ExamDTO> listExamFilter = new ArrayList<>();
        for (ExamDTO exam : listExam) {
            boolean matchesMaDe = selectedMaDe.equals("Tất cả") || exam.getTestCode().equals(selectedMaDe);
            boolean matchesThuTu = selectedThuTu.equals("Tất cả") || exam.getExOrder().equals(selectedThuTu);
            boolean matchesSearch = exam.getTestCode().toLowerCase().contains(search) ||
                    exam.getExOrder().toLowerCase().contains(search) ||
                    exam.getExCode().toLowerCase().contains(search);

            if (matchesMaDe && matchesThuTu && matchesSearch) {
                listExamFilter.add(exam);
            }
        }
        return listExamFilter;
    }
    
    
    public ArrayList<ExamDTO> getAllExams() {
        return ExamDAO.getAll(true);
    }

    public ArrayList<TopicQuestion> shuffleQuestions(List<TopicQuestion> questions) {
        int n = questions.size();
        Random rand = new Random();

        var result = new ArrayList<>(questions);

        while (n > 1) {
            n--;
            int index = rand.nextInt(n);

            TopicQuestion temp = result.get(n);
            result.set(n, result.get(index));
            result.set(index, temp);
        }

        return result;
    }

    public ExamDTO findByID(Integer id) {
        return ExamDAO.findByID(id);
    }

    public boolean isExist(String testCode) {
        return ExamDAO.isExist(testCode);
    }

    public ExamDTO addExam(ExamDTO exam) {
        return ExamDAO.create(exam);
    }

    public boolean updateExam(ExamDTO exam, Integer id) {
        return ExamDAO.update(id, exam);
    }

    public boolean deleteExam(Integer id) {
        return ExamDAO.delete(id);
    }

    public boolean deleteExam(String testCode) {
        return ExamDAO.delete(testCode);
    }

    public ExamDTO findByExCode(String exCode) {
        return ExamDAO.findByExCode(exCode);
    }

    public List<ExamDTO> findByTestCode(String testCode) {
        return ExamDAO.findByTestCode(testCode);
    }
    
    public ExamDTO randomExamByTestCode(String testCode) {
        return ExamDAO.randomExamByTestCode(testCode);
    }

    /**
     * Get the number of exams that are randomly generated
     */
    public int getExamCount(String testCode) {
        return ExamDAO.findByTestCode(testCode).size();
    }

    public boolean generateExam(TestDTO testExam, int quantity, List<TestStructureDTO> listTestStructure) {
        var testStructureQuestions =  getTestStructureQuestions(listTestStructure);

        HashMap<Integer, DifficultCount> diffCountMap = testStructureQuestions.getFirst();
        List<TopicQuestion> questions = testStructureQuestions.getLast();

        ArrayList<ExamDTO> requests = new ArrayList<>();

        char order = 'A';

        for (int i = 0; i < quantity; i++) {
            if (order > 'Z') return false;

            resetDiffCountMap(diffCountMap);
            var shuffleList = shuffleQuestions(questions);
            var list = getQuestionsWithCondition(shuffleList, diffCountMap);

            ExamDTO model = ExamDTO.builder()
                                   .setTestCode(testExam.getTestCode())
                                   .setExOrder(String.valueOf(order))
                                   .setExCode(testExam.getTestCode() + String.valueOf(order))
                                   .setQuestions(list)
                                   .setStatus(true)
                                   .build();

            requests.add(model);

            order++;
        }

        return ExamDAO.createMultipleExam(requests);
    }

    // prepare resource
    private Pair<HashMap<Integer, DifficultCount>, List<TopicQuestion>> getTestStructureQuestions(List<TestStructureDTO> listTestStructure) {
        HashMap<Integer, DifficultCount> map = new HashMap<>();
        ArrayList<TopicQuestion> questions = new ArrayList<>();

        for (var testStructure : listTestStructure) {
            int topicId = testStructure.getTopicID();
            
            DifficultCount difficultCount = new DifficultCount(testStructure.getNumEasy(), 
                                                               testStructure.getNumMedium(), 
                                                               testStructure.getNumDiff());

            map.put(topicId, difficultCount);

            List<QuestionDTO> tempList = questionBUS.getQuestionByTopicAndLevel(topicId, "");
            for (QuestionDTO question : tempList) {
                questions.add(new TopicQuestion(topicId, question));
            }
        }

        Pair<HashMap<Integer, DifficultCount>, List<TopicQuestion>> result = new Pair<>(map, questions);
        return result;
    }

    private void resetDiffCountMap(HashMap<Integer, DifficultCount> diffCountMap) {
        for (var key : diffCountMap.keySet()) {
            diffCountMap.get(key).clear();
        }
    }

    /**
     * Get list question with matching num of difficult
     */
    private ArrayList<QuestionDTO> getQuestionsWithCondition(List<TopicQuestion> questions, HashMap<Integer, DifficultCount> diffCountMap) {
        ArrayList<QuestionDTO> result = new ArrayList<>();

        for (TopicQuestion topicQuestion : questions) {
            int topicId = topicQuestion.topicID;
            if (!canAddQuestion(topicQuestion, diffCountMap.get(topicId))) continue;
            result.add(topicQuestion.question);
        }

        return result;
    }

    private boolean canAddQuestion(TopicQuestion topicQuestion, DifficultCount difficultCount) {
        QuestionDTO question = topicQuestion.question;
        String level = question.getLevel();
        Runnable callback;

        switch (level) {
            case "easy":
                if (difficultCount.isEnoughEasy()) return false;
                callback = () -> difficultCount.increaseEasy();
                break;

            case "medium":
            if (difficultCount.isEnoughMedium()) return false;
                callback = () -> difficultCount.increaseMedium();
                break;

            case "diff":
            if (difficultCount.isEnoughDiff()) return false;
                callback = () -> difficultCount.increaseDiff();
                break;
        
            default:
                return false;
        }

        callback.run();
        return true;
    }

    private class TopicQuestion {
        public final int topicID;
        public final QuestionDTO question;

        public TopicQuestion(int topicID, QuestionDTO question) {
            this.topicID = topicID;
            this.question = question;
        }
    }

    /**
     * Help in count condition of TestStructure
     */
    private class DifficultCount {
        private Pair<Integer, Integer> easy;
        private Pair<Integer, Integer> medium;
        private Pair<Integer, Integer> diff;

        public DifficultCount(int easyTarget, int mediumTarget, int diffTarget) {
            easy = new Pair<Integer,Integer>(0, easyTarget);
            medium = new Pair<Integer,Integer>(0, mediumTarget);
            diff = new Pair<Integer,Integer>(0, diffTarget);
        }

        public boolean increaseEasy() {
            int value = (Integer) easy.getFirst() + 1;
            if (value > easy.getLast()) return false;

            easy.setFirst(value);
            return true;
        }

        public boolean increaseMedium() {
            int value = (Integer) medium.getFirst() + 1;
            if (value > medium.getLast()) return false;

            medium.setFirst(value);
            return true;
        }

        public boolean increaseDiff() {
            int value = (Integer) diff.getFirst() + 1;
            if (value > diff.getLast()) return false;

            diff.setFirst(value);
            return true;
        }

        public boolean isEnoughEasy() {
            return easy.getFirst() == easy.getLast();
        }

        public boolean isEnoughMedium() {
            return medium.getFirst() == medium.getLast();
        }
        
        public boolean isEnoughDiff() {
            return diff.getFirst() == diff.getLast();
        }

        public void clear() {
            easy.setFirst(0);
            medium.setFirst(0);
            diff.setFirst(0);
        }
    }
}