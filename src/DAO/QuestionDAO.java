/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.List;
import Helper.ConnectDB;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author quang
 */
public class QuestionDAO implements BaseDAO<QuestionDTO, Integer>{

    @Override
    public QuestionDTO create(QuestionDTO request) {
        String query = "INSERT INTO questions (qContent, qPictures, qTopicID, qLevel, qStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, request.getContent());
            pstm.setString(2, request.getPicture());
            pstm.setInt(3, request.getTopicId());
            pstm.setString(4, request.getLevel());
            pstm.setInt(5, 1);
            pstm.executeUpdate();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newId = generatedKeys.getInt(1);
                request.setId(newId); // Gán lại ID cho object
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public boolean update(Integer id, QuestionDTO request) {
        String query = "UPDATE questions SET "
                + "qContent = ? AND "
                + "qPictures = ? AND "
                + "qTopicID = ? AND "
                + "qLevel = ? "
                + "WHERE qID = ? AND qStatus = 1";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setString(1, request.getContent());
            pstm.setString(2, request.getPicture());
            pstm.setInt(3, request.getTopicId());
            pstm.setString(4, request.getLevel());
            pstm.setInt(5, id);
            return pstm.executeUpdate() != 0; 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "UPDATE questions SET qStatus = 0 WHERE qID = ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, id);
            return pstm.executeUpdate() != 0; 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public List<QuestionDTO> getQuestionByTopicAndLevel(List<Integer> listTopic, String level) {
        List<QuestionDTO> listQuestion = new ArrayList<>();
        String placeholders = String.join(",", Collections.nCopies(listTopic.size(), "?"));
        String query = "SELECT * FROM questions WHERE qStatus = 1 AND qTopicID IN (" + placeholders + ") AND qLevel = ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            for (int i = 0; i < listTopic.size(); i++) {
                pstm.setInt(i + 1, listTopic.get(i));
            }
            pstm.setString(listTopic.size() + 1, level);
            
            ResultSet rs = pstm.executeQuery();
            System.out.println(pstm.toString());
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
    
    public List<QuestionDTO> getQuestionByTopic(List<Integer> listTopic) {
        List<QuestionDTO> listQuestion = new ArrayList<>();
        String placeholders = String.join(",", Collections.nCopies(listTopic.size(), "?"));
        String query = "SELECT * FROM questions WHERE qStatus = 1 AND qTopicID IN (" + placeholders + ")";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            for (int i = 0; i < listTopic.size(); i++) {
                pstm.setInt(i + 1, listTopic.get(i));
            }            
            ResultSet rs = pstm.executeQuery();
            System.out.println(pstm.toString());
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
    
    public List<Integer> getListTopic(int topicId) {
        List<Integer> listTopicID = new ArrayList<>();
        String query = "WITH RECURSIVE TopicTree AS (\n" +
                        "    SELECT tpID, tpParent FROM topics WHERE tpID = ? AND tpStatus = 1\n" +
                        "    UNION ALL\n" +
                        "    SELECT t.tpID, t.tpParent FROM topics t \n" +
                        "    INNER JOIN TopicTree tt ON t.tpParent = tt.tpID  \n" +
                        ")\n" +
                        "SELECT * FROM TopicTree;";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, topicId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                
                listTopicID.add(rs.getInt("tpID"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return listTopicID;
    }
    
}
