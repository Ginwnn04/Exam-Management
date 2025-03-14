/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.AnswerDAO;
import DTO.AnswerDTO;
import java.util.List;

/**
 *
 * @author pc
 */
public class AnswerBUS {
    private AnswerDAO anwserDAO = new AnswerDAO();
    
    public AnswerDTO createAnwser(AnswerDTO answer) {
        return anwserDAO.create(answer);
    }
    
    public List<AnswerDTO> getAnswerByQuestionId(int id) {
        return anwserDAO.getByQuestionId(id);
    }

    public List<AnswerDTO> findAnswersByListQuestionIds(List<Integer> questionIds) {
        return anwserDAO.findByListQuestionId(questionIds);
    }
}
