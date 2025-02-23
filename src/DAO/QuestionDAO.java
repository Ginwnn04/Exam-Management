/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.List;
import Helper.ConnectDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author quang
 */
public class QuestionDAO implements BaseDAO<QuestionDTO, Integer>{

    @Override
    public boolean create(QuestionDTO request) {
        
        return true;
    }

    @Override
    public boolean update(Integer id, QuestionDTO request) {
        
        return true;
    }

    @Override
    public boolean delete(Integer id) {

        return true;
    }

    @Override
    public List<QuestionDTO> getAll(boolean active) {
        List<QuestionDTO> listQuestion = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE qStatus = ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, active ? 1 : 0);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                QuestionDTO question = QuestionDTO.builder()
                        .setId(rs.getInt("qId"))
                        .setContent(rs.getString("qContent"))
                        .setPicture(rs.getString("qPictures"))
                        .setTopicId(rs.getInt("qTopicID"))
                        .setLevel(rs.getString("qLevel"))
                        .build();
                listQuestion.add(question);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return listQuestion;
    }

    
    
}