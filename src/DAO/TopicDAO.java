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
     public List<TopicDTO> getAll(boolean active) {
        List<TopicDTO> listtopic = new ArrayList<>();
        String query = "SELECT * FROM topics WHERE tpStatus= ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setInt(1,active ? 1 : 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TopicDTO topic = TopicDTO.builder()
                .setId(resultSet.getInt("tpID"))
                .setTitle(resultSet.getString("tpTitle"))
                .setParent(resultSet.getString("tpParent"))
                // .setStatus(resultSet.getBoolean("tpStatus"))
                
                .build();
                listtopic.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listtopic;
    }
    @Override
    public TopicDTO create(TopicDTO topicDTO){
        String query = "INSERT INTO topics(  tpTitle, tpParent,tpStatus) VALUE(?,?,?)";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            // preparedStatement.setLong(1,userDTO.createId());
            preparedStatement.setString(1, topicDTO.getTitle());
            preparedStatement.setString(2, topicDTO.getParent());
            preparedStatement.setInt(3, 1);
            
            var result = preparedStatement.executeUpdate() > 0;

            if (!result) return null;
            else return topicDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Integer id, TopicDTO topicDTO) {
        String query = "UPDATE topics SET tpTitle = ?, tpParent = ? WHERE tpID = ?";

        try (Connection conn = Helper.ConnectDB.getInstance().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
    
            preparedStatement.setString(1, topicDTO.getTitle());
            preparedStatement.setString(2, topicDTO.getParent());
            preparedStatement.setInt(3, id);
           return preparedStatement.executeUpdate()> 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE from topics WHERE tpID = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

   
public TopicDTO findByID(Integer id) {
    String query = "SELECT * FROM topics WHERE tpID = ?";
        try ( PreparedStatement preparedStatement = Helper.ConnectDB.getInstance().getConnection()
        .prepareStatement(query)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                TopicDTO topic = TopicDTO.builder()
                .setId(resultSet.getInt("tpID"))
                .setTitle(resultSet.getString("tpTitle"))
                .setParent(resultSet.getString("tpParent"))
                
                .build();
                return topic;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
  

   }

   
   

