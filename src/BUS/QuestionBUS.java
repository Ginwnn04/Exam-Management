/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.AnswerDAO;
import DAO.QuestionDAO;
import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.HashSet;
import DAO.QuestionDAO;
import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author quang
 */
public class QuestionBUS {
    private QuestionDAO questionDAO =  new QuestionDAO();
    private AnswerDAO answerDAO = new AnswerDAO();


    
    public List<QuestionDTO> filterTable(String search, int topicId, String level) {
        List<QuestionDTO> list = questionDAO.getAll(true);
        return list.stream()
            .filter(question -> topicId == -1 || question.getTopicId() == topicId) 
            .filter(question -> level.equals("All") || question.getLevel().equalsIgnoreCase(level)) 
            .filter(question -> search.isEmpty() || question.getContent().toLowerCase().contains(search.toLowerCase())) 
            .collect(Collectors.toList());
       
    }
    
    public List<QuestionDTO> getAllQuestion(boolean active) {
        return questionDAO.getAll(active);
    }
    
    public QuestionDTO createQuestion(QuestionDTO question) {
        return questionDAO.create(question);
    }
    public boolean updateQuestion(int id, QuestionDTO question) {
        return questionDAO.update(id, question);
    }
    public boolean deleteQuestion(int id) {
        return questionDAO.delete(id) && answerDAO.deleteByQuestionId(id);
    }

    public List<QuestionDTO> getQuestionByExamCode(String examCode) {
        return questionDAO.getQuestionByExamCode(examCode);
    }
    
    public List<QuestionDTO> getQuestionByTopicAndLevel(int id, String level) {
        List<Integer> listTopic = getListTopicParent(id);
        if (level.equals("")) {
            return questionDAO.getQuestionByTopic(listTopic);
        }
        return questionDAO.getQuestionByTopicAndLevel(listTopic, level);
    }
    
    private List<Integer> getListTopicParent(int topicId) { 
        return questionDAO.getListTopic(topicId);
    }
}
