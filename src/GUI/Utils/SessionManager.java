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

    
public class SessionManager {
    private static UserDTO currentUser; // Lưu user đang đăng nhập

    // Lưu thông tin user vào session
    public static void setCurrentUser(UserDTO user) {
        currentUser = user;
    }

    // Lấy user hiện tại
    public static UserDTO getCurrentUser() {
        return currentUser;
    }

}

