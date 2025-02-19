/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;

/**
 *
 * @author quang
 */
public interface BaseDao<T, ID> {
    boolean create(T request);
    boolean update(ID id, T request);
    boolean delete(ID id);
    List<T> getAll(boolean active);
    T findByID(ID id); 
}