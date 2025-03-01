package BUS;

import DAO.ExamDAO;
import DTO.ExamDTO;
import DTO.QuestionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExamBUS {
    private ExamDAO ExamDAO = new ExamDAO();

    public ArrayList<ExamDTO> getAllExams() {
        return ExamDAO.getAll(true);
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
}