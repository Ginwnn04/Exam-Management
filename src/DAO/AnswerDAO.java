/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.AnswerDTO;
import Helper.ConnectDB;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author pc
 */
public class AnswerDAO implements BaseDAO<AnswerDTO, Integer>{

    @Override
    public AnswerDTO create(AnswerDTO request) {
        String query = "INSERT INTO answers (qID, awContent, awPictures, isRight, awStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, request.getQuestionId());
            pstm.setString(2, request.getContent());
            pstm.setString(3, request.getPicture() != null ? request.getPicture() : "");
            pstm.setInt(4, request.isIsRight() ? 1 : 0);
            pstm.setInt(5, request.isStatus() ? 1 : 0);
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
    public boolean update(Integer id, AnswerDTO request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AnswerDTO> getAll(boolean active) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<AnswerDTO> getByQuestionId(int id) {
        List<AnswerDTO> listAnsw = new ArrayList<>();
        String query = "SELECT * FROM answers WHERE awStatus = 1 AND qID = ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                AnswerDTO answ = AnswerDTO.builder()
                        .setId(rs.getInt("awID"))
                        .setContent(rs.getString("awContent"))
                        .setPicture(rs.getString("awPictures"))
                        .setQuestionId(rs.getInt("qID"))
                        .setIsRight(rs.getInt("isRight") == 1 ? true : false)
                        .build();
                listAnsw.add(answ);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return listAnsw;
        
    }
    
    public boolean deleteByQuestionId(int id) {
        String query = "UPDATE answers SET awStatus = 0 WHERE qID = ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, id);
            return pstm.executeUpdate() != 0; 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
