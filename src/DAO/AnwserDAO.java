/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.AnwserDTO;
import Helper.ConnectDB;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;


/**
 *
 * @author pc
 */
public class AnwserDAO implements BaseDAO<AnwserDTO, Integer>{

    @Override
    public AnwserDTO create(AnwserDTO request) {
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
    public boolean update(Integer id, AnwserDTO request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AnwserDTO> getAll(boolean active) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
