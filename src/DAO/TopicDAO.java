/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.TopicDTO;
import java.util.ArrayList;
import java.util.List;
import Helper.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.Statement;


/**
 *
 * @author 
 */
public class TopicDAO implements BaseDAO<TopicDTO, Integer>{

    @Override
    public TopicDTO create(TopicDTO topicDTO) {
        
        return null;
    }

    @Override
    public boolean update(Integer id, TopicDTO topicDTO) {
        String query = "UPDATE topics SET tpTitle = ?, tpParent = ? WHERE tpID = ?";

        try (Connection conn = Helper.ConnectDB.getInstance().getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {
    
            pstm.setString(1, topicDTO.getTitle());
            pstm.setInt(2, topicDTO.getParent());
            pstm.setInt(3, id);
    
            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0; 
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
       
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM topics WHERE tpID = ?";

    try (Connection conn = Helper.ConnectDB.getInstance().getConnection();
         PreparedStatement pstm = conn.prepareStatement(query)) {

        pstm.setInt(1, id);
        int affectedRows = pstm.executeUpdate();

        return affectedRows > 0; // ✅ Trả về true nếu xóa thành công

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
       
    }
    

    @Override
    public List<TopicDTO> getAll(boolean active) {
        List<TopicDTO> listTopic = new ArrayList<>();
        String query = "SELECT * FROM topics WHERE tpStatus = ?";
        try (PreparedStatement pstm = ConnectDB.getInstance().getConnection().prepareStatement(query)) {
            pstm.setInt(1, active ? 1 : 0);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                TopicDTO topic = TopicDTO.builder()
                        .setId(rs.getInt("tpId"))
                        .setTitle(rs.getString("tpTitle"))
                          .setParent(rs.getInt("tpParent"))
                       
                        .build();
                listTopic.add(topic);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return listTopic;
    }

  
   
   
    
}