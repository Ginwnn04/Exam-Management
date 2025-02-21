/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.QuestionDAO;
import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quang
 */
public class QuestionBUS {
    private List<QuestionDTO> listQuestion;
    private QuestionDAO questionDAO;

    public QuestionBUS() {
        this.listQuestion = new ArrayList<>();
        this.questionDAO = new QuestionDAO();
    }
    
    
    public List<QuestionDTO> getAllQuestion(boolean active) {
        listQuestion = questionDAO.getAll(active);
        return listQuestion;
    }
    
    public boolean createQuestion(QuestionDTO question) {
        return questionDAO.create(question);
    }
    public boolean updateQuestion(int id, QuestionDTO question) {
        return questionDAO.update(id, question);
    }
    public boolean deleteQuestion(int id) {
        return questionDAO.delete(id);
    }
    
    
}
