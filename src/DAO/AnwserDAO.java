/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.AnwserDTO;
import Helper.ConnectDB;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author pc
 */
public class AnwserDAO implements BaseDAO<AnwserDTO, Integer>{

    @Override
    public boolean create(AnwserDTO request) {
        String query = "INSERT INTO(qID, awContent, awPictures, isRight, awStatus) VALUES ?, ?, ?, ?, ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, request.getQuestionId());
            pstm.setString(2, request.getContent());
            pstm.setString(3, request.getPicture());
            pstm.setInt(4, request.isIsRight() ? 1 : 0);
            pstm.setInt(5, request.isStatus() ? 1 : 0);
            return pstm.executeUpdate() != 0; 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
