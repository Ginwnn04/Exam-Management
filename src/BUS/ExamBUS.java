package BUS;

import DAO.ExamDAO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import DTO.TestExamDTO;
import Enum.LevelEnum;

import java.util.ArrayList;
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

    /**
     * Get the number of exams that are randomly generated
     */
    public int getExamCount(String testCode) {
        return ExamDAO.findByTestCode(testCode).size();
    }

    public boolean generateExam(TestExamDTO testExam, int quantity) {
        List<QuestionDTO> questions = questionBUS.getQuestionByTopicAndLevel(testExam.getTopicId(), "");
        char order = 65;
        boolean result = false;
        
        for (int i = 0; i < quantity; i++) {
            if (order > 'Z') return false;

            var list = getQuestionsByCondition(testExam, shuffleQuestions(questions));

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
    
    public ArrayList<QuestionDTO> getQuestionsByCondition(TestExamDTO testExam, List<QuestionDTO> questions) {
        ArrayList<QuestionDTO> result = new ArrayList<>();

        int numEasy = testExam.getEasyQuestionCount();
        int numMedium = testExam.getMediumQuestionCount();
        int numDiff = testExam.getDiffQuestionCount();

        int easyCount = 0, mediumCount = 0, diffCount = 0;

        for (QuestionDTO question : questions) {
            String level = question.getLevel();
            
            if (level.equals(LevelEnum.EASY.getDesc()) && easyCount < numEasy) easyCount++;
            else if (level.equals(LevelEnum.MEDIUM.getDesc()) && mediumCount < numMedium) mediumCount++;
            else if (level.equals(LevelEnum.HARD.getDesc()) && diffCount < numDiff) diffCount++;
            else continue;

            result.add(question);
        }

        return result;
    }
}