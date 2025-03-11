package BUS;

import DAO.ExamDAO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import DTO.TestDTO;
import DTO.TestStructureDTO;
import Enum.LevelEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ExamBUS {
    private ExamDAO ExamDAO = new ExamDAO();
    private QuestionBUS questionBUS = new QuestionBUS();

    public ArrayList<ExamDTO> getAllExams() {
        return ExamDAO.getAll(true);
    }

    public ArrayList<QuestionDTO> shuffleQuestions(List<QuestionDTO> questions) {
        int n = questions.size();
        Random rand = new Random();

        var result = new ArrayList<>(questions);

        while (n > 1) {
            n--;
            int index = rand.nextInt(n);

            QuestionDTO temp = result.get(n);
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

    // need optimize
    public boolean generateExam(TestDTO testExam, int quantity, List<TestStructureDTO> listTestStructure) {
        ArrayList<TestStructureQuestion> testStructureQuestions = getQuestionsByTestStructure(listTestStructure);

        char order = 65;
        boolean result = false;
        
        for (int i = 0; i < quantity; i++) {
            if (order > 'Z') return false;

            var list = getShuffleQuestion(testStructureQuestions);

            ExamDTO model = ExamDTO.builder()
                                   .setTestCode(testExam.getTestCode())
                                   .setExOrder(String.valueOf(order))
                                   .setExCode(testExam.getTestCode() + String.valueOf(order))
                                   .setQuestions(list);

            result = addExam(model) != null;
            order++;
        }

        return result;
    }

    private ArrayList<QuestionDTO> getShuffleQuestion(List<TestStructureQuestion> testStructureQuestions) {
        ArrayList<QuestionDTO> result = new ArrayList<>();

        for (var testStructureQuestion : testStructureQuestions) {
            var questions = testStructureQuestion.getRandomWithCondition();
            result.addAll(questions);
        }

        return result;
    }

    private ArrayList<TestStructureQuestion> getQuestionsByTestStructure(List<TestStructureDTO> listTestStructure) {
        ArrayList<TestStructureQuestion> result = new ArrayList<>();

        for (TestStructureDTO testStructure : listTestStructure) {
            TestStructureQuestion topicQuestion = new TestStructureQuestion(testStructure, questionBUS);
            result.add(topicQuestion);
        }

        return result;
    }

    private class TestStructureQuestion {
        private TestStructureDTO testStructure;
        private List<QuestionDTO> questions;

        public TestStructureQuestion(TestStructureDTO testStructure, QuestionBUS questionBUS) {
            this.testStructure = testStructure;
            questions = questionBUS.getQuestionByTopicAndLevel(testStructure.getTopicID(), "");
        }

        private ArrayList<QuestionDTO> shuffleQuestions() {
            int n = questions.size();
            Random rand = new Random();
    
            var result = new ArrayList<>(questions);
    
            while (n > 1) {
                n--;
                int index = rand.nextInt(n);
    
                QuestionDTO temp = result.get(n);
                result.set(n, result.get(index));
                result.set(index, temp);
            }
    
            return result;
        }

        public List<QuestionDTO> getRandomWithCondition() {
            shuffleQuestions();

            ArrayList<QuestionDTO> result = new ArrayList<>();
            int easyCount = 0, mediumCount = 0, diffCount = 0;

            int easyTarget = testStructure.getNumEasy(), mediumTarget = testStructure.getNumMedium();
            int diffTarget = testStructure.getNumDiff();
            
            for (var question : questions) {
                String level = question.getLevel().trim();
            
                if (level.equals("easy") && easyCount < easyTarget) easyCount++;
                else if (level.equals("medium") && mediumCount < mediumTarget) mediumCount++;
                else if (level.equals("diff") && diffCount < diffTarget) diffCount++;
                else continue;

                result.add(question);
            }

            return result;
        }
    }
}