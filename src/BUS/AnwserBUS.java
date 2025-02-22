/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.AnwserDAO;
import DTO.AnwserDTO;

/**
 *
 * @author pc
 */
public class AnwserBUS {
    private AnwserDAO anwserDAO = new AnwserDAO();
    
    public AnwserDTO createAnwser(AnwserDTO answer) {
        return anwserDAO.create(answer);
    }
}
