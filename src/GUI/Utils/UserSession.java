/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Utils;

import DTO.UserDTO;

/**
 *
 * @author 84376
 */

    
public class UserSession {
    private static UserSession instance;
    private static UserDTO currentUser; 
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
   
    public void setCurrentUser(UserDTO user) {
        currentUser = user;
    }

   
    public UserDTO getCurrentUser() {
        return currentUser;
    }

}

